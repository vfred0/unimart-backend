package ec.edu.unemi.unimart.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "ratings")
public class Rating {
    @Id
    @Column(name = "id", nullable = false)
    UUID id;

    @Column(name = "score", nullable = false)
    Short score;

    @Column(name = "comment", nullable = false, length = 100)
    String comment;

    @Column(name = "date", nullable = false)
    LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id_who_was_rated")
    User userIdWhoWasRated;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id_who_rated")
    User userIdWhoRated;

    @OneToMany(mappedBy = "receiverRating")
    Set<Exchange> receiverRating = new LinkedHashSet<>();

    @OneToMany(mappedBy = "proposerRating")
    Set<Exchange> proposerRating = new LinkedHashSet<>();

}