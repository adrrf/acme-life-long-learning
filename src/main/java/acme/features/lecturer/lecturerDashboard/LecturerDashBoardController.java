
package acme.features.lecturer.lecturerDashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.forms.LecturerDashboard;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerDashBoardController extends AbstractController<Lecturer, LecturerDashboard> {

	@Autowired
	protected LecturerDashBoardShowService showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);

	}

}
