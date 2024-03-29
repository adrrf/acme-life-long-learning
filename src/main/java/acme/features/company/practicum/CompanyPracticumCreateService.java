
package acme.features.company.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.course.Course;
import acme.entities.practicum.Practicum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumCreateService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository	repository;

	@Autowired
	protected ConfigurationRepository		configuration;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("courseId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		status = course != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		Company company;
		int courseId;
		Course course;

		object = new Practicum();
		company = this.repository.findOneCompanyById(super.getRequest().getPrincipal().getActiveRoleId());
		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		object.setDraftMode(true);
		object.setCompany(company);
		object.setCourse(course);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;

		int companyId;
		Company company;
		int courseId;
		Course course;

		companyId = super.getRequest().getPrincipal().getActiveRoleId();
		company = this.repository.findOneCompanyById(companyId);
		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);

		super.bind(object, "code", "title", "recap", "goals", "draftMode");
		object.setCompany(company);
		object.setDraftMode(true);
		object.setCourse(course);
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			boolean status;
			String message;

			message = object.getTitle();
			status = this.configuration.hasSpam(message);

			super.state(!status, "title", "company.practicum.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("recap")) {
			boolean status;
			String message;

			message = object.getRecap();
			status = this.configuration.hasSpam(message);

			super.state(!status, "recap", "company.practicum.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("goals")) {
			boolean status;
			String message;

			message = object.getGoals();
			status = this.configuration.hasSpam(message);

			super.state(!status, "goals", "company.practicum.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Practicum practicum;
			String code;

			code = object.getCode();
			practicum = this.repository.findOnePracticumByCode(code);
			super.state(practicum == null, "code", "company.practicum.form.error.duplicated-code");
		}
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;
		int companyId;
		Company company;
		int courseId;
		Course course;

		companyId = super.getRequest().getPrincipal().getActiveRoleId();
		company = this.repository.findOneCompanyById(companyId);
		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		tuple = super.unbind(object, "code", "title", "recap", "goals", "draftMode");
		tuple.put("totalTime", 0);
		tuple.put("courseId", super.getRequest().getData("courseId", int.class));
		tuple.put("company", company);
		tuple.put("course", course);

		super.getResponse().setData(tuple);
	}

}
