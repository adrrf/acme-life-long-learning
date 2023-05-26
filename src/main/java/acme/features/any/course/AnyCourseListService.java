
package acme.features.any.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.course.Lecture;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyCourseListService extends AbstractService<Any, Course> {

	@Autowired
	protected AnyCourseRepository repository;


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
		Collection<Course> objects;

		objects = this.repository.findManyCourse();

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
