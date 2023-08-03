package ec.edu.unemi.unimart.models;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "exchanges")
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false, insertable = false, updatable = false)
    LocalDateTime date;

    @Builder.Default
    @Column(nullable = false, insertable = false)
    Boolean isMade = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "proposed_article_id", nullable = false)
    @ToString.Exclude
    ProposedArticle proposedArticle;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "receiver_rating_id")
    @ToString.Exclude
    Rating receiverRating;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "proposer_rating_id")
    @ToString.Exclude
    Rating proposerRating;

    public ExchangeDto getDetails(User user) {
        User userProposer;
        boolean hasBeenRated;
        String articleToReceive;
        String articleToExchange;

        if (isUserReceiver(user)) {
            userProposer = getProposerArticle().getUser();
            hasBeenRated = this.receiverRating != null;
            articleToReceive = getProposerArticle().getTitle();
            articleToExchange = getReceiverArticle().getTitle();
        } else {
            userProposer = getReceiverArticle().getUser();
            hasBeenRated = this.proposerRating != null;
            articleToReceive = getReceiverArticle().getTitle();
            articleToExchange = getProposerArticle().getTitle();
        }

        return ExchangeDto.builder()
                .id(this.id)
                .date(this.date)
                .isDiscarded(!this.isMade)
                .userName(userProposer.getName())
                .userPhoto(userProposer.getPhoto())
                .articleToReceive(articleToReceive)
                .articleToExchange(articleToExchange)
                .hasBeenRated(hasBeenRated)
                .build();
    }

    private Article getReceiverArticle() {
        return this.proposedArticle.getReceiverArticle();
    }

    private Article getProposerArticle() {
        return this.proposedArticle.getProposerArticle();
    }

    private boolean isUserReceiver(User user) {
        return getReceiverArticle().getUser().equals(user);
    }

    public void addRating(Rating rating) {
        if (isUserReceiver(rating.getUserIdWhoRated())) {
            this.receiverRating = rating;
        } else {
            this.proposerRating = rating;
        }
        this.isMade = true;
    }
}