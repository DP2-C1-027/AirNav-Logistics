
package acme.features.customers.booking;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.datatypes.Money;
import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.booking.Booking;
import acme.entities.flights.Flight;
import acme.realms.Customers;

@GuiService
public class CustomersBookingShowService extends AbstractGuiService<Customers, Booking> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private CustomersBookingRepository repository;


	// AbstractGuiService interface -------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int bookingId;
		Booking booking;
		Customers customer;
		Flight f;

		bookingId = super.getRequest().getData("id", int.class);
		booking = this.repository.findBookinById(bookingId);
		f = this.repository.findFlightByBookingId(bookingId);
		customer = booking == null ? null : booking.getCustomer();
		status = super.getRequest().getPrincipal().hasRealm(customer) || f != null;

		//puede estar a un vuelo q no esta publicado?¿-> imagino q no

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int bookingId;
		Booking booking;

		bookingId = super.getRequest().getData("id", int.class);
		booking = this.repository.findBookinById(bookingId);

		super.getBuffer().addData(booking);

	}

	@Override
	public void unbind(final Booking booking) {
		Dataset dataset;

		Money price = booking.getPrice();

		dataset = super.unbindObject(booking, "locatorCode", "purchaseMoment", "travelClass", "lastNibble");
		dataset.put("price", price);

		super.getResponse().addData(dataset);
	}
}
