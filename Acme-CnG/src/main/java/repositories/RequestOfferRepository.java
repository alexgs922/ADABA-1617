
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.RequestOffer;

@Repository
public interface RequestOfferRepository extends JpaRepository<RequestOffer, Integer> {

	@Query("select ro from RequestOffer ro where ro.requestOrOffer = 0")
	Collection<RequestOffer> findAllRequest();

	@Query("select ro from RequestOffer ro where ro.requestOrOffer = 1")
	Collection<RequestOffer> findAllOffers();

}
