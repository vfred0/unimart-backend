package ec.edu.unemi.unimart.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "proposed_articles")
public class ProposedArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne
    @JoinColumn(name = "article_id", foreignKey = @ForeignKey(name = "fk_proposed_articles_article_id"))
    Article article;

    @ManyToOne
    @JoinColumn(name = "proposed_article_id", foreignKey = @ForeignKey(name = "fk_proposed_articles_article_proposed_id"))
    Article proposedArticle;
}
