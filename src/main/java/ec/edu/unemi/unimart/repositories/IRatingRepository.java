package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.dtos.rating.RatingCardDto;
import ec.edu.unemi.unimart.models.Rating;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IRatingRepository extends IRepository<Rating, UUID>{
//    @Query(value="SELECT * from fn_get_rating_by_user_id(:userId)", nativeQuery = true)
//    List<RatingCardDto> getByUserId(UUID userId);

//    @Query("SELECT u.name  from User u, Rating r where u.id = r.userId and u.id = :userId")
    List<RatingCardDto> findByUserId(UUID userId);
}
