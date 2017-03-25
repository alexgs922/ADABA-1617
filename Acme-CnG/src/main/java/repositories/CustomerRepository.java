
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id = ?1")
	Customer findByUserAccountId(int userAccountId);

	@Query("select c from Customer c join c.applications a where a.status = 0 group by c having count(a) >= ALL(select count(a2) from Customer c2 join c2.applications a2 group by c2)")
	Collection<Customer> findCustomersWithMoreAcceptedApplications();

	@Query("select c from Customer c join c.applications a where a.status = 2 group by c having count(a) >= ALL(select count(a2) from Customer c2 join c2.applications a2 group by c2)")
	Collection<Customer> findCustomersWithMoreDeniedApplications();

	@Query("select c from Customer c where c.userAccount.username = ?1")
	Customer findCustomerByUsername(String username);

}
