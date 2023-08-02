package ec.edu.unemi.unimart.entities;

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

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "articles")
public class Article {
    @Id
    @Column(name = "id", nullable = false)
    UUID id;

    @Size(max = 60)
    @NotNull
    @Column(name = "title", nullable = false, length = 60)
    String title;

    @Size(max = 250)
    @NotNull
    @Column(name = "description", nullable = false, length = 250)
    String description;

    @NotNull
    @Column(name = "numbers_proposals", nullable = false)
    Short numbersProposals;

    @NotNull
    @Column(name = "date", nullable = false, updatable = false)
    LocalDateTime date;

    @NotNull
    @Enumerated(EnumType.STRING)
    Category category;

    @NotNull
    @Enumerated(EnumType.STRING)
    State state;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    TypeArticle typeArticle;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @OneToOne(mappedBy = "articles")
    ArticleImage articleImage;

    @OneToMany(mappedBy = "receiverArticle")
    Set<ProposedArticle> receiverArticles = new LinkedHashSet<>();

    @OneToOne(mappedBy = "proposerArticle")
    ProposedArticle proposerArticle;

}