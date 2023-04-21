
package acme.features.students.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.enrolment.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

	@Query("select a from Activity a where a.enrolment.id = :id")
	Collection<Activity> findManyActivitysByEnrolmentId(int id);

	@Query("select e from Enrolment e where e.id = :id")
	Enrolment findOneEnrolmentById(int id);

	@Query("select a from Activity a where a.id = :id")
	Activity findOneActivityById(int id);

	@Query("select a.enrolment from Activity a where a.id = :id")
	Enrolment findOneActivityByEnrolmentId(int id);

}
