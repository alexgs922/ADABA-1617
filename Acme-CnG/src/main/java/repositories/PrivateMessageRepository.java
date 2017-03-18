
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PrivateMessage;

@Repository
public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Integer> {

	//customer 98 99 100
	//select m from Actor c join c.recivedMessages m;
	//select m from Actor c join c.sendedMessages m group by m.id where c.id=97;

	@Query("select m from Actor c join c.recivedMessages m where m.copy=false and c.id=?1")
	Collection<PrivateMessage> myRecivedMessages(int actorId);

	@Query("select m from Actor c join c.sendedMessages m where m.copy=true and c.id=?1")
	Collection<PrivateMessage> mySendedMessages(int actorId);

}
