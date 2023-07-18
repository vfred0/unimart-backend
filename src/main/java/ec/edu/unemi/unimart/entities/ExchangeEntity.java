package ec.edu.unemi.unimart.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "exchanges")
public class ExchangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String exchangeId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userPhoto;

    @Column(nullable = false)
    private String articleToExchange;

    @Column(nullable = false)
    private String articleToReceive;

    @Column(nullable = false)
    private String date;

}

