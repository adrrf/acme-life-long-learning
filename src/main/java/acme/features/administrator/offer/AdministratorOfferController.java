
package acme.features.administrator.offer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.messages.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

@Controller
public class AdministratorOfferController extends AbstractController<Administrator, Offer> {

	@Autowired
	protected AdministratorOfferListService		listService;

	@Autowired
	protected AdministratorOfferShowService		showService;

	@Autowired
	protected AdministratorOfferCreateService	createService;

	@Autowired
	protected AdministratorOfferUpdateService	updateService;

	@Autowired
	protected AdministratorOfferDeleteService	deleteService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
	}

}
