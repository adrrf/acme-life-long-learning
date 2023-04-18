
package acme.features.company.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.practicum.Practicum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumUpdateService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository	repository;

	@Autowired
	protected ConfigurationRepository		configuration;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		status = practicum != null && practicum.getDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;

		super.bind(object, "code", "title", "recap", "goals", "draftMode");
		object.setDraftMode(true);
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
			super.state(practicum == null || practicum.equals(object), "code", "company.practicum.form.error.duplicated-code");
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

		tuple = super.unbind(object, "code", "title", "recap", "goals", "draftMode");

		super.getResponse().setData(tuple);
	}

}
