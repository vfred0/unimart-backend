package ec.edu.unemi.unimart.data.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IRepository<M, ID> extends JpaRepository<M, ID> {
}
