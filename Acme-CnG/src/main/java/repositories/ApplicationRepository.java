
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

}
