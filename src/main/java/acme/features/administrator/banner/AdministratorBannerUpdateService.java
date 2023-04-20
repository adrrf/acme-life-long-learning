
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.messages.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository	repository;

	@Autowired
	protected ConfigurationRepository		configuration;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findBannerById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "instationUpdateMoment", "startTime", "finishTime", "slogan", "linkPicture", "linkDocument");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("slogan")) {
			boolean status;
			String message;

			message = object.getSlogan();
			status = this.configuration.hasSpam(message);

			super.state(!status, "slogan", "administrator.banner.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("startTime") && !super.getBuffer().getErrors().hasErrors("finishTime"))
			if (!MomentHelper.isBefore(object.getStartTime(), object.getFinishTime()))
				super.state(false, "finishTime", "administrator.banner.form.error.end-before-start");
			else if (!MomentHelper.isBefore(object.getInstationUpdateMoment(), object.getStartTime()))
				super.state(false, "startTime", "administrator.banner.form.error.start-before-instation");
			else {
				final int duration = (int) MomentHelper.computeDuration(object.getStartTime(), object.getFinishTime()).toDays();
				if (!(7 <= duration))
					super.state(false, "finishTime", "administrator.banner.form.error.duration");
			}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instationUpdateMoment", "startTime", "finishTime", "slogan", "linkPicture", "linkDocument");

		super.getResponse().setData(tuple);
	}

}
