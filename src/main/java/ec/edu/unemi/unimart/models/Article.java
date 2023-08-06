package ec.edu.unemi.unimart.models;

import ec.edu.unemi.unimart.dtos.article.ArticleDto;
import ec.edu.unemi.unimart.models.enums.Category;
import ec.edu.unemi.unimart.models.enums.Gender;
import ec.edu.unemi.unimart.models.enums.State;
import ec.edu.unemi.unimart.models.enums.TypeArticle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Size(max = 60) @NotNull
    @Column(nullable = false, length = 60)
    String title;

    @Size(max = 250) @NotNull
    @Column(nullable = false, length = 250)
    String description;

    @Column(nullable = false, insertable = false)
    Short numbersProposals;

    @Column(nullable = false, insertable = false, updatable = false)
    LocalDateTime date;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    Category category = Category.TEXT_BOOKS_EDUCATIONAL_MATERIAL;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    State state = State.NEW;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    Gender gender = null;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    TypeArticle typeArticle = TypeArticle.PUBLISHED;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    User user;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "article_images", joinColumns = @JoinColumn(name = "article_id"))
    @Column(name = "image", nullable = false)
    Set<String> images = new LinkedHashSet<>();

    @OneToMany(mappedBy = "receiverArticle", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    Set<ProposedArticle> whereReceived = new LinkedHashSet<>();

    @OneToOne(mappedBy = "proposerArticle")
    ProposedArticle whereProposed;

    public boolean containsFilters(String title, Category category, State state) {
        return this.title.contains(title) && this.category.equals(category) && this.state.equals(state);
    }

    public boolean isExchanged() {
        return TypeArticle.isExchanged(this.typeArticle);
    }

    private List<UUID> getProposersUserIdsForArticle() {
        if (!this.whereReceived.isEmpty()) {
            return this.whereReceived.stream()
                    .map(proposedArticle -> proposedArticle.getProposerArticle().getUser().getId())
                    .collect(Collectors.toList());
        }
        return null;
    }

    private UUID getReceiverUserIdForArticle() {
        if (this.whereProposed != null) {
            return this.whereProposed.getReceiverArticle().getUser().getId();
        }
        return null;
    }

    private Boolean isAcceptProposals() {
        boolean isAcceptProposals = true;
        if (this.whereProposed != null) {
            isAcceptProposals = this.whereProposed.getExchanges().stream().noneMatch(Exchange::getIsMade);
        }
        if (!this.whereReceived.isEmpty()) {
            isAcceptProposals = this.whereReceived.stream()
                    .flatMap(proposedArticle -> proposedArticle.getExchanges().stream())
                    .noneMatch(Exchange::getIsMade);
        }
        return isAcceptProposals;
    }

    public List<Article> getProposerArticles() {
        return this.whereReceived.stream()
                .filter(proposedArticle -> proposedArticle.getExchanges().isEmpty())
                .map(ProposedArticle::getProposerArticle)
                .collect(Collectors.toList());
    }

    public void removeProposer(Article article) {
        this.whereReceived.removeIf(proposedArticle -> proposedArticle.getProposerArticle().equals(article));
        this.updateNumberProposals();
    }

    public void updateNumberProposals() {
        this.numbersProposals = (short) this.whereReceived.size();
    }

    public void addWhereReceived(ProposedArticle proposedArticle) {
        this.whereReceived.add(proposedArticle);
        this.updateNumberProposals();
    }

    public List<Exchange> getExchanges() {
        List<Exchange> exchanges = new ArrayList<>();
        if (!this.whereReceived.isEmpty()) {
            exchanges = this.whereReceived.stream()
                    .map(ProposedArticle::getExchanges)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
        if (this.whereProposed != null) {
            exchanges.addAll(this.whereProposed.getExchanges()
                    .stream()
                    .toList());
        }
        return exchanges;
    }

    public ArticleDto setExchangeDetails(ArticleDto articleDto) {
        boolean isAcceptableForExchange = true;
        for (ProposedArticle proposedArticle : whereProposed.getReceiverArticle().getWhereReceived()) {
            if (!proposedArticle.getExchanges().isEmpty()) {
                Logger.getLogger(Article.class.getName()).log(Level.INFO, "Proposed article: " + proposedArticle.getId());
                isAcceptableForExchange = false;
                break;
            }
        }
        articleDto.setIsAcceptableForExchange(isAcceptableForExchange);
        articleDto.setReceiverUserIdForArticle(this.getReceiverUserIdForArticle());
        articleDto.setProposersUserIdsForArticle(this.getProposersUserIdsForArticle());
        return articleDto;
    }

    public ArticleDto setReceiverArticleIdAndNumberProposals(ArticleDto articleDto) {
        short numberProposals = this.numbersProposals;
        short countExchanges = (short) this.whereReceived.stream()
                .filter(proposedArticle -> !proposedArticle.getExchanges().isEmpty())
                .count();
        if (this.whereProposed != null) {
            articleDto.setReceiverArticleId(this.whereProposed.getReceiverArticle().getId());
            articleDto.setNumbersProposals((short) (numberProposals - countExchanges));
        }
        return articleDto;
    }

    public void updateArticlesFromDeleteOrExchanged() {
        if (this.whereProposed != null) {
            this.whereProposed.getReceiverArticle().updateNumberProposals();
        }
        if (!this.whereReceived.isEmpty()) {
            this.whereReceived.forEach(proposedArticle ->
                    proposedArticle.getProposerArticle().setPublished()
            );
        }
    }

    public void setPublished() {
        this.typeArticle = TypeArticle.PUBLISHED;
    }
}