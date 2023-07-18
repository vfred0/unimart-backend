package ec.edu.unemi.unimart.services;

import ec.edu.unemi.unimart.dtos.ArticleDto;
import ec.edu.unemi.unimart.repositories.IArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
