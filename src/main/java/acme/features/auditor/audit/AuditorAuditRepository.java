
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditingRecord;
import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

	@Query("select a from Audit a where a.auditor.id = :id")
	Collection<Audit> findManyAuditsByAuditorId(int id);

	@Query("select a from Audit a where a.id = :id")
	Audit findOneAuditById(int id);

	@Query("select au from Auditor au where au.id = :id")
	Auditor findOneAuditorById(int id);

	@Query("select a from Audit a where a.code = :code")
	Audit findOneAuditByCode(String code);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select aud from AuditingRecord aud where aud.audit.id = :id")
	Collection<AuditingRecord> findManyAuditingRecordByAuditId(int id);

}
