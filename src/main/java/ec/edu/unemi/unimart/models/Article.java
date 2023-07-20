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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_articles_users_user_id"))
    User user;

    @Column(nullable = false)
    Integer numbersProposals;

    @Column(nullable = false)
    LocalDateTime date;
}
