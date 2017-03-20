
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	//	@Query("select c from Comment c where c.recipient.id=?1")
	//	Collection<Comment> findCommentsByActorId(int actorId);

	@Query("select avg(r.commentsReceived.size)from RequestOffer r where r.requestOrOffer=0")
	Double findAverageNumberCommentsPerRequest();

	@Query("select avg(r.commentsReceived.size)from RequestOffer r where r.requestOrOffer=1")
	Double findAverageNumberCommentsPerOffer();

	@Query("select avg(a.commentsReceived.size)from Actor a")
	Double findAverageNumberCommentsPerActor();

	@Query("select avg(c.commentsSent.size) from Customer c")
	Double averageNumberCommentsPostedByCustomer();

	@Query("select avg(c.commentsSent.size) from Administrator c")
	Double averageNumberCommentsPostedByAdministrator();

}
