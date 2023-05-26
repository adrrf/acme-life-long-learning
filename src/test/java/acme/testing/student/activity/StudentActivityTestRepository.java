
package acme.testing.student.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.enrolment.Activity;
import acme.framework.repositories.AbstractRepository;

public interface StudentActivityTestRepository extends AbstractRepository {

	@Query("select a from Activity a where a.enrolment.student.userAccount.username = :username")
	Collection<Activity> findManyActivitiesByStudentUsername(String username);

	@Query("select a.id from Activity a where a.title = :title")
	int findIdByTitle(String title);

}
