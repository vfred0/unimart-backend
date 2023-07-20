package ec.edu.unemi.unimart.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IRepository<E, ID> extends JpaRepository<E, ID> {
}
