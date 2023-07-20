package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.ArticleDto;
import ec.edu.unemi.unimart.services.crud.ICrudService;

import java.util.UUID;

public interface IArticleService extends ICrudService<ArticleDto, UUID> {
}
