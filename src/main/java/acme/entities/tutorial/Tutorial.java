
package acme.entities.tutorial;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import acme.entities.course.Course;
import acme.framework.data.AbstractEntity;

public class Tutorial extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	protected String			code;

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			recap;

	@NotBlank
	@Length(max = 101)
	protected String			goals;

	@Min(0)
	protected Integer			estimatedTime;

	@NotNull
	protected Boolean			draftMode;

	@NotNull
	@Valid
	@OneToOne(optional = false)
	protected Course			course;

}
