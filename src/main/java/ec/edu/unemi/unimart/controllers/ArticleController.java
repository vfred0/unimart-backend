package ec.edu.unemi.unimart.controllers;

import ec.edu.unemi.unimart.dtos.article.ArticleDto;
import ec.edu.unemi.unimart.dtos.article.ArticleCardDto;
import ec.edu.unemi.unimart.dtos.article.ProposedArticleDto;
import ec.edu.unemi.unimart.models.enums.Category;
import ec.edu.unemi.unimart.models.enums.State;
import ec.edu.unemi.unimart.services.article.IArticleService;
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
@RequestMapping("/api/v1/articles")
public class ArticleController {
    private final IArticleService articleService;

    @GetMapping
    ResponseEntity<List<ArticleDto>> getAll() {
        return ResponseEntity.ok(articleService.getAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<ArticleDto> findById(@PathVariable UUID id) {
        return articleService.findById(id).map(articleDto -> new ResponseEntity<>(articleDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/user/{id}")
    ResponseEntity<List<ArticleCardDto>> findByUserId(@PathVariable UUID id) {
        return ResponseEntity.ok(articleService.findByUserId(id));
    }

    @PostMapping
    ResponseEntity<HttpHeaders> save(@RequestBody ArticleDto articleDto) {
        UUID articleId = articleService.save(articleDto).getId();
        return new ResponseEntity<>(this.getHttpHeaders(articleId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<HttpHeaders> update(@PathVariable UUID id, @RequestBody ArticleDto articleDto) {
        UUID articleId = articleService.update(id, articleDto).getId();
        return new ResponseEntity<>(this.getHttpHeaders(articleId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpHeaders> delete(@PathVariable UUID id) {
        articleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    ResponseEntity<List<ArticleDto>> search(@RequestParam(required = false) String title, @RequestParam(required = false) String category, @RequestParam(required = false) String state) {
        return ResponseEntity.ok(articleService.search(title, Category.byName(category), State.byName(state)));
    }

    @GetMapping("/proposedArticles/articles/{articleId}")
    ResponseEntity<List<ArticleCardDto>> proposedArticlesByArticleId(@PathVariable UUID articleId) {
        return ResponseEntity.ok(articleService.proposedArticlesByArticleId(articleId));
    }

    @DeleteMapping("/proposedArticles/articles/{articleId}/delete/{proposedArticleId}")
    ResponseEntity<HttpHeaders> deleteProposedArticleByArticleId(@PathVariable UUID articleId, @PathVariable UUID proposedArticleId) {
        articleService.deleteProposedArticleByArticleId(articleId, proposedArticleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/proposedArticles/users/{userId}")
    ResponseEntity<List<ArticleCardDto>> proposedArticlesByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(articleService.proposedArticlesByUserId(userId));
    }

    @PostMapping("proposedArticles")
    ResponseEntity<HttpHeaders> addProposedArticle(@RequestBody ProposedArticleDto proposedArticleDto) {
        UUID articleId = articleService.addProposedArticle(proposedArticleDto);
        return new ResponseEntity<>(this.getHttpHeaders(articleId), HttpStatus.CREATED);
    }

    @DeleteMapping("proposedArticles/{id}")
    ResponseEntity<HttpHeaders> deleteProposedArticleById(@PathVariable UUID id) {
        articleService.deleteProposedArticleById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    private HttpHeaders getHttpHeaders(UUID userId) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userId).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return headers;
    }
}
