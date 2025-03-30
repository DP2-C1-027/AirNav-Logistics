
package acme.features.flightCrewMember.flightAssignment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flightAssignment.FlightAssignment;
import acme.realms.FlightCrewMember;

@GuiService
public class FlightCrewMemberFlightAssignmentListPublishedService extends AbstractGuiService<FlightCrewMember, FlightAssignment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FlightCrewMemberFlightAssignmentRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<FlightAssignment> completedFlightAssignments = this.repository.findAllPublishedFlightAssignments();

		super.getBuffer().addData(completedFlightAssignments);
	}

	@Override
	public void unbind(final FlightAssignment completedFlightAssignments) {
		Dataset dataset = super.unbindObject(completedFlightAssignments, "duty", "moment", "currentStatus", "remarks", "draftMode", "leg");
		dataset.put("leg", completedFlightAssignments.getLeg().getFlightNumber());

		super.addPayload(dataset, completedFlightAssignments, "duty", "moment", "currentStatus", "remarks", "draftMode", "leg");
		super.getResponse().addData(dataset);
		super.getResponse().addGlobal("showCreate", false);

	}
}
