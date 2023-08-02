package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.article.ArticleDto;
import ec.edu.unemi.unimart.models.enums.Category;
import ec.edu.unemi.unimart.models.enums.State;
import ec.edu.unemi.unimart.services.crud.ICrudService;

import javax.net.ssl.SSLSession;
import java.util.List;
import java.util.UUID;

public interface IArticleService extends ICrudService<ArticleDto, UUID> {
    ArticleDto save(UUID userId, ArticleDto articleDto);

    List<ArticleDto> findByUserId(UUID id);

//   @ UUID update(UUID userId, ArticleDto articleDto);
//    List<ArticleDto> search(String title, Category category, State state);


}
