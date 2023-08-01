package ec.edu.unemi.unimart.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "numberWhatsapp", name = "uq_number_whatsapp")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    UUID id;

    @Column(nullable = false, length = 15)
    String username;

    @Column(nullable = false, length = 50)
    String photo;

    @Column(nullable = false, length = 50)
    String name;

    @Column(nullable = false, length = 250)
    String about;

    @ColumnDefault("0")
    @Column(nullable = false, insertable = false)
    Short numberOfExchanges;

    @ColumnDefault("0.0")
    @Column(nullable = false, insertable = false)
    Double rating;

    @Column(nullable = false, length = 10)
    String numberWhatsapp;

    @Column(nullable = false, columnDefinition = "VARCHAR(32)")
    String password;
}
