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
@Table(name = "exchanges_articles")
public class ExchangeArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne
    @JoinColumn(name = "exchange_id", foreignKey = @ForeignKey(name = "fk_exchanges_articles_exchange_id"))
    Exchange exchange;

    @ManyToOne
    @JoinColumn(name = "article_id", foreignKey = @ForeignKey(name = "fk_exchanges_articles_article_id"))
    Article article;

    @ManyToOne
    @JoinColumn(name = "article_proposed_id", foreignKey = @ForeignKey(name = "fk_exchanges_articles_article_proposed_id"))
    Article articleProposed;

    public void setExchangeWith(Exchange exchange, Article article, Article articleProposed) {
        this.exchange = exchange;
        this.article = article;
        this.articleProposed = articleProposed;
    }
}
