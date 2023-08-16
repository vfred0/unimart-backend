package ec.edu.unemi.unimart.models;

import ec.edu.unemi.unimart.dtos.RatingDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "ratings")
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
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
                .userName(this.userWhoRated.getName())
                .userPhoto(this.userWhoRated.getPhoto())
                .build();
    }
}