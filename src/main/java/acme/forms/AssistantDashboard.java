
package acme.forms;

import java.util.Map;

import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractForm;
import acme.roles.Assistant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	@Min(0)
	@NotNull
	Integer						nTutorialsTheoricalCourse;

	@Min(0)
	@NotNull
	Integer						nTutorialsHandsOnCourse;

	@NotNull
	Map<String, Integer>		statsSessionTime;

	@NotNull
	Map<String, Integer>		statsTutorialTime;

	@Valid
	@NotNull
	@OneToOne(optional = false)
	Assistant					assistant;
}
