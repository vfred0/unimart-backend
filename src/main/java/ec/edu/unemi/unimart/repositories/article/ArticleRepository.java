package ec.edu.unemi.unimart.repositories.article;

import ec.edu.unemi.unimart.dtos.ArticleDto;
import ec.edu.unemi.unimart.mappers.IArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class ArticleRepository implements IArticleRepository {
    private final IArticleCrudRepository articleCrudRepository;
    private final IArticleMapper articleMapper;

    @Override
    public List<ArticleDto> getAll() {
        return this.articleMapper.toArticlesDto( this.articleCrudRepository.findAll());
    }

    @Override
    public UUID save(ArticleDto articleDto) {
        return this.articleCrudRepository.save(this.articleMapper.toArticleEntity(articleDto)).getId();
    }
}
