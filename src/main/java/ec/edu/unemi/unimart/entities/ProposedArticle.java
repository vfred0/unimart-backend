package ec.edu.unemi.unimart.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "proposed_articles")
public class ProposedArticle {
    @Id
    @Column(name = "id", nullable = false)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "receiver_article_id", nullable = false)
    Article receiverArticle;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "proposer_article_id", nullable = false)
    Article proposerArticle;

    @OneToMany(mappedBy = "proposedArticle")
    Set<Exchange> exchanges = new LinkedHashSet<>();

}