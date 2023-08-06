package ec.edu.unemi.unimart.models;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.dtos.article.ArticleDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
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

    @Column(nullable = false, insertable = false)
    Short numberExchanges;

    @Size(max = 10)
    @Column(nullable = false, length = 10)
    String numberWhatsapp;

    @Size(max = 32)
    @Column(nullable = false, length = 32)
    String password;

    @OneToMany(mappedBy = "userIdWhoWasRated")
    @ToString.Exclude
    List<Rating> ratings;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    List<Article> articles;

    public void setAverageRatingAndNumberExchanges(Rating rating) {
        this.ratings.add(rating);
        this.rating = this.ratings.stream()
                .mapToDouble(Rating::getScore)
                .average().orElse(0.0);
        this.updateNumberExchanges();
    }

    public void updateNumberExchanges() {
        this.numberExchanges = (short) this.ratings.size();
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