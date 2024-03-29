
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.MoneyExchangeRepository;
import acme.entities.course.Course;
import acme.entities.course.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseListService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository	repository;

	@Autowired
	protected MoneyExchangeRepository	moneyRepository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Course> objects;
		int lecturerId;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findManyCoursesByLecturerId(lecturerId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;
		Collection<Lecture> lectures;
		boolean isTheory = true;
		int theoryLectures;
		int handsOnLectures;

		lectures = this.repository.findManyLecturesByCourseId(object.getId());
		if (object.getDraftMode()) {
			theoryLectures = (int) lectures.stream().filter(l -> l.getIsTheory()).count();
			handsOnLectures = lectures.size() - theoryLectures;
			if (handsOnLectures >= theoryLectures)
				isTheory = false;
		} else
			isTheory = object.getIsTheory();

		tuple = super.unbind(object, "code", "title", "recap", "retailPrice", "link", "draftMode");
		tuple.put("isTheory", isTheory);

		super.getResponse().setData(tuple);
	}

}
