package ec.edu.unemi.unimart.controllers;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.dtos.ExchangeSaveDto;
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

    @DeleteMapping("/{id}")
    ResponseEntity<HttpHeaders> delete(@PathVariable UUID id) {
        exchangeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    ResponseEntity<HttpHeaders> save(@RequestBody ExchangeSaveDto exchangeSaveDto) {
        UUID id = exchangeService.save(exchangeSaveDto);
        return new ResponseEntity<>(getHttpHeaders(id), HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
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
