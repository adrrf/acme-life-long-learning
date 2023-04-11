
package acme.features.company.session;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.practicum.Session;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class CompanySessionController extends AbstractController<Company, Session> {

	@Autowired
	protected CompanySessionListService	listService;

	@Autowired
	protected CompanySessionShowService	showService;

	//	@Autowired
	//	protected CompanySessionCreateService	createService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		//		super.addBasicCommand("create", this.createService);
	}
}
