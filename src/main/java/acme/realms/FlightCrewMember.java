
package acme.realms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractRole;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoney;
import acme.constraints.ValidIdentifier;
import acme.constraints.ValidLongText;
import acme.constraints.ValidPhoneNumber;
import acme.constraints.ValidYearsOfExperience;
import acme.entities.airline.Airline;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidIdentifier
public class FlightCrewMember extends AbstractRole {

	// Serialisation version --------------------------------------------------
	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@Automapped
	@Column(unique = true)
	private String				codigo;

	@Mandatory
	@Automapped
	@ValidPhoneNumber
	private String				phoneNumber;

	@Mandatory
	@Automapped
	@ValidLongText
	private String				languageSkills;

	@Mandatory
	@Automapped
	@Enumerated(EnumType.STRING)
	private AvailabilityStatus	availabilityStatus;

	@Mandatory
	@Automapped
	@ValidMoney
	private Money				salary;

	@Optional
	@Automapped
	@ValidYearsOfExperience
	private Integer				yearsOfExperience;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Airline				airline;

}
