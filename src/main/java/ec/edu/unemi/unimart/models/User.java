package ec.edu.unemi.unimart.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "numberWhatsapp", name = "uq_number_whatsapp")
    }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;
    @Column(nullable = false)
    private String photo;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 100)
    private String about;
    @Column(nullable = false)
    private Integer numberOfExchanges;
    @Column(nullable = false)
    private Double rating;
    @Column(nullable = false, length = 10)
    private String numberWhatsapp;
}
