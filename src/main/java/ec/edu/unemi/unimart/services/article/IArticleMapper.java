package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.ArticleDto;
import ec.edu.unemi.unimart.models.Article;
import ec.edu.unemi.unimart.utils.Category;
import ec.edu.unemi.unimart.utils.Gender;
import ec.edu.unemi.unimart.utils.State;
import ec.edu.unemi.unimart.utils.TypeArticle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface IArticleMapper {

    @Mapping(target = "category", qualifiedByName = "mapCategory")
    @Mapping(target = "state", qualifiedByName = "mapState")
    @Mapping(target = "gender", qualifiedByName = "mapGender")
    @Mapping(target = "typeArticle", qualifiedByName = "mapTypeArticle")
    Article toModel(ArticleDto article);


    @Named("mapCategory")
    default Category mapCategory(String categoryName) {
        return Category.byName(categoryName);
    }

    @Named("mapState")
    default State mapState(String stateName) {
        return State.byName(stateName);
    }

    @Named("mapGender")
    default Gender mapGender(String genderName) {
        return (genderName != null) ? Gender.byName(genderName) : null;
    }

    @Named("mapTypeArticle")
    default TypeArticle mapTypeArticle(String typeArticleName) {
        return TypeArticle.byName(typeArticleName);
    }

    ArticleDto toDto(Article article);
}
