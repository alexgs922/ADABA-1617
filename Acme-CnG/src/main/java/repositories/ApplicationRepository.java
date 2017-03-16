
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.customer.id = ?1")
	Collection<Application> findCustomerApplications(int customerId);

	@Query("select a from Application a where a.requestOffer.customer.id = ?1")
	Collection<Application> findCustomerRequestOfferApplications(int customerID);

}
