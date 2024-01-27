package ec.edu.unemi.unimart.api.resources;

import ec.edu.unemi.unimart.api.dtos.RatingDto;
import ec.edu.unemi.unimart.services.rating.IRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("ratings")
public class RatingResource {

    private final IRatingService ratingService;

    @GetMapping("users/{userId}")
    ResponseEntity<List<RatingDto>> getByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(ratingService.getByUserId(userId));
    }

}
