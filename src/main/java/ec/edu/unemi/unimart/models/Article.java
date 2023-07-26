package ec.edu.unemi.unimart.models;

import ec.edu.unemi.unimart.models.enums.Category;
import ec.edu.unemi.unimart.models.enums.Gender;
import ec.edu.unemi.unimart.models.enums.State;
import ec.edu.unemi.unimart.models.enums.TypeArticle;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @ElementCollection(targetClass = UUID.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "articles_suggestions", joinColumns = @JoinColumn(name = "article_id"))
    @Column(name = "suggest_id", nullable = false)
    Set<UUID> suggestions;

    public void setSuggestArticle(UUID suggestArticleId) {
        this.suggestions.add(suggestArticleId);
        this.numbersProposals++;
    }
}
