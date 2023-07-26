package ec.edu.unemi.unimart.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "exchanges")
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "exchange")
    Set<ExchangeArticle> exchangeArticle;

    @Column(nullable = false, length = 50)
    String userName;

    @Column(nullable = false, length = 50)
    String userPhoto;

    @Column(nullable = false, length = 60)
    String articleToExchange;

    @Column(nullable = false, length = 60)
    String articleToReceive;

    @Column(nullable = false)
    LocalDateTime date;

    public void add(Article article, Article articleProposed) {
        if (this.exchangeArticle == null) {
            exchangeArticle = new HashSet<>();
        }
        Logger.getLogger("Exchange").info("Article: " + article + "ArticleProposed: " + articleProposed);
        this.exchangeArticle.add(ExchangeArticle.builder().exchange(this).article(article).articleProposed(articleProposed).build());
        this.userName = article.getUser().getName();
        this.userPhoto = article.getUser().getPhoto();
        this.articleToExchange = article.getTitle();
        this.articleToReceive = articleProposed.getTitle();
        this.date = LocalDateTime.now();
    }
}

