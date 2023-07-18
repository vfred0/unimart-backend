package ec.edu.unemi.unimart.entities;

import ec.edu.unemi.unimart.utils.Category;
import ec.edu.unemi.unimart.utils.Gender;
import ec.edu.unemi.unimart.utils.State;
import ec.edu.unemi.unimart.utils.TypeArticle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "articles")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    UUID id;
    @Column(nullable = false)
    String title;
    @Column(nullable = false)
    String description;

    @ElementCollection
    @Column(nullable = false)
    List<String> images;

    @Column(nullable = false)
    Category category;

    @Column(nullable = false)
    State state;

    Gender gender;

    @Column(nullable = false)
    TypeArticle typeArticle;

    @Column(nullable = false)
    UUID idUser;

    @Column(nullable = false)
    Integer numbersProposals;

    @Column(nullable = false)
    String datePublished;
}
