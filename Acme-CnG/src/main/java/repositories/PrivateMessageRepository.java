
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

	@Query("select count(m) from Actor a join a.sendedMessages m where m.copy=true group by a.id having count(m)<= ALL (select count(m1) from Actor a1 join a1.sendedMessages m1 where m1.copy=true group by a1.id))")
	Double minNumberMessagesSentPerActor();

	@Query("select count(m) from Actor a join a.sendedMessages m where m.copy=true group by a.id having count(m)>= ALL (select count(m1) from Actor a1 join a1.sendedMessages m1 where m1.copy=true group by a1.id)")
	Double maxNumberMessagesSentPerActor();

	@Query("select (count(m)*1.0)/(select count(a1)*1.0 from Actor a1) from Actor a join a.sendedMessages m where m.copy=true")
	Double averageNumberMessagesSentPerActor();

	@Query("select count(m) from Actor a join a.recivedMessages m where m.copy=false group by a.id having count(m)<= ALL (select count(m1) from Actor a1 join a1.recivedMessages m1 where m1.copy=false group by a1.id)")
	Double minNumberMessagesReceivedPerActor();

	@Query("select count(m) from Actor a join a.recivedMessages m where m.copy=false group by a.id having count(m)>= ALL (select count(m1) from Actor a1 join a1.recivedMessages m1 where m1.copy=false group by a1.id)")
	Double maxNumberMessagesReceivedPerActor();

	@Query("select (count(m)*1.0)/(select count(a1)*1.0 from Actor a1) from Actor a join a.recivedMessages m where m.copy=false")
	Double averageNumberMessagesReceivedPerActor();

}
