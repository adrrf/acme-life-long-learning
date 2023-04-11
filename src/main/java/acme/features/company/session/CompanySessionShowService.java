
package acme.features.company.session;

import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicum.Session;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

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
		int id;
		Session session;

		id = super.getRequest().getData("id", int.class);
		session = this.repository.findOneSessionById(id);
		status = session != null && super.getRequest().getPrincipal().hasRole(session.getPracticum().getCompany());

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

		tuple = super.unbind(object, "title", "recap", "timePeriod", "link");
		tuple.put("sessionId", object.getId());

		super.getResponse().setData(tuple);
	}
}
