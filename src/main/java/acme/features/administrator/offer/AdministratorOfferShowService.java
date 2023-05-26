
package acme.features.administrator.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.components.RateRepository;
import acme.entities.configuration.Configuration;
import acme.entities.messages.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.datatypes.Money;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferShowService extends AbstractService<Administrator, Offer> {

	@Autowired
	protected AdministratorOfferRepository	repository;

	@Autowired
	protected ConfigurationRepository		configuration;

	@Autowired
	protected RateRepository				rateRepository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int OfferId;
		Offer Offer;

		OfferId = super.getRequest().getData("id", int.class);
		Offer = this.repository.findOfferById(OfferId);
		status = Offer != null && super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Offer object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOfferById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;
		final Configuration config;
		final String moneda;
		final Double rate;
		final Money cambio = new Money();

		config = this.configuration.getSystemConfiguration().iterator().next();
		moneda = config.getCurrency();
		this.rateRepository.getRate();

		rate = this.rateRepository.rate(object.getPrice().getCurrency(), moneda);

		cambio.setAmount(rate * object.getPrice().getAmount());
		cambio.setCurrency(moneda);
		tuple = super.unbind(object, "instantiationMoment", "heading", "summary", "startDate", "endDate", "price", "link");
		tuple.put("exchange", cambio);

		super.getResponse().setData(tuple);
	}

}
