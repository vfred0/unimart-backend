package ec.edu.unemi.unimart.controllers.article;

import ec.edu.unemi.unimart.dtos.article.ArticleCardDto;
import ec.edu.unemi.unimart.dtos.article.ArticleDto;
import ec.edu.unemi.unimart.dtos.article.ProposedArticleDto;
import ec.edu.unemi.unimart.services.article.proposedArticle.IProposedArticleService;
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
@RequestMapping("/api/v1/proposedArticles")
public class ProposedArticleController {
    private final IProposedArticleService proposedArticleService;

    @PostMapping
    ResponseEntity<HttpHeaders> save(@RequestBody ProposedArticleDto proposedArticleDto) {
        UUID articleId = proposedArticleService.save(proposedArticleDto);
        return new ResponseEntity<>(this.getHttpHeaders(articleId), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    ResponseEntity<HttpHeaders> deleteById(@PathVariable UUID id) {
        proposedArticleService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("articles/{articleId}")
    ResponseEntity<List<ArticleDto>> proposedArticlesByArticleId(@PathVariable UUID articleId) {
        return ResponseEntity.ok(proposedArticleService.proposedArticlesByArticleId(articleId));
    }

    @GetMapping("users/{userId}")
    ResponseEntity<List<ArticleCardDto>> proposedArticlesByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(proposedArticleService.proposedArticlesByUserId(userId));
    }

    @GetMapping("users/{userId}/articles/{articleId}")
    ResponseEntity<Boolean> userHasMadeProposed(@PathVariable UUID userId, @PathVariable UUID articleId) {
        return ResponseEntity.ok(proposedArticleService.userHasMadeProposed(userId, articleId));
    }

    @GetMapping("{proposedArticleId}/article")
    ResponseEntity<ArticleDto> getArticleByProposedArticleId(@PathVariable UUID proposedArticleId) {
        return ResponseEntity.ok(proposedArticleService.getArticleByProposedArticleId(proposedArticleId));
    }

    private HttpHeaders getHttpHeaders(UUID userId) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userId).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return headers;
    }
}
