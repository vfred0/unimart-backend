package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.models.Rating;

import java.util.List;
import java.util.UUID;

public interface IRatingRepository extends IRepository<Rating, UUID> {
    List<Rating> findByUserId(UUID userId);
}
