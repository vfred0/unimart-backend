package ec.edu.unemi.unimart.api.resources;

import ec.edu.unemi.unimart.api.dtos.ArticleDto;
import ec.edu.unemi.unimart.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ec.edu.unemi.unimart.api.dtos.UserDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserResource {

    private final IUserService userService;

    @GetMapping("{id}/articles")
    ResponseEntity<List<ArticleDto>> getArticlesByUserId(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getArticlesByUserId(id));
    }

    @PostMapping
    ResponseEntity<HttpHeaders> save(@RequestBody UserDto userDto) {
        UUID articleId = userService.save(userDto);
        return new ResponseEntity<>(HttpHeader.getHttpHeaders(articleId), HttpStatus.CREATED);
    }

    @PutMapping("{userId}")
    ResponseEntity<HttpHeaders> update(@PathVariable UUID userId, @RequestBody UserDto userDto) {
        UUID articleId = userService.update(userId, userDto);
        return new ResponseEntity<>(HttpHeader.getHttpHeaders(articleId), HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
    ResponseEntity<Void> delete(@PathVariable UUID userId) {
        userService.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}")
    ResponseEntity<UserDto> findById(@PathVariable UUID id) {
        return userService.findById(id)
                .map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
