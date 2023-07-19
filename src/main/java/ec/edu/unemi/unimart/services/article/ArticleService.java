package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.ArticleDto;
import ec.edu.unemi.unimart.repositories.article.IArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class ArticleService implements IArticleService {
    private final IArticleRepository articleRepository;

    @Override
    public List<ArticleDto> getAll() {
        Logger.getLogger(ArticleService.class.getName()).log(Level.INFO, "getAll");
        return this.articleRepository.getAll();
    }

    @Override
    public UUID save(ArticleDto articleDto) {
        return this.articleRepository.save(articleDto);
    }
}
