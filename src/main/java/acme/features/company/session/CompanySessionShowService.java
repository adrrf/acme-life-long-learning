
package acme.features.company.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.Session;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionShowService extends AbstractService<Company, Session> {

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
		Practicum practicum;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOneSessionByPracticumId(practicumId);
		status = practicum != null && super.getRequest().getPrincipal().hasRole(practicum.getCompany()) && super.getRequest().getPrincipal().getUsername().equals(practicum.getCompany().getUserAccount().getUsername());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Session object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSessionById(id);

		super.getBuffer().setData(object);
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
