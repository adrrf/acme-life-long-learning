
package acme.features.administrator.bulletin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.messages.Bulletin;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

public class AdministratorBulletinController extends AbstractController<Administrator, Bulletin> {

	@Autowired
	protected AdministratorBulletinShowService		showService;

	@Autowired
	protected AdministratorBulletinCreateService	createService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
	}

}
