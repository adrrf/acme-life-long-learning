
package acme.features.company.session;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Session;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionListService extends AbstractService<Company, Session> {

	@Autowired
	protected CompanySessionRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		int practicumId;
		Company object;

		practicumId = super.getRequest().getData("masterId", int.class);
		object = this.repository.findOneCompanyByPracticumId(practicumId);
		status = super.getRequest().getPrincipal().getUsername().equals(object.getUserAccount().getUsername());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		Collection<Session> objects;

		id = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManySessionsByPracticumId(id);

		super.getBuffer().setData(objects);
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
