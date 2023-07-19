package ec.edu.unemi.unimart.mappers;

import ec.edu.unemi.unimart.dtos.UserDto;
import ec.edu.unemi.unimart.entities.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    UserDto toUserDto(UserEntity user);

    @InheritInverseConfiguration
    UserEntity toUserEntity(UserDto user);

    List<UserDto> toUserDtos(List<UserEntity> all);
}
