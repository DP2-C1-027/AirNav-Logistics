
package acme.entities.airline;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Past;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidEmail;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidIATAcode;
import acme.constraints.ValidPhoneNumber;
import acme.constraints.ValidShortText;
import acme.entities.airport.OperationalScope;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Airline extends AbstractEntity {

	// Serialisation version --------------------------------------------------
	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidShortText
	@Automapped
	private String				name;

	@Mandatory
	@ValidIATAcode
	@Automapped
	@Column(unique = true)
	private String				code;

	@Mandatory
	@Automapped
	private OperationalScope	operationalScope;

	@Mandatory
	@Automapped
	@ValidUrl
	private String				website;

	@Mandatory
	@Enumerated(EnumType.STRING)
	@Automapped
	private Type				type;

	@Mandatory
	@Automapped
	@Past
	private java.util.Date		foundationMoment;

	@Optional
	@Automapped
	@ValidEmail
	private String				email;

	@Optional
	@Automapped
	@ValidPhoneNumber
	private String				phoneNumber;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
