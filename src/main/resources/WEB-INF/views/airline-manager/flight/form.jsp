<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="airlineManager.flight.form.label.tag" path="tag"/>
	<acme:input-money code="airlineManager.flight.form.label.cost" path="cost"/>
	<acme:input-textbox code="airlineManager.flight.form.label.description" path="description"/>
	<acme:input-select code="airlineManager.flight.form.label.airlines" path="airline" choices="${airlines}"/>
	<acme:input-checkbox code="airlineManager.flight.form.label.indication" path="indication"/>
	<jstl:choose>
		<jstl:when test="${!(layovers == null)}">
			<acme:input-moment code="airlineManager.flight.form.label.scheduledDeparture" path="scheduledArrival" readonly="true"/>
			<acme:input-moment code="airlineManager.flight.form.label.scheduledArrival" path="scheduledArrival" readonly="true"/>
			<acme:input-textbox code="airlineManager.flight.form.label.departureCity" path="departureCity" readonly="true"/>
			<acme:input-textbox code="airlineManager.flight.form.label.arrivalCity" path="arrivalCity" readonly="true"/>
			<acme:input-integer code="airlineManager.flight.form.label.layovers" path="layovers" readonly="true"/>
		</jstl:when>
	</jstl:choose>
	
	
	
	<jstl:choose>
				<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')  && draftMode == true}">
					<acme:submit code="airlineManager.flight.form.button.update" action="/airline-manager/flight/update"/>
					<acme:submit code="airlineManager.flight.form.button.delete" action="/airline-manager/flight/delete"/>
					<acme:submit code="airlineManager.flight.form.button.publish" action="/airline-manager/flight/publish"/>
					<acme:button code="airlineManager.flight.form.button.legs" action="/airline-manager/leg/list-flight?masterId=${id}"/>	
				</jstl:when>
				<jstl:when test="${_command == 'show'  && draftMode == false}">
					<acme:button code="airlineManager.flight.form.button.legs" action="/airline-manager/leg/list-flight?masterId=${id}"/>	
				</jstl:when>
				<jstl:when test="${_command == 'create'}">
					<acme:submit code="airlineManager.flight.form.button.create" action="/airline-manager/flight/create"/>
				</jstl:when>		
		</jstl:choose>	
</acme:form>