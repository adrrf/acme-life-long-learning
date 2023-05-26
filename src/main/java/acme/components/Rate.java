
package acme.components;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Rate extends AbstractEntity {

	@Temporal(TemporalType.DATE)
	public Date		date;

	@NotNull
	@Valid
	public String	source;

	@NotNull
	@Valid
	public String	target;

	@NotNull
	public Double	rate;

}
