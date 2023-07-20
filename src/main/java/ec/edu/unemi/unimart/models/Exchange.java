package ec.edu.unemi.unimart.models;

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
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

   @ManyToMany
    @JoinTable(
            name = "exchanges_articles",
            joinColumns = @JoinColumn(name = "exchange_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private Set<Article> articleId;

    @Column(nullable = false, length = 50)
    private String userName;

    @Column(nullable = false)
    private String userPhoto;

    @Column(nullable = false, length = 60)
    private String articleToExchange;

    @Column(nullable = false, length = 60)
    private String articleToReceive;

    @Column(nullable = false)
    private LocalDateTime date;
}

