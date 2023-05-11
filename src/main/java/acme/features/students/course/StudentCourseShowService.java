
package acme.features.students.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;
import acme.roles.Student;

@Service
public class StudentCourseShowService extends AbstractService<Student, Course> {

	@Autowired
	protected StudentCourseRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(courseId);
		status = course != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;
		Lecturer lr;
		//final Collection<Lecture> lectures = this.repository.findManyLecturesByCourseId(object.getId());

		tuple = super.unbind(object, "code", "title", "recap", "retailPrice", "link", "draftMode");
		//tuple.put("lectures", lectures);
		lr = this.repository.findOneLecturerById(object.getLecturer().getId());
		tuple.put("lecturerId", lr.getId());

		super.getResponse().setData(tuple);
	}

}
