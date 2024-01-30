package ec.edu.unemi.unimart.api.resources;

import ec.edu.unemi.unimart.api.dtos.ArticleDto;
import ec.edu.unemi.unimart.data.enums.Category;
import ec.edu.unemi.unimart.data.enums.State;
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
public class ArticleResource {

    private final IArticleService articleService;

    @GetMapping("{articleId}")
    ResponseEntity<ArticleDto> findById(@PathVariable UUID articleId) {
        return articleService.findById(articleId).map(articleDto -> new ResponseEntity<>(articleDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("{userId}")
    ResponseEntity<HttpHeaders> save(@PathVariable UUID userId, @RequestBody ArticleDto articleDto) {
        UUID articleId = articleService.save(userId, articleDto);
        return new ResponseEntity<>(
                HttpHeader.getHttpHeaders(articleId),
                HttpStatus.CREATED
        );
    }


    @PutMapping("{articleId}")
    ResponseEntity<HttpHeaders> update(@PathVariable UUID articleId, @RequestBody ArticleDto articleDto) {
        UUID id = articleService.update(articleId, articleDto);
        return new ResponseEntity<>(HttpHeader.getHttpHeaders(id), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    ResponseEntity<HttpHeaders> deleteById(@PathVariable UUID id) {
        articleService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("search")
    ResponseEntity<List<ArticleDto>> search(@RequestParam(required = false) String title, @RequestParam(required = false) Category category, @RequestParam(required = false) State state) {
        return ResponseEntity.ok(articleService.search(title, category, state));
    }

}
