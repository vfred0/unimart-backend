package ec.edu.unemi.unimart.controllers;

import ec.edu.unemi.unimart.dtos.RatingDto;
import ec.edu.unemi.unimart.services.rating.IRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ratings")
public class RatingController {

    private final IRatingService ratingService;

    @GetMapping("users/{userId}")
    ResponseEntity<List<RatingDto>> getByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(ratingService.getByUserId(userId));
    }

}
