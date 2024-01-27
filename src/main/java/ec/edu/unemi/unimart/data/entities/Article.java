package ec.edu.unemi.unimart.data.entities;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import ec.edu.unemi.unimart.api.dtos.ArticleDto;
import ec.edu.unemi.unimart.data.enums.Category;
import ec.edu.unemi.unimart.data.enums.Gender;
import ec.edu.unemi.unimart.data.enums.State;
import ec.edu.unemi.unimart.data.enums.TypeArticle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
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

    @Builder.Default
    @Column(nullable = false, insertable = false)
    Short numbersProposals = 0;

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
    @JoinColumn(name = "user_id")
    User user;

    @Type(StringArrayType.class)
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Image> images = new LinkedHashSet<>();

    @OneToMany(mappedBy = "receiverArticle", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    Set<Proposal> whereReceived = new LinkedHashSet<>();

    @OneToOne(mappedBy = "proposerArticle")
    Proposal whereProposed;

    private List<UUID> getProposersUserIdsForArticle() {
        if (containsArticlesWhereReceived()) {
            return this.whereReceived.stream()
                    .map(Proposal::getProposerUserId)
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<Article> getProposerArticles() {
        return this.whereReceived.stream()
                .filter(proposedArticle -> !proposedArticle.containsExchanged())
                .map(Proposal::getProposerArticle)
                .collect(Collectors.toList());
    }

    public void removeProposer(Article article) {
        this.whereReceived.removeIf(proposedArticle -> proposedArticle.isProposer(article));
        this.updateNumberProposals();
    }

    public void updateNumberProposals() {
        this.numbersProposals = (short) this.whereReceived.size();
    }

    public void addWhereReceived(Proposal proposal) {
        this.whereReceived.add(proposal);
        this.updateNumberProposals();
    }

    public List<Exchange> getExchanges() {
        List<Exchange> exchanges = new ArrayList<>();
        if (containsArticlesWhereReceived()) {
            exchanges = this.whereReceived.stream()
                    .map(Proposal::getExchanges)
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
                .filter(Proposal::containsExchanged)
                .count();

        articleDto.setNumbersProposals((short) (numberProposals - countExchanges));

        if (Objects.nonNull(this.whereProposed)) {
            articleDto.setReceiverArticleId(this.whereProposed.getIdReceiverArticle());
        }

        return articleDto;
    }

    public void updateArticlesFromDeleteOrExchanged() {
        if (Objects.nonNull(this.whereProposed)) {
            Article receiverArticle = this.whereProposed.getReceiverArticle();
            receiverArticle.decrementNumbersProposals();
        }
        if (containsArticlesWhereReceived()) {
            this.whereReceived.forEach(proposedArticle ->
                    proposedArticle.getProposerArticle().setPublished()
            );
        }
    }

    private void decrementNumbersProposals() {
        if (this.numbersProposals > 0) {
            this.numbersProposals--;
        } else {
            this.numbersProposals = 0;
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