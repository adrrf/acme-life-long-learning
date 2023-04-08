
package acme.features.lecturers.lectures;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureListService extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int id;
		Collection<Lecture> objects;

		id = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyLecturesByCourseId(id);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		int id;
		Tuple tuple;

		id = super.getRequest().getData("masterId", int.class);
		tuple = super.unbind(object, "title", "recap", "learningTime", "body", "isTheory", "link");
		tuple.put("lectureId", object.getId());

		super.getResponse().setData(tuple);
	}

}
