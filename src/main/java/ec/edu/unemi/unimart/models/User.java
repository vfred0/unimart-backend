package ec.edu.unemi.unimart.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Size(max = 50)
    @Column(name = "photo", nullable = false, length = 50)
    String photo;

    @Size(max = 15)
    @Column(name = "username", nullable = false, length = 15)
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

}