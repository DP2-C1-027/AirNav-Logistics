
package acme.features.flightCrewMember.activityLog;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flightAssignment.ActivityLog;
import acme.realms.FlightCrewMember;

@GuiService
public class FlightCrewMemberActivityLogShowService extends AbstractGuiService<FlightCrewMember, ActivityLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FlightCrewMemberActivityLogRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		// Only is allowed to show an activity log if the creator is the flight crew member associated to the flight assignment.
		int activityLogId = super.getRequest().getData("id", int.class);
		ActivityLog activityLog = this.repository.findActivityLogById(activityLogId);
		boolean status = activityLog != null && super.getRequest().getPrincipal().hasRealm(activityLog.getFlightAssignment().getFlightCrewMember());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		ActivityLog activityLog = this.repository.findActivityLogById(id);

		super.getBuffer().addData(activityLog);
	}

	@Override
	public void unbind(final ActivityLog activityLog) {
		Dataset dataset = super.unbindObject(activityLog, "registrationMoment", "typeOfIncident", "description", "severityLevel", "draftMode");

		// Show create if the assignment is completed
		if (activityLog.getFlightAssignment().getLeg().getScheduledArrival().before(MomentHelper.getCurrentMoment()))
			super.getResponse().addGlobal("showAction", true);

		boolean draftModeFlightAssignment = this.repository.findFlightAssignmentById(activityLog.getFlightAssignment().getId()).getDraftMode();
		dataset.put("draftModeFlightAssignment", draftModeFlightAssignment);

		super.getResponse().addData(dataset);
	}
}
