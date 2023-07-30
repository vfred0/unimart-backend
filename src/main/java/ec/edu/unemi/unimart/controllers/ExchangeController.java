package ec.edu.unemi.unimart.controllers;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.dtos.article.ProposedArticleDto;
import ec.edu.unemi.unimart.services.exchange.IExchangeService;
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
@RequestMapping("/api/v1/exchanges")
public class ExchangeController {
    private final IExchangeService exchangeService;

    @GetMapping
    ResponseEntity<List<ExchangeDto>> getAll() {
        return ResponseEntity.ok(exchangeService.getAll());
    }

    @PostMapping
    ResponseEntity<HttpHeaders> save(@RequestBody ProposedArticleDto proposedArticleDto) {
        UUID id = exchangeService.save(proposedArticleDto);
        return new ResponseEntity<>(getHttpHeaders(id), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    ResponseEntity<HttpHeaders> delete(@PathVariable UUID id) {
        exchangeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("users/{id}")
    ResponseEntity<List<ExchangeDto>> findByUserId(@PathVariable UUID id) {
        return ResponseEntity.ok(exchangeService.findByUserId(id));
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
