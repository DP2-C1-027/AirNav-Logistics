
package acme.forms;

import java.util.List;

import acme.client.components.basis.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomersDashboards extends AbstractForm {
	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private List<String>		theLastFiveDestinations; // tengo q poner el private
	Double						moneySpentInBookingDuringLastYear;
	//Map<TravelClass, Integer>	bookingsGroupedByTravelClass;

	Integer						bookingGroupByEconomy;
	Integer						bookingGroupByBusiness;

	Integer						countOfTheCostBooking5Years;
	Double						averageOfTheCostBooking5Years;
	Double						minOfTheCostBooking5Years;
	Double						maxOfTheCostBooking5Years;
	Double						desviationOfTheCostBooking5Years;

	Integer						countOfTheNumberOfPassengersBookings;
	Double						averageOfTheNumberOfPassengersBookings;
	Double						minOfTheNumberOfPassengersBookings;
	Double						maxOfTheNumberOfPassengersBookings;
	Double						desviationOfTheNumberOfPassengersBookings;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
