package ec.edu.unemi.unimart.entities;

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
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    UUID id;
    @Column(nullable = false)
    String photo;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String about;
    @Column(nullable = false)
    String description;
    @Column(nullable = false)
    Integer numberOfExchanges;
    @Column(nullable = false)
    Double rating;
    @Column(nullable = false)
    String numberWhatsapp;
}
