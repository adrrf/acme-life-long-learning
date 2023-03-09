
package acme.forms;

import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerDashboards extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	@Min(0)
	@NotNull
	Integer						nlectures;

	@NotNull
	Map<String, Float>			statsLearningTimeLectures;

	@NotNull
	Map<String, Float>			statsLearningTimeCourses;

}
