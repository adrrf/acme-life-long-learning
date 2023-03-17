
package acme.forms;

import java.util.Map;

import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractForm;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	Map<String, Integer> nPracticaOrCoursesByMonth;

	@NotNull
	Map<String, Float> statsSessions;

	@NotNull
	Map<String, Float> statsPracticums;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Valid
	@NotNull
	@OneToOne
	Company	company;

}
