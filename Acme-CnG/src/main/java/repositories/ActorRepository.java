
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccountId(int userAccountId);

	@Query("select a from Actor a where a.commentsSent.size >= (select avg(c.commentsSent.size) from Actor c)*0.1")
	Collection<Actor> findActorsWhoPostedPlus10PercentOfAverageNumberOfComments();

	@Query("select a from Actor a where a.commentsSent.size <= (select avg(c.commentsSent.size) from Actor c)*0.1")
	Collection<Actor> findActorsWhoPostedLess10PercentOfAverageNumberOfComments();

	@Query("select a from Actor a join a.sendedMessages m where m.copy=true group by a.id having count(m)>= ALL (select count(m1) from Actor a1 join a1.sendedMessages m1 where m1.copy=true group by a1.id)")
	Collection<Actor> findActorwithMoreMessagesSent();

	@Query("select a from Actor a join a.recivedMessages m where m.copy=false group by a.id having count(m)>= ALL (select count(m1) from Actor a1 join a1.recivedMessages m1 where m1.copy=false group by a1.id)")
	Collection<Actor> findActorWithMoreMessagesReceived();

}
