
package acme.features.any.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.components.MoneyExchangeRepository;
import acme.entities.configuration.Configuration;
import acme.entities.course.Course;
import acme.entities.course.Lecture;
import acme.forms.MoneyExchange;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;
import acme.roles.Auditor;

@Service
public class AnyCourseShowService extends AbstractService<Any, Course> {

	@Autowired
	protected AnyCourseRepository		repository;

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

		final Tuple tuple;
		int id;
		Collection<Lecture> lectures;
		boolean isTheory = true;
		final int theoryLectures;
		final int handsOnLectures;
		final MoneyExchange exchange;
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
		tuple.put("isTheory", isTheory);
		tuple.put("exchange", exchange.getTarget());
		tuple.put("isAssistant", super.getRequest().getPrincipal().hasRole(Assistant.class));
		tuple.put("isAuditor", super.getRequest().getPrincipal().hasRole(Auditor.class));
		super.getResponse().setData(tuple);
	}

}
