
package acme.entities.audit;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AuditRepository extends CrudRepository<Audit, Integer> {

	@Override
	List<Audit> findAll();

}
