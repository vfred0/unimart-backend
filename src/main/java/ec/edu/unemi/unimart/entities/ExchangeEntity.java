package ec.edu.unemi.unimart.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "exchanges")
public class ExchangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

   @ManyToMany
    @JoinTable(
            name = "exchanges_articles",
            joinColumns = @JoinColumn(name = "exchange_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private Set<ArticleEntity> articleId;

    @Column(nullable = false, length = 50)
    private String userName;

    @Column(nullable = false)
    private String userPhoto;

    @Column(nullable = false, length = 60)
    private String articleToExchange;

    @Column(nullable = false, length = 60)
    private String articleToReceive;

    @Column(nullable = false)
//    @Temporal(TemporalType.DATE)
    private LocalDateTime date;
}

