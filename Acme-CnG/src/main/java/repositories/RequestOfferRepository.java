
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

	@Query("select ro from RequestOffer ro where ro.requestOrOffer = 0 and ro.customer.id=?1 and ro.banned=false")
	Collection<RequestOffer> findAllRequestFromPrincipal(int principalid);

	@Query("select ro from RequestOffer ro where ro.requestOrOffer = 1 and ro.customer.id=?1 and ro.banned=false")
	Collection<RequestOffer> findAllOffersFromPrincipal(int principalid);

	@Query("select ro from RequestOffer ro where ro.customer.id=?1 and ro.banned=true")
	Collection<RequestOffer> findAllBannedRequestsOffersFromPrincipal(int principalid);

}
