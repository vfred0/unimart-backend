package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.models.ProposedArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProposedArticleRepository extends JpaRepository<ProposedArticle, UUID> {

}