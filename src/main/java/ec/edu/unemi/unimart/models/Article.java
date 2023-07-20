package ec.edu.unemi.unimart.models;

import ec.edu.unemi.unimart.utils.Category;
import ec.edu.unemi.unimart.utils.Gender;
import ec.edu.unemi.unimart.utils.State;
import ec.edu.unemi.unimart.utils.TypeArticle;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false, length = 60)
    String title;

    @Column(nullable = false, length = 100)
    String description;

    @ElementCollection
    @Column(nullable = false)
    List<String> images;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Category category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    State state;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TypeArticle typeArticle;

    @ManyToMany
    @JoinTable(
            name = "user_articles",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_user_articles_article_id"),
            inverseForeignKey = @ForeignKey(name = "fk_user_articles_user_id")
    )
    List<User> users;

    @Column(nullable = false)
    Integer numbersProposals;

    @Column(nullable = false)
    LocalDateTime date;
}
