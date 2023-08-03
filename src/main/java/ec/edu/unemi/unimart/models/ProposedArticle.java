package ec.edu.unemi.unimart.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "proposed_articles")
@NoArgsConstructor
@AllArgsConstructor
public class ProposedArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "receiver_article_id", nullable = false)
    @ToString.Exclude
    Article receiverArticle;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "proposer_article_id", nullable = false)
    @ToString.Exclude
    Article proposerArticle;

    @OneToMany(mappedBy = "proposedArticle")
    @ToString.Exclude
    Set<Exchange> exchanges = new LinkedHashSet<>();
}