package ec.edu.unemi.unimart.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.UUID;

//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "exchanges")
public class Exchange {
    @Id
    @NotNull
    UUID id;

    @NotNull
    LocalDateTime date;

    @NotNull
    Boolean isMade = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "proposed_article_id", nullable = false)
    ProposedArticle proposedArticle;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "receiver_rating_id")
    Rating receiverRating;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "proposer_rating_id")
    Rating proposerRating;

}