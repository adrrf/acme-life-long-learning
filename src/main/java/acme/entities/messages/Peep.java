
package acme.entities.messages;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;

public class Peep extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@Past
	protected LocalDateTime		instantiationMoment;

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 76)
	protected String			nick;

	@NotBlank
	@Length(max = 101)
	protected String			message;

	@Email
	protected String			email;

	@URL
	protected String			link;
}
