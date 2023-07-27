package ec.edu.unemi.unimart.models;

import ec.edu.unemi.unimart.models.enums.Category;
import ec.edu.unemi.unimart.models.enums.Gender;
import ec.edu.unemi.unimart.models.enums.State;
import ec.edu.unemi.unimart.models.enums.TypeArticle;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false, length = 60)
    String title;

    @Column(nullable = false, length = 250)
    String description;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @Column(nullable = false, length = 50)
    List<String> images;

    @Column(nullable = false, length = 35)
    @Enumerated(EnumType.STRING)
    Category category;

    @Column(nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    State state;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    Gender gender;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    TypeArticle typeArticle;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_articles_users_user_id"))
    User user;

    @Column(columnDefinition = "SMALLINT DEFAULT 0")
    Short numbersProposals;

    LocalDateTime date;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<ProposedArticle> proposedArticles = new HashSet<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<ExchangeArticle> exchangeArticle = new HashSet<>();

    public void addProposedArticle(UUID articleProposedId) {
        this.proposedArticles.add(
                ProposedArticle.builder()
                        .article(this)
                        .proposedArticle(Article.builder().id(articleProposedId).build())
                        .build()
        );
        this.updateNumberProposals();
    }

    public void updateNumberProposals() {
        this.numbersProposals = (short) this.proposedArticles.size();
    }

    public void removeProposedArticle(UUID proposedArticleId) {
        this.proposedArticles.removeIf(proposedArticle -> proposedArticle.getProposedArticle().getId().equals(proposedArticleId));
        this.updateNumberProposals();
    }

    public boolean isToUserAndArticleProposed(UUID userId) {
        return this.user.getId().equals(userId) && this.typeArticle.equals(TypeArticle.PROPOSED);
    }
}
