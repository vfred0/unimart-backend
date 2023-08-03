package ec.edu.unemi.unimart.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingDto {
    String userName;
    String userPhoto;
    UUID userIdWhoWasRated;
    UUID userIdWhoRated;
    @Size(max = 250, message = "El comentario no puede tener más de 100 caracteres")
    String comment;
    Short score;
    LocalDateTime date;
}