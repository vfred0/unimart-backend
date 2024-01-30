package ec.edu.unemi.unimart.data.entities;

import ec.edu.unemi.unimart.data.entities.auditing.Auditing;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "proposals")
public class Proposal extends Auditing {
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