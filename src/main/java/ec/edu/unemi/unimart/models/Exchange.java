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

    @ManyToOne
    @JoinColumn(name = "proposed_article_id", foreignKey = @ForeignKey(name = "fk_exchanges_proposed_article_id"))
    ProposedArticle proposedArticle;

    @ManyToOne
    @JoinColumn(name = "receiver_rating_id", foreignKey = @ForeignKey(name = "fk_exchanges_receiver_rating_id"))
    Rating receiverRatingId;

    @ManyToOne
    @JoinColumn(name = "proposer_rating_id", foreignKey = @ForeignKey(name = "fk_exchanges_proposer_rating_id"))
    Rating proposerRatingId;

    @ColumnDefault("NOW()")
    @Column(nullable = false, insertable = false, updatable = false)
    LocalDateTime date;

    @ColumnDefault("false")
    @Column(nullable = false, insertable = false)
    Boolean isMade;

}