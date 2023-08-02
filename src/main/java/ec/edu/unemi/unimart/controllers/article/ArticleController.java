package ec.edu.unemi.unimart.controllers.article;

import ec.edu.unemi.unimart.controllers.HttpHeader;
import ec.edu.unemi.unimart.dtos.article.ArticleDto;
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

    @GetMapping("{articleId}")
    ResponseEntity<ArticleDto> findById(@PathVariable UUID articleId) {
        return articleService.findById(articleId).map(articleDto -> new ResponseEntity<>(articleDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("users/{id}")
    ResponseEntity<List<ArticleDto>> findByUserId(@PathVariable UUID id) {
        return ResponseEntity.ok(articleService.findByUserId(id));
    }

    @PostMapping("{userId}")
    ResponseEntity<HttpHeaders> save(@PathVariable UUID userId, @RequestBody ArticleDto articleDto) {
        UUID articleId = articleService.save(userId, articleDto).getId();
        return new ResponseEntity<>(HttpHeader.getHttpHeaders(articleId), HttpStatus.CREATED);
    }

    @PutMapping("{articleId}")
    ResponseEntity<HttpHeaders> update(@PathVariable UUID articleId, @RequestBody ArticleDto articleDto) {
        UUID id = articleService.update(articleId, articleDto).getId();
        return new ResponseEntity<>(HttpHeader.getHttpHeaders(id), HttpStatus.CREATED);
    }
//
    @DeleteMapping("{id}")
    ResponseEntity<HttpHeaders> delete(@PathVariable UUID id) {
        articleService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//
//    @GetMapping("search")
//    ResponseEntity<List<ArticleDto>> search(@RequestParam(required = false) String title, @RequestParam(required = false) String category, @RequestParam(required = false) String state) {
//        return ResponseEntity.ok(articleService.search(title, Category.byName(category), State.byName(state)));
//    }

}
