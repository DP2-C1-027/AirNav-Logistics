
package acme.features.customers.passenger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.controllers.AbstractGuiController;
import acme.client.controllers.GuiController;
import acme.entities.booking.Passenger;
import acme.realms.Customers;

@GuiController
public class CustomersPassengersController extends AbstractGuiController<Customers, Passenger> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private CustomersPassengersListService			listService;

	@Autowired
	private CustomersPassengersShowService			showService;

	@Autowired
	private CustomersPassengerCreateService			createService;

	@Autowired
	private CustomersPassengerCreateService2		createService2;

	@Autowired
	private CustomersBookingPassengerListService	passengerListService;

	@Autowired
	private CustomersPassengersUpdateService		updateService;

	@Autowired
	private CustomersPassengerPublishService		publishService;

	@Autowired
	private CustomersPassengerDeleteService			deteleService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addCustomCommand("passengerList", "list", this.passengerListService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addCustomCommand("createBooking", "create", this.createService2);
		super.addBasicCommand("delete", this.deteleService);
	}
}
