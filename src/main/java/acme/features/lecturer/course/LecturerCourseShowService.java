
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.components.RateRepository;
import acme.entities.configuration.Configuration;
import acme.entities.course.Course;
import acme.entities.course.Lecture;
import acme.framework.components.datatypes.Money;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseShowService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository	repository;

	@Autowired
	protected ConfigurationRepository	configuration;

	@Autowired
	protected RateRepository			rateRepository;


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
		status = course != null && super.getRequest().getPrincipal().hasRole(course.getLecturer());

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
		int id;
		Collection<Lecture> lectures;
		boolean isTheory = true;
		int theoryLectures;
		int handsOnLectures;
		Configuration config;
		String moneda;
		Double rate;
		final Money cambio = new Money();

		config = this.configuration.getSystemConfiguration().iterator().next();
		moneda = config.getCurrency();

		id = super.getRequest().getData("id", int.class);
		lectures = this.repository.findManyLecturesByCourseId(id);
		if (object.getDraftMode()) {
			theoryLectures = (int) lectures.stream().filter(l -> l.getIsTheory()).count();
			handsOnLectures = lectures.size() - theoryLectures;
			if (handsOnLectures >= theoryLectures)
				isTheory = false;
		} else
			isTheory = object.getIsTheory();

		this.rateRepository.getRate();

		rate = this.rateRepository.rate(object.getRetailPrice().getCurrency(), moneda);

		cambio.setAmount(rate * object.getRetailPrice().getAmount());
		cambio.setCurrency(moneda);

		tuple = super.unbind(object, "code", "title", "recap", "retailPrice", "link", "draftMode");
		tuple.put("id", object.getId());
		tuple.put("isTheory", isTheory);
		tuple.put("exchange", cambio);

		super.getResponse().setData(tuple);
	}

}
