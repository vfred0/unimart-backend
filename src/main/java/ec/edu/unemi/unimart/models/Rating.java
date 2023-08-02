package ec.edu.unemi.unimart.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id_who_was_rated", foreignKey = @ForeignKey(name = "fk_ratings_users_user_id_receiver"))
    User user;

    @ManyToOne
    @JoinColumn(name = "user_id_who_rated", foreignKey = @ForeignKey(name = "fk_ratings_users_user_id_proposer"))
    User userWhoRated;

    @Column(nullable = false, length = 250)
    String comment;

    @Column(nullable = false)
    Short score;

    @ColumnDefault("NOW()")
    @Column(nullable = false, insertable = false, updatable = false)
    LocalDateTime date;
}
