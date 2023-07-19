package ec.edu.unemi.unimart.mappers;

import ec.edu.unemi.unimart.dtos.ArticleDto;
import ec.edu.unemi.unimart.entities.ArticleEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IArticleMapper {
    List<ArticleDto> toArticlesDto(List<ArticleEntity> articles);

    @InheritInverseConfiguration
    // save name of TypeArticleEntity in ArticleDto
    @Mapping(target = "typeArticle", source = "typeArticle.name")
    ArticleEntity toArticleEntity(ArticleDto article);
}
