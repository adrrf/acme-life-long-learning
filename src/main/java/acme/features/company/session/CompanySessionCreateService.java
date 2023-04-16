
package acme.features.company.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.Session;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionCreateService extends AbstractService<Company, Session> {

	@Autowired
	protected CompanySessionRepository repository;

	//	@Autowired
	//	protected ConfigurationRepository	configuration;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		status = practicum != null && practicum.getDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany()) && super.getRequest().getPrincipal().getUsername().equals(practicum.getCompany().getUserAccount().getUsername());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Session object;
		Practicum practicum;
		int practicumId;

		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		object = new Session();
		object.setPracticum(practicum);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Session object) {
		assert object != null;

		Practicum practicum;
		int practicumId;

		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);

		super.bind(object, "title", "recap", "startTime", "endTime", "link");
		object.setPracticum(practicum);

	}

	@Override
	public void validate(final Session object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startTime") && !super.getBuffer().getErrors().hasErrors("endTime"))
			if (!MomentHelper.isBefore(object.getStartTime(), object.getEndTime()))
				super.state(false, "endTime", "company.session.form.error.end-before-start");
			else {
				final int days = (int) MomentHelper.computeDuration(object.getStartTime(), MomentHelper.getCurrentMoment()).toDays();
				if (days < 7)
					super.state(false, "startTime", "company.session.form.error.day-ahead");
				else {
					final int dias = (int) MomentHelper.computeDuration(object.getStartTime(), object.getEndTime()).toDays();
					if (!(7 <= dias))
						super.state(false, "endTime", "company.session.form.error.duration");
				}
			}
	}

	@Override
	public void perform(final Session object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Session object) {
		assert object != null;

		Practicum practicum;
		int practicumId;

		Tuple tuple;

		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		tuple = super.unbind(object, "title", "recap", "startTime", "endTime", "link");
		tuple.put("practicum", practicum);
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("draftMode", object.getPracticum().getDraftMode());

		super.getResponse().setData(tuple);
	}

}
