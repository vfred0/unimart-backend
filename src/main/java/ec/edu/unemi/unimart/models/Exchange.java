package ec.edu.unemi.unimart.models;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    @JoinColumn(name = "proposal_id", nullable = false)
    @ToString.Exclude
    Proposal proposal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_rating_id")
    @ToString.Exclude
    Rating receiverRating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proposer_rating_id")
    @ToString.Exclude
    Rating proposerRating;

    public ExchangeDto getExchangeDetails(User user) {
        if (isUserReceiver(user)) {
            return getExchangeDto(getProposerArticle(), getReceiverArticle(), this.receiverRating != null);
        }
        return getExchangeDto(getReceiverArticle(), getProposerArticle(), this.proposerRating != null);
    }

    private ExchangeDto getExchangeDto(Article proposerArticle, Article receiverArticle, boolean hasBeenRated) {
        return ExchangeDto.builder()
                .id(this.id)
                .date(this.date)
                .userId(proposerArticle.getUser().getId())
                .isDiscarded(!this.isMade)
                .userName(proposerArticle.getUser().getName())
                .userPhoto(proposerArticle.getUser().getPhoto())
                .articleToReceive(proposerArticle.getTitle())
                .articleToExchange(receiverArticle.getTitle())
                .hasBeenRated(hasBeenRated)
                .build();
    }

    private Article getReceiverArticle() {
        return this.proposal.getReceiverArticle();
    }

    private Article getProposerArticle() {
        return this.proposal.getProposerArticle();
    }

    private boolean isUserReceiver(User user) {
        return getReceiverArticle().getUser().getId().equals(user.getId());
    }

    public void addRating(Rating rating) {
        if (isUserReceiver(rating.getUserWhoRated())) {
            this.receiverRating = rating;
        } else {
            this.proposerRating = rating;
        }
        this.isMade = true;
    }

    public void updateArticlesFromDiscard() {
        Article proposerArticle = getProposerArticle();
        Article receiverArticle = getReceiverArticle();
        receiverArticle.removeProposer(proposerArticle);
        proposerArticle.setPublished();
    }

    public void updateArticlesFromMade() {
        Article receiverArticle = getReceiverArticle();
        Article proposerArticle = getProposerArticle();
        receiverArticle.updateArticlesFromDeleteOrExchanged();
        proposerArticle.updateArticlesFromDeleteOrExchanged();
        receiverArticle.setExchanged();
        proposerArticle.setExchanged();
    }

    public UUID getProposerArticleId() {
        return getProposerArticle().getId();
    }
}