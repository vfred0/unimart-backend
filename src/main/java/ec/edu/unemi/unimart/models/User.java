package ec.edu.unemi.unimart.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
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

    @Column(nullable = false, insertable = false)
    Double rating;

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
}