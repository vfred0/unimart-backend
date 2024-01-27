package ec.edu.unemi.unimart.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ec.edu.unemi.unimart.api.dtos.ExchangeDto;
import ec.edu.unemi.unimart.data.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Builder
@Getter
@Setter
@ToString
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Size(max = 50)
    @Column(nullable = false, length = 50)
    String photo;

    @Size(max = 15)
    @Column(nullable = false, length = 15)
    String username;

    @Size(max = 50)
    @Column(nullable = false, length = 50)
    String name;

    @Size(max = 250)
    @Column(nullable = false, length = 250)
    String about;

    @Builder.Default
    @Column(nullable = false, insertable = false)
    Double rating = 0.0;

    @Builder.Default
    @Column(nullable = false, insertable = false)
    Short numberExchanges = 0;

    @Size(max = 10)
    @Column(nullable = false, length = 10)
    String numberWhatsapp;

    @JsonIgnore
    @Size(max = 32)
    @Column(nullable = false, length = 32)
    String password;

    @OneToMany(mappedBy = "userWhoWasRated")
    @ToString.Exclude
    List<Rating> whereUserWhoWasRated;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    List<Article> articles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id")
    )
    Set<Role> roles;

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