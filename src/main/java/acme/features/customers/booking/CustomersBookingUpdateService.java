
package acme.features.customers.booking;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.datatypes.Money;
import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.booking.Booking;
import acme.entities.booking.TravelClass;
import acme.realms.Customers;

@GuiService
public class CustomersBookingUpdateService extends AbstractGuiService<Customers, Booking> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private CustomersBookingRepository repository;

	// AbstractService -------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int bookingId;
		Booking booking;
		Customers customer;

		bookingId = super.getRequest().getData("id", int.class);
		booking = this.repository.findBookinById(bookingId);
		customer = (Customers) super.getRequest().getPrincipal().getActiveRealm();
		status = booking != null && booking.isDraftMode() && super.getRequest().getPrincipal().hasRealm(customer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Booking booking;
		int id;

		id = super.getRequest().getData("id", int.class);
		booking = this.repository.findBookinById(id);

		super.getBuffer().addData(booking);
	}

	@Override
	public void bind(final Booking booking) {

		super.bindObject(booking, "locatorCode", "purchaseMoment", "travelClass", "lastNibble");

	}

	@Override
	public void validate(final Booking booking) {
		String cod = booking.getLocatorCode();
		Collection<Booking> codigo = this.repository.findAllBookingLocatorCode(cod).stream().filter(x -> x.getId() != booking.getId()).toList();

		if (!codigo.isEmpty())
			super.state(false, "locatorCode", "customers.booking.error.repeat-code");

	}

	@Override
	public void perform(final Booking booking) {
		this.repository.save(booking);
	}

	@Override
	public void unbind(final Booking booking) {

		Dataset dataset;
		SelectChoices choices;
		Integer numero = this.repository.getNumberofPassenger(booking.getId());
		double precio = booking.getPrice().getAmount() * numero;
		String moneda = booking.getPrice().getCurrency();
		Money precioNuevo = new Money();
		precioNuevo.setAmount(precio);
		precioNuevo.setCurrency(moneda);
		choices = SelectChoices.from(TravelClass.class, booking.getTravelClass());
		dataset = super.unbindObject(booking, "locatorCode", "purchaseMoment", "travelClass", "lastNibble", "draftMode");
		dataset.put("price", precioNuevo);
		dataset.put("travelClasses", choices);
		super.getResponse().addData(dataset);
	}
}
