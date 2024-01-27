package ec.edu.unemi.unimart.api.resources;

import ec.edu.unemi.unimart.api.dtos.ExchangeDto;
import ec.edu.unemi.unimart.api.dtos.ProposalDto;
import ec.edu.unemi.unimart.api.dtos.RatingDto;
import ec.edu.unemi.unimart.services.exchange.IExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("exchanges")
public class ExchangeResource {
    private final IExchangeService exchangeService;

    @PutMapping("{exchangeId}")
    ResponseEntity<HttpHeaders> setExchangeMade(@PathVariable UUID exchangeId, @RequestBody RatingDto ratingDto) {
        UUID id = exchangeService.setExchangeMade(exchangeId, ratingDto);
        return new ResponseEntity<>(HttpHeader.getHttpHeaders(id), HttpStatus.CREATED);
    }

    @PostMapping
    ResponseEntity<HttpHeaders> acceptExchange(@RequestBody ProposalDto proposalDto) {
        UUID id = exchangeService.acceptExchange(proposalDto);
        return new ResponseEntity<>(HttpHeader.getHttpHeaders(id), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    ResponseEntity<HttpHeaders> discardExchange(@PathVariable UUID id) {
        exchangeService.discardExchange(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("users/{id}")
    ResponseEntity<List<ExchangeDto>> findByUserId(@PathVariable UUID id) {
        return ResponseEntity.ok(exchangeService.findByUserId(id));
    }
}
