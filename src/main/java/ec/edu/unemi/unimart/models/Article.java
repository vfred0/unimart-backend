package ec.edu.unemi.unimart.models;

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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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

    @Size(max = 60)
    @NotNull
    @Column(name = "title", nullable = false, length = 60)
    String title;

    @Size(max = 250)
    @NotNull
    @Column(name = "description", nullable = false, length = 250)
    String description;

    @Column(name = "numbers_proposals", nullable = false, insertable = false)
    Short numbersProposals;

    @Column(name = "date", nullable = false, insertable = false, updatable = false)
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
    Set<ProposedArticle> receiverArticles = new LinkedHashSet<>();


    @OneToOne(mappedBy = "proposerArticle")
    ProposedArticle proposerArticle;


    public boolean containsFilters(String title, Category category, State state) {
        return this.title.contains(title) && this.category.equals(category) && this.state.equals(state);
    }

    public boolean isExchanged() {
        return TypeArticle.isExchanged(this.typeArticle);
    }


    public List<UUID> getProposersUserIdsForArticle() {
        if (!this.receiverArticles.isEmpty()) {
            return this.receiverArticles.stream()
                    .map(proposedArticle -> proposedArticle.getProposerArticle().getUser().getId())
                    .collect(Collectors.toList());
        }
        return null;
    }

    public UUID getReceiverUserIdForArticle() {
        if (this.proposerArticle != null) {
            return this.proposerArticle.getReceiverArticle().getUser().getId();
        }
        return null;
    }

    public Boolean isAcceptProposals() {
        boolean isAcceptProposals = true;
        if (this.proposerArticle != null) {
            isAcceptProposals = this.proposerArticle.getExchanges().stream()
                    .noneMatch(Exchange::getIsMade);
        }
        if (!this.receiverArticles.isEmpty()) {
            isAcceptProposals = this.receiverArticles.stream()
                    .flatMap(proposedArticle -> proposedArticle.getExchanges().stream())
                    .noneMatch(Exchange::getIsMade);
        }
        return isAcceptProposals;
    }
}