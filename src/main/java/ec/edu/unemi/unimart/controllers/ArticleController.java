package ec.edu.unemi.unimart.controllers;

import ec.edu.unemi.unimart.dtos.ArticleDto;
import ec.edu.unemi.unimart.services.article.IArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    ResponseEntity<HttpHeaders> save(@RequestBody ArticleDto articleDto) {
        UUID articleId = articleService.save(articleDto);
        return new ResponseEntity<>(this.getHttpHeaders(articleId), HttpStatus.CREATED);
    }

    private HttpHeaders getHttpHeaders(UUID articleId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/articles/" + articleId);
        return headers;
    }
}
