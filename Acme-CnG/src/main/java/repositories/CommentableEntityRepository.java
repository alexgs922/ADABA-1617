
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CommentableEntity;

@Repository
public interface CommentableEntityRepository extends JpaRepository<CommentableEntity, Integer> {

	@Query("select c from CommentableEntity c where c.id =?1")
	CommentableEntity findOneAlternativo(int id);
	
}
