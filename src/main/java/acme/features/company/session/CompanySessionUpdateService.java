
package acme.features.company.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.practicum.Session;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionUpdateService extends AbstractService<Company, Session> {

	@Autowired
	protected CompanySessionRepository	repository;

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
		int sessionId;
		Session session;

		sessionId = super.getRequest().getData("id", int.class);
		session = this.repository.findOneSessionById(sessionId);
		status = session != null && session.getPracticum().getDraftMode() && super.getRequest().getPrincipal().hasRole(session.getPracticum().getCompany())
			&& super.getRequest().getPrincipal().getUsername().equals(session.getPracticum().getCompany().getUserAccount().getUsername());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Session object;
		int sessionId;

		sessionId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSessionById(sessionId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Session object) {
		assert object != null;

		super.bind(object, "title", "recap", "startTime", "endTime", "link");
	}

	@Override
	public void validate(final Session object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			boolean status;
			String message;

			message = object.getTitle();
			status = this.configuration.hasSpam(message);

			super.state(!status, "title", "company.session.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("recap")) {
			boolean status;
			String message;

			message = object.getRecap();
			status = this.configuration.hasSpam(message);

			super.state(!status, "recap", "company.session.error.spam");
		}

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

		Tuple tuple;

		tuple = super.unbind(object, "title", "recap", "startTime", "endTime", "link");
		tuple.put("masterId", object.getPracticum().getId());
		tuple.put("draftMode", object.getPracticum().getDraftMode());

		super.getResponse().setData(tuple);
	}

}
