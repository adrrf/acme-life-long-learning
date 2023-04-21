
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.components.MoneyExchangeRepository;
import acme.entities.configuration.Configuration;
import acme.entities.course.Course;
import acme.entities.course.Lecture;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCoursePublishService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository	repository;

	@Autowired
	protected ConfigurationRepository	configuration;


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
		status = course != null && course.getDraftMode() && super.getRequest().getPrincipal().hasRole(course.getLecturer());

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
	public void bind(final Course object) {
		assert object != null;

	}

	@Override
	public void validate(final Course object) {
		assert object != null;

		Collection<Lecture> lectures;
		boolean publish;

		lectures = this.repository.findManyLecturesByCourseId(object.getId());
		publish = lectures.stream().anyMatch(l -> l.getDraftMode() == true);

		if (lectures.isEmpty())
			super.state(false, "*", "lecturer.course.form.error.empty");

		if (publish)
			super.state(!publish, "*", "lecturer.course.form.error.publish");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Course existing;

			existing = this.repository.findeOneCourseByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "lecturer.course.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("retailPrice")) {
			Configuration configuration;

			configuration = this.repository.findConfiguration();

			super.state(configuration.getAcceptedCurrencies().contains(object.getRetailPrice().getCurrency()), "retailPrice", "lecturer.course.form.error.currency-retailprice" + configuration.getAcceptedCurrencies());
		}

		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(object.getRetailPrice().getAmount() > 0, "retailPrice", "lecturer.course.form.error.negative-retailprice");

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			boolean status;
			String message;

			message = object.getTitle();
			status = this.configuration.hasSpam(message);

			super.state(!status, "title", "lecturer.course.form.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("recap")) {
			boolean status;
			String message;

			message = object.getRecap();
			status = this.configuration.hasSpam(message);

			super.state(!status, "recap", "lecturer.course.form.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("isTheory")) {
			boolean status;

			lectures = this.repository.findManyLecturesByCourseId(object.getId());
			status = !lectures.stream().anyMatch(l -> l.getIsTheory() == false);

			super.state(!status, "isTheory", "lecturer.course.form.error-isTheory");

		}
	}

	@Override
	public void perform(final Course object) {
		assert object != null;
		int theoryLectures;
		int handsOnLectures;
		int id;
		boolean isTheory = true;
		Collection<Lecture> lectures;

		id = super.getRequest().getData("id", int.class);
		lectures = this.repository.findManyLecturesByCourseId(id);

		object.setDraftMode(false);

		theoryLectures = (int) lectures.stream().filter(l -> l.getIsTheory()).count();
		handsOnLectures = lectures.size() - theoryLectures;
		if (handsOnLectures >= theoryLectures)
			isTheory = false;
		object.setIsTheory(isTheory);

		this.repository.save(object);
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
		MoneyExchange exchange;
		Configuration config;
		String moneda;

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

		exchange = MoneyExchangeRepository.computeMoneyExchange(object.getRetailPrice(), moneda);

		tuple = super.unbind(object, "code", "title", "recap", "retailPrice", "link", "draftMode");

		tuple.put("id", object.getId());
		tuple.put("isTheory", isTheory);
		tuple.put("exchange", exchange.getTarget());

		super.getResponse().setData(tuple);
	}
}
