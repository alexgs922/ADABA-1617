package repositories;

import java.util.Collection;

import org.junit.runner.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.RequestOffer;

@Repository
public interface RequestOfferRepository extends
		JpaRepository<RequestOffer, Integer> {

	@Query("select ro from RequestOffer ro where ro.requestOrOffer = 0")
	Collection<RequestOffer> findAllRequest();

	@Query("select ro from RequestOffer ro where ro.requestOrOffer = 1")
	Collection<RequestOffer> findAllOffers();

	@Query("select ro from RequestOffer ro where ro.requestOrOffer = 0 and ro.customer.id=?1 and ro.banned=false")
	Collection<RequestOffer> findAllRequestFromPrincipal(int principalid);

	@Query("select ro from RequestOffer ro where ro.requestOrOffer = 1 and ro.customer.id=?1 and ro.banned=false")
	Collection<RequestOffer> findAllOffersFromPrincipal(int principalid);

	@Query("select ro from RequestOffer ro where ro.requestOrOffer = 0 and ro.banned=false")
	Collection<RequestOffer> findAllRequestNotBanned();

	@Query("select ro from RequestOffer ro where ro.requestOrOffer = 1 and ro.banned=false")
	Collection<RequestOffer> findAllOffersNotBanned();

	@Query("select ro from RequestOffer ro where ro.customer.id=?1 and ro.banned=true")
	Collection<RequestOffer> findAllBannedRequestsOffersFromPrincipal(
			int principalid);

	@Query("select count(ro)*1.0 /(select count(rr)*1.0 from RequestOffer rr where rr.requestOrOffer=0)from RequestOffer ro where ro.requestOrOffer=1")
	Double ratioOffersVsRequest();

	@Query("select avg(o.size) from Customer c join c.requestsOffers o where o.requestOrOffer = 1")
	Double averageNumberOfOffersPerCustomer();

	@Query("select avg(o.size) from Customer c join c.requestsOffers o where o.requestOrOffer = 0")
	Double averageNumberOfRequestPerCustomer();

	@Query("select r from RequestOffer r where r.title like %?1% or r.description like %?1% or r.originPlace.address like %?1% or r.destinationPlace.address like %?1%")
	Collection<RequestOffer> searchByKeyword(String keyword);
}
