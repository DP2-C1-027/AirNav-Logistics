
package acme.features.customers.bookingRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.booking.Booking;
import acme.entities.booking.BookingRecord;
import acme.entities.booking.Passenger;
import acme.realms.Customers;

@GuiService
public class CustomersBookingRecordCreateService extends AbstractGuiService<Customers, BookingRecord> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private CustomersBookingRecordRepository repository;


	// AbstractService<Manager, ProjectUserStoryLink> ---------------------------
	@Override
	public void authorise() {
		Customers customer;
		boolean status;
		customer = (Customers) super.getRequest().getPrincipal().getActiveRealm();

		status = super.getRequest().getPrincipal().hasRealm(customer);
		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		BookingRecord bookingRecord;

		bookingRecord = new BookingRecord();

		super.getBuffer().addData(bookingRecord);
	}

	@Override
	public void bind(final BookingRecord bookingRecord) {

		super.bindObject(bookingRecord, "booking", "passenger");
	}

	@Override
	public void validate(final BookingRecord bookingRecord) {
		Booking booking;
		Passenger passenger;

		booking = bookingRecord.getBooking();
		passenger = bookingRecord.getPassenger();

		Customers customer;
		customer = (Customers) super.getRequest().getPrincipal().getActiveRealm();

		super.state(booking != null, "booking", "customer.booking-record.create.error.null-booking");
		super.state(passenger != null, "passenger", "customer.booking-record.create.error.null-passenger");

		boolean exists = this.repository.existsByBookingAndPassenger(booking, passenger);
		super.state(!exists, "*", "customer.booking-record.create.error.duplicate-booking-passenger");

	}

	@Override
	public void perform(final BookingRecord bookingRecord) {
		this.repository.save(bookingRecord);
	}

	@Override
	public void unbind(final BookingRecord bookingRecord) {
		Dataset dataset;
		Customers customer = (Customers) super.getRequest().getPrincipal().getActiveRealm();
		//creo q da igual si el pasajero esta publicado o no
		Collection<Passenger> passenger = this.repository.findPassengerByCustomerId(customer.getId());
		//creo q el booking no debe estar publicado
		Collection<Booking> booking = this.repository.findNotPublishBooking(customer.getId(), true);

		SelectChoices passengerChoices;
		SelectChoices bookingChoices;

		passengerChoices = SelectChoices.from(passenger, "fullName", bookingRecord.getPassenger());
		bookingChoices = SelectChoices.from(booking, "locatorCode", bookingRecord.getBooking());

		dataset = super.unbindObject(bookingRecord, "booking", "passenger");
		dataset.put("booking", bookingChoices.getSelected().getKey());
		dataset.put("bookings", bookingChoices);
		dataset.put("passenger", passengerChoices.getSelected().getKey());
		dataset.put("passengers", passengerChoices);
		dataset.put("draftMode", true);

		super.getResponse().addData(dataset);

	}
}
