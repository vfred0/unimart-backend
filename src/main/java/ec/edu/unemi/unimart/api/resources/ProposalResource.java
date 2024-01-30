package ec.edu.unemi.unimart.api.resources;

import ec.edu.unemi.unimart.api.dtos.ArticleDto;
import ec.edu.unemi.unimart.api.dtos.ProposalDto;
import ec.edu.unemi.unimart.services.article.IArticleService;
import ec.edu.unemi.unimart.services.proposal.IProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/proposals")
public class ProposalResource {

    private final IProposalService proposedArticleService;
    private final IArticleService articleService;

    @PostMapping
    ResponseEntity<HttpHeaders> save(@RequestBody ProposalDto proposalDto) {
        UUID id = proposedArticleService.save(proposalDto);
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
