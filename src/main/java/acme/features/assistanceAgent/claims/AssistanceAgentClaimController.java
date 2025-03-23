
package acme.features.assistanceAgent.claims;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.controllers.AbstractGuiController;
import acme.client.controllers.GuiController;
import acme.entities.claims.Claim;
import acme.realms.AssistanceAgent;

@GuiController
public class AssistanceAgentClaimController extends AbstractGuiController<AssistanceAgent, Claim> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AssistanceAgentClaimListService				listService;
	@Autowired
	private AssistanceAgentClaimShowService				showService;

	@Autowired
	private AssistanceAgentClaimListUndergoingService	listUndergoingService;

	@Autowired
	private AssistanceAgentClaimCreateService			createService;
	/*
	 * 
	 * 
	 * @Autowired
	 * private AssistanceAgentClaimUpdateService updateService;
	 * 
	 * @Autowired
	 * private AssistanceAgentClaimDeleteService deleteService;
	 * 
	 * @Autowired
	 * private AssistanceAgentClaimPublishService publishService;
	 * 
	 * // Constructors -----------------------------------------------------------
	 */


	@PostConstruct
	protected void initialise() {

		super.addBasicCommand("list", this.listService);

		super.addBasicCommand("show", this.showService);
		//super.addBasicCommand("listUndergoing", this.listUndergoingService);

		super.addBasicCommand("create", this.createService);
		/*
		 * super.addBasicCommand("listUndergoing", this.listUndergoingService);
		 * 
		 * super.addBasicCommand("create", this.createService);
		 * super.addBasicCommand("update", this.updateService);
		 * super.addBasicCommand("delete", this.deleteService);
		 * super.addBasicCommand("publish", this.publishService);
		 */

	}

}
