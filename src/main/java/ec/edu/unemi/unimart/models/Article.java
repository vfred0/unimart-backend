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

    private List<UUID> getProposersUserIdsForArticle() {
        if (containsArticlesWhereReceived()) {
            return this.whereReceived.stream()
                    .map(ProposedArticle::getProposerUserId)
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<Article> getProposerArticles() {
        return this.whereReceived.stream()
                .filter(proposedArticle -> !proposedArticle.containsExchanged())
                .map(ProposedArticle::getProposerArticle)
                .collect(Collectors.toList());
    }

    public void removeProposer(Article article) {
        this.whereReceived.removeIf(proposedArticle -> proposedArticle.isProposer(article));
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
        if (containsArticlesWhereReceived()) {
            exchanges = this.whereReceived.stream()
                    .map(ProposedArticle::getExchanges)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
        if (Objects.nonNull(this.whereProposed)) {
            exchanges.addAll(this.whereProposed.getExchanges());
        }
        return exchanges;
    }

    public ArticleDto setExchangeDetails(ArticleDto articleDto) {
        if (Objects.nonNull(whereProposed)) {
            articleDto.setIsAcceptableForExchange(this.whereProposed.receiverOrProposerAcceptExchanged());
            articleDto.setReceiverArticleId(this.whereProposed.getIdReceiverArticle());
            articleDto.setReceiverUserIdForArticle(this.whereProposed.getReceiverUserId());
        }
        articleDto.setProposersUserIdsForArticle(this.getProposersUserIdsForArticle());
        return articleDto;
    }

    public ArticleDto setReceiverArticleIdAndNumberProposals(ArticleDto articleDto) {
        short numberProposals = this.numbersProposals;
        short countExchanges = (short) this.whereReceived.stream()
                .filter(ProposedArticle::containsExchanged)
                .count();

        articleDto.setNumbersProposals((short) (numberProposals - countExchanges));

        if (Objects.nonNull(this.whereProposed)) {
            articleDto.setReceiverArticleId(this.whereProposed.getIdReceiverArticle());
        }

        return articleDto;
    }

    public void updateArticlesFromDeleteOrExchanged() {
        if (Objects.nonNull(this.whereProposed)) {
            this.whereProposed.getReceiverArticle().updateNumberProposals();
        }
        if (containsArticlesWhereReceived()) {
            this.whereReceived.forEach(proposedArticle ->
                    proposedArticle.getProposerArticle().setPublished()
            );
        }
    }

    private boolean containsArticlesWhereReceived() {
        return !this.whereReceived.isEmpty();
    }

    public void setPublished() {
        this.typeArticle = TypeArticle.PUBLISHED;
    }

    public void setExchanged() {
        this.typeArticle = TypeArticle.EXCHANGED;
    }

    public void setProposed() {
        this.typeArticle = TypeArticle.PROPOSED;
    }
}