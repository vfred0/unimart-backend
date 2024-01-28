package ec.edu.unemi.unimart.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ec.edu.unemi.unimart.api.dtos.ExchangeDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(columnDefinition = "TEXT DEFAULT NULL")
    String photo;

    @Size(max = 15)
    @Column(nullable = false, length = 15)
    String username;

    @Size(max = 50)
    @Column(nullable = false, length = 50)
    String names;

    @Size(max = 250)
    @Column(length = 250, columnDefinition = "VARCHAR(250) DEFAULT 'No hay información'")
    String about;

    @Builder.Default
    @Column(nullable = false, insertable = false, columnDefinition = "FLOAT DEFAULT 0.0")
    Double rating = 0.0;

    @Builder.Default
    @Column(nullable = false, insertable = false, columnDefinition = "SMALLINT DEFAULT 0")
    Short numberExchanges = 0;


    @Size(max = 10)
    @Column(length = 10, columnDefinition = "VARCHAR(10) DEFAULT '1234567890'")
    String numberWhatsapp;

    @JsonIgnore
    @Column(nullable = false)
    String password;

    @OneToMany(mappedBy = "userWhoWasRated")
    @ToString.Exclude
    List<Rating> whereUserWhoWasRated = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    Set<Article> articles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<RoleEntity> roles = new HashSet<>();


    @OneToMany(mappedBy = "user")
    Set<Session> sessions = new HashSet<>();

    public void setAverageRating(Rating rating) {
        this.whereUserWhoWasRated.add(rating);
        this.rating = this.whereUserWhoWasRated.stream()
                .mapToDouble(Rating::getScore)
                .average().orElse(0.0);
    }

    public void updateNumberExchanges() {
        this.numberExchanges = (short) this.whereUserWhoWasRated.size();
    }

    public List<ExchangeDto> getExchanges() {
        return this.articles.stream()
                .map(Article::getExchanges)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .map(exchange -> exchange.getExchangeDetails(this))
                .toList();
    }

}