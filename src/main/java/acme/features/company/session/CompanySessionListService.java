
package acme.features.company.session;

import java.time.Duration;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.Session;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionListService extends AbstractService<Company, Session> {

	@Autowired
	protected CompanySessionRepository repository;


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
		Practicum object;

		practicumId = super.getRequest().getData("masterId", int.class);
		object = this.repository.findOneSessionByPracticumId(practicumId);
		status = object != null && super.getRequest().getPrincipal().hasRole(object.getCompany()) && super.getRequest().getPrincipal().getUsername().equals(object.getCompany().getUserAccount().getUsername());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Session> objects;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManySessionsByPracticumId(masterId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Session object) {
		assert object != null;

		Tuple tuple;
		Duration duration;

		duration = MomentHelper.computeDuration(object.getStartTime(), object.getEndTime());

		tuple = super.unbind(object, "title", "recap", "link");
		tuple.put("duration", duration.toMinutes());

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<Session> objects) {
		assert objects != null;

		int masterId;
		Practicum practicum;
		final boolean showCreate;

		masterId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(masterId);
		showCreate = practicum.getDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setGlobal("masterId", masterId);
		super.getResponse().setGlobal("showCreate", showCreate);
	}
}
