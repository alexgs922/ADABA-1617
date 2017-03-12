
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.RequestOffer;

@Repository
public interface RequestOfferRepository extends JpaRepository<RequestOffer, Integer> {

}
