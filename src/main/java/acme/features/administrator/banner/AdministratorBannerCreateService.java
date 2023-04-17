
package acme.features.administrator.banner;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messages.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object = new Banner();
		object.setInstationUpdateMoment(moment);

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

		if (!super.getBuffer().getErrors().hasErrors("startTime") && !super.getBuffer().getErrors().hasErrors("finishTime"))
			if (!MomentHelper.isBefore(object.getStartTime(), object.getFinishTime()))
				super.state(false, "finishTime", "administrator.banner.form.error.end-before-start");
			else {
				final int days = (int) MomentHelper.computeDuration(MomentHelper.getCurrentMoment(), object.getStartTime()).toDays();
				if (days < 1)
					super.state(false, "startTime", "administrator.banner.form.error.day-ahead");
				else {
					final int hours = (int) MomentHelper.computeDuration(object.getStartTime(), object.getFinishTime()).toHours();
					if (!(1 <= hours && hours <= 5))
						super.state(false, "finishTime", "administrator.banner.form.error.duration");
				}
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
