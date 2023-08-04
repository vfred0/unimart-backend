package ec.edu.unemi.unimart.controllers;

import ec.edu.unemi.unimart.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ec.edu.unemi.unimart.dtos.UserDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final IUserService userService;

    @GetMapping
    ResponseEntity<List<UserDto>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<HttpHeaders> save(@RequestBody UserDto userDto) {
        UUID articleId = userService.save(userDto).getId();
        return new ResponseEntity<>(HttpHeader.getHttpHeaders(articleId), HttpStatus.CREATED);
    }

    @PutMapping("{userId}")
    ResponseEntity<HttpHeaders> update(@PathVariable UUID userId, @RequestBody UserDto userDto) {
        UUID articleId = userService.update(userId, userDto).getId();
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
