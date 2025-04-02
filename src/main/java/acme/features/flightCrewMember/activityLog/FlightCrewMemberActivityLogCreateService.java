
package acme.features.flightCrewMember.activityLog;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flightAssignment.ActivityLog;
import acme.entities.flightAssignment.FlightAssignment;
import acme.realms.FlightCrewMember;

@GuiService
public class FlightCrewMemberActivityLogCreateService extends AbstractGuiService<FlightCrewMember, ActivityLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FlightCrewMemberActivityLogRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		// Only is allowed to create an activity log if the creator is the flight crew member associated to the flight assignment.
		// An activity log cannot be created if the assignment is planned, only complete are allowed.
		int assignmentId = super.getRequest().getData("assignmentId", int.class);
		FlightAssignment flightAssignment = this.repository.findFlightAssignmentById(assignmentId);
		boolean status = flightAssignment != null && flightAssignment.getLeg().getScheduledArrival().before(MomentHelper.getCurrentMoment()) && super.getRequest().getPrincipal().hasRealm(flightAssignment.getFlightCrewMember());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ActivityLog activityLog = new ActivityLog();

		activityLog.setRegistrationMoment(MomentHelper.getCurrentMoment());

		int assignmentId = super.getRequest().getData("assignmentId", int.class);
		FlightAssignment flightAssignment = this.repository.findFlightAssignmentById(assignmentId);
		activityLog.setFlightAssignment(flightAssignment);

		activityLog.setDraftMode(true);
		super.getBuffer().addData(activityLog);
	}

	@Override
	public void bind(final ActivityLog activityLog) {
		super.bindObject(activityLog, "typeOfIncident", "description", "severityLevel");
	}

	@Override
	public void validate(final ActivityLog activityLog) {
	}

	@Override
	public void perform(final ActivityLog activityLog) {
		this.repository.save(activityLog);
	}

	@Override
	public void unbind(final ActivityLog activityLog) {
		Dataset dataset = super.unbindObject(activityLog, "registrationMoment", "typeOfIncident", "description", "severityLevel", "draftMode");

		// Show create if the assignment is completed
		if (activityLog.getFlightAssignment().getLeg().getScheduledArrival().before(MomentHelper.getCurrentMoment()))
			super.getResponse().addGlobal("showAction", true);

		dataset.put("assignmentId", super.getRequest().getData("assignmentId", int.class));
		super.getResponse().addData(dataset);
	}

}
