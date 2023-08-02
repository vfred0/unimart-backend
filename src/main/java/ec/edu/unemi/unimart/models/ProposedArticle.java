package ec.edu.unemi.unimart.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "proposed_articles", uniqueConstraints = {@UniqueConstraint(name = "uq_proposed_article", columnNames = "proposed_article_id")})
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

    @OneToMany(mappedBy = "proposedArticle", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Exchange> exchanges = new HashSet<>();
}
