package ec.edu.unemi.unimart.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;

@Getter
@Setter
@ToString
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "proposals")
@NoArgsConstructor
@AllArgsConstructor
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_article_id", nullable = false)
    @ToString.Exclude
    Article receiverArticle;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proposer_article_id", nullable = false)
    @ToString.Exclude
    Article proposerArticle;

    @OneToMany(mappedBy = "proposal")
    @ToString.Exclude
    Set<Exchange> exchanges = new LinkedHashSet<>();

    public boolean containsExchanged() {
        return !this.exchanges.isEmpty();
    }

    public boolean isProposer(Article article) {
        return this.proposerArticle.getId().equals(article.getId());
    }

    public UUID getReceiverUserId() {
        if (this.receiverArticle != null) {
            return this.receiverArticle.getUser().getId();
        }
        return null;
    }

    public UUID getProposerUserId() {
        return this.proposerArticle.getUser().getId();
    }

    public UUID getIdReceiverArticle() {
        return this.receiverArticle.getId();
    }

    public boolean receiverOrProposerAcceptExchanged() {
        return receiverArticle.getWhereReceived().stream()
                .noneMatch(Proposal::containsExchanged) &&
                whereProposedContainsExchanged();
    }

    private boolean whereProposedContainsExchanged() {
        return Objects.isNull(receiverArticle.getWhereProposed()) || !receiverArticle.getWhereProposed().containsExchanged();
    }
}