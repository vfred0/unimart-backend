package ec.edu.unemi.unimart.data.entities;

import ec.edu.unemi.unimart.api.dtos.RatingDto;
import ec.edu.unemi.unimart.data.entities.auditing.Auditing;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "ratings")
public class Rating extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Builder.Default
    @Column(name = "score", nullable = false)
    Short score = 0;

    @Column(name = "comment", nullable = false, length = 100)
    String comment;

    @Column(nullable = false, insertable = false, updatable = false)
    LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_who_was_rated")
    User userWhoWasRated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_who_rated")
    User userWhoRated;

    @OneToMany(mappedBy = "receiverRating")
    Set<Exchange> receiverRating = new LinkedHashSet<>();

    @OneToMany(mappedBy = "proposerRating")
    Set<Exchange> proposerRating = new LinkedHashSet<>();

    public RatingDto getDetails() {
        return RatingDto.builder()
                .comment(this.comment)
                .score(this.score)
                .date(this.date)
                .userName(this.userWhoRated.getNames())
                .userPhoto(this.userWhoRated.getPhoto())
                .build();
    }
}