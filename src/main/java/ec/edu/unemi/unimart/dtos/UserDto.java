package ec.edu.unemi.unimart.dtos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class UserDto {
    UUID id;
    String photo;
    String name;
    String about;
    Integer numberOfExchanges;
    Double rating;
    String numberWhatsapp;
}