
package acme.features.students.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.course.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentLectureListService extends AbstractService<Student, Lecture> {

	@Autowired
	protected StudentLectureRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		Collection<Lecture> objects;
		Course course;

		id = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyLecturesByCourseId(id);
		course = this.repository.findOneCourseById(id);

		super.getBuffer().setData(objects);
		super.getResponse().setGlobal("courseDraftMode", course.getDraftMode());
		super.getResponse().setGlobal("masterId", id);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		int id;
		Tuple tuple;
		Course course;

		id = super.getRequest().getData("masterId", int.class);
		course = this.repository.findOneCourseById(id);

		tuple = super.unbind(object, "title", "recap", "learningTime", "body", "isTheory", "link");
		tuple.put("lectureId", object.getId());
		tuple.put("course", course);
		tuple.put("masterId", id);

		super.getResponse().setGlobal("courseDraftMode", course.getDraftMode());
		super.getResponse().setGlobal("masterId", id);
		super.getResponse().setData(tuple);
	}

}
