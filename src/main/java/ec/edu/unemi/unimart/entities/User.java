package ec.edu.unemi.unimart.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    UUID id;

    @Size(max = 50)
    @Column(name = "photo", nullable = false, length = 50)
    String photo;

    @Size(max = 15)
    @Column(name = "username", nullable = false, length = 15)
    String username;

    @Size(max = 50)
    @Column(name = "name", nullable = false, length = 50)
    String name;

    @Size(max = 250)
    @Column(name = "about", nullable = false, length = 250)
    String about;

    @Column(name = "rating", nullable = false)
    Double rating;

    @Column(name = "number_exchanges", nullable = false)
    Short numberExchanges;

    @Size(max = 10)
    @Column(name = "number_whatsapp", nullable = false, length = 10)
    String numberWhatsapp;

    @Size(max = 32)
    @Column(name = "password", nullable = false, length = 32)
    String password;

}