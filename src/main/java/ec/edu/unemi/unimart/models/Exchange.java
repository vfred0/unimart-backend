package ec.edu.unemi.unimart.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
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
@Table(name = "exchanges")
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @OneToMany(mappedBy = "exchange", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<ProposedArticle> proposedArticles = new HashSet<>();

    @Column(nullable = false, length = 50)
    String userName;

    @Column(nullable = false, length = 50)
    String userPhoto;

    @Column(nullable = false, length = 60)
    String articleToExchange;

    @Column(nullable = false, length = 60)
    String articleToReceive;

    @ColumnDefault("NOW()")
    @Column(nullable = false, insertable = false, updatable = false)
    LocalDateTime date;

    @ColumnDefault("false")
    @Column(nullable = false)
    Boolean isAccepted;

    public void setValues(Article article, Article proposedArticle) {
        this.userName = article.getUser().getName();
        this.userPhoto = article.getUser().getPhoto();
        this.articleToExchange = article.getTitle();
        this.articleToReceive = proposedArticle.getTitle();
    }
}