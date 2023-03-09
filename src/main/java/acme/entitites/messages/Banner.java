package acme.entitites.messages;
/*
 * Consumer.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@PastOrPresent
	protected Date instationUpdateMoment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date startTime;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date finishTime;
	
	@NotBlank
	@Length(max= 76)
	protected String slogan;
	
	@NotNull
	@URL
	protected String linkPicture;
	
	@NotNull
	@URL
	protected String linkDocument;


	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
