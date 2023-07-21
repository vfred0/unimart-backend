package ec.edu.unemi.unimart.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

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

    @ManyToMany
    @JoinTable(
            name = "exchanges_articles",
            joinColumns = @JoinColumn(name = "exchange_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"),
            foreignKey = @ForeignKey(name = "fk_exchanges_articles_exchange_id"),
            inverseForeignKey = @ForeignKey(name = "fk_exchanges_articles_article_id")
    )
    Set<Article> article;

    @Column(nullable = false, length = 50)
    String userName;

    @Column(nullable = false, length = 20)
    String userPhoto;

    @Column(nullable = false, length = 60)
    String articleToExchange;

    @Column(nullable = false, length = 60)
    String articleToReceive;

    @Column(nullable = false)
    LocalDateTime date;
}

