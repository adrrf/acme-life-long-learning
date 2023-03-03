
package acme.forms;

import java.util.Map;

import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractForm;
import acme.roles.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Min(0)
	@NotNull
	Integer						nActivities;

	@NotNull
	Map<String, Integer>		statsSessionTime;

	@NotNull
	Map<String, Integer>		statsTutorialTime;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Valid
	@NotNull
	@OneToOne(optional = false)
	Student						student;

}
