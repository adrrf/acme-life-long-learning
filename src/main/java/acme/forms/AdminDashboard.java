
package acme.forms;

import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDashboard extends AbstractForm {

	private static final long		serialVersionUID	= 1L;

	@NotNull
	Map<String, Integer>			nRoles;

	@Min(0)
	@NotNull
	Float							ratioPeepsEmailURL;

	@Min(0)
	@NotNull
	Float							ratioCritialBulletins;

	@NotNull
	Map<String, Map<String, Float>>	statsBudgetOffersByCurrency;

}
