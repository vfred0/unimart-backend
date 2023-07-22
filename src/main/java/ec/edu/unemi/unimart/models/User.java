package ec.edu.unemi.unimart.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "numberWhatsapp", name = "uq_number_whatsapp")
        }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    UUID id;
    @Column(nullable = false, length = 50)
    String photo;
    @Column(nullable = false, length = 50)
    String name;
    @Column(nullable = false, length = 250)
    String about;
    @Column(nullable = false)
    Integer numberOfExchanges;
    @Column(nullable = false)
    Double rating;
    @Column(nullable = false, length = 10)
    String numberWhatsapp;
}
