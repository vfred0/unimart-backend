package ec.edu.unemi.unimart.mappers;

import ec.edu.unemi.unimart.dtos.RatingDto;
import ec.edu.unemi.unimart.entities.RatingEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRatingMapper {
    RatingDto toRatingDto(RatingEntity rating);

    @InheritInverseConfiguration
    RatingEntity toRatingEntity(RatingDto rating);
}
