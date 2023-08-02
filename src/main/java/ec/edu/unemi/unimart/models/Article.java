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
import java.util.Set;
import java.util.UUID;

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
    @CollectionTable(name = "articles_images", joinColumns = @JoinColumn(name = "article_id"), foreignKey = @ForeignKey(name = "fk_article_images_article_id"))
    @Column(nullable = false, length = 50)
    Set<String> images = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "receiverArticle")
    Set<ProposedArticle> receiverArticles = new LinkedHashSet<>();

    @OneToOne(mappedBy = "proposerArticle")
    ProposedArticle proposerArticle;

}