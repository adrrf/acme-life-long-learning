
package acme.forms;

import java.util.Map;

import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractForm;
import acme.roles.Auditor;

public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@Min(0)
	protected Integer				nAudits;

	@NotNull
	protected Map<String, Float>	statsAudits;

	@NotNull
	protected Map<String, Float>	statsAuditingRecords;

	@Valid
	@NotNull
	@OneToOne(optional = false)
	protected Auditor				auditor;

}
