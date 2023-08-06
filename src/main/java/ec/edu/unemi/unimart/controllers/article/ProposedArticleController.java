package ec.edu.unemi.unimart.controllers.article;

import ec.edu.unemi.unimart.controllers.HttpHeader;
import ec.edu.unemi.unimart.dtos.article.ArticleDto;
import ec.edu.unemi.unimart.dtos.article.ProposedArticleDto;
import ec.edu.unemi.unimart.services.article.IArticleService;
import ec.edu.unemi.unimart.services.proposedArticle.IProposedArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/proposedArticles")
public class ProposedArticleController {
    private final IProposedArticleService proposedArticleService;
    private final IArticleService articleService;

    @PostMapping
    ResponseEntity<HttpHeaders> save(@RequestBody ProposedArticleDto proposedArticleDto) {
        UUID id = proposedArticleService.save(proposedArticleDto);
        return new ResponseEntity<>(HttpHeader.getHttpHeaders(id), HttpStatus.CREATED);
    }

    @DeleteMapping("{proposerArticleId}")
    ResponseEntity<HttpHeaders> deleteByProposerArticleId(@PathVariable UUID proposerArticleId) {
        proposedArticleService.deleteByProposerArticleId(proposerArticleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{articleId}")
    ResponseEntity<List<ArticleDto>> proposedArticlesByArticleId(@PathVariable UUID articleId) {
        return ResponseEntity.ok(articleService.proposerArticlesByReceiverArticleId(articleId));
    }
}
