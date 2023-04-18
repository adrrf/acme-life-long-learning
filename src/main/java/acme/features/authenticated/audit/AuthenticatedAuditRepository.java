
package acme.features.authenticated.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditingRecord;
import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAuditRepository extends AbstractRepository {

	@Query("select a from Audit a where a.draftMode = false and a.course.draftMode = false and a.course.id = :courseId")
	Collection<Audit> findManyAudit(int courseId);

	@Query("select a from Audit a where a.id = :auditId")
	Audit findOneAuditById(int auditId);

	@Query("select c from Course c where c.id = :courseId")
	Course findOneCourseById(int courseId);

	@Query("select au from AuditingRecord au where au.audit.id = :id")
	Collection<AuditingRecord> findManyAuditingRecordByAuditId(int id);
}
