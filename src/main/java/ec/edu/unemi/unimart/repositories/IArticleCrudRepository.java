package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.entities.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IArticleCrudRepository extends JpaRepository<ArticleEntity, UUID> {
}
