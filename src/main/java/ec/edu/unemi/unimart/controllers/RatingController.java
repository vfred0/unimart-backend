package ec.edu.unemi.unimart.controllers;

import ec.edu.unemi.unimart.dtos.RatingDto;
import ec.edu.unemi.unimart.services.rating.IRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ratings")
public class RatingController {
    private final IRatingService ratingService;

    @GetMapping
    ResponseEntity<List<RatingDto>> getAll() {
        return ResponseEntity.ok(ratingService.getAll());
    }

    @PostMapping
    ResponseEntity<HttpHeaders> save(@RequestBody RatingDto ratingDto) {
        UUID id = ratingService.save(ratingDto).getId();
        return new ResponseEntity<>(getHttpHeaders(id), HttpStatus.CREATED);
    }


    private HttpHeaders getHttpHeaders(UUID userId) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userId)
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return headers;
    }

}
