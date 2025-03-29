<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" uri="http://acme-framework.org/" %>

<h2>
    <acme:print code="customer.dashboard.title" />
</h2>

<!-- Estad�sticas de reservas -->
<h3>
    <acme:print code="customer.customers-dashboard.booking-statistics" />
</h3>
<table class="table table-sm">
    <!-- N�mero de Reservas -->
    <tr>
        <th scope="row">
            <acme:print code="customer.customers-dashboard.label.count-booking" />
        </th>
        <td>
            <acme:print value="${booking5Years.count}" />
        </td>
    </tr>

    <!-- Promedio de Reservas -->
    <tr>
        <th scope="row">
            <acme:print code="customer.customers-dashboard.label.average-booking" />
        </th>
        <td>
            <acme:print value="${booking5Years.average}" />
        </td>
    </tr>

    <!-- Reserva M�nima -->
    <tr>
        <th scope="row">
            <acme:print code="customer.customers-dashboard.label.min-booking" />
        </th>
        <td>
            <acme:print value="${booking5Years.min}" />
        </td>
    </tr>

    <!-- Reserva M�xima -->
    <tr>
        <th scope="row">
            <acme:print code="customer.customers-dashboard.label.max-booking" />
        </th>
        <td>
            <acme:print value="${booking5Years.max}" />
        </td>
    </tr>
     <!-- reserva desviacion -->
    <tr>
        <th scope="row">
            <acme:print code="customer.customers-dashboard.label.devv-booking" />
        </th>
        <td>
            <acme:print value="${booking5Years.deviationsss}" />
        </td>
    </tr>
</table>

<!-- Estad�sticas de pasajeros -->
<h3>
    <acme:print code="customer.customers-dashboard.passenger-statistics" />
</h3>
<table class="table table-sm">
    <!-- N�mero de Pasajeros -->
    <tr>
        <th scope="row">
            <acme:print code="customer.customers-dashboard.label.count-passenger" />
        </th>
        <td>
            <acme:print value="${passengersBooking.count}" />
        </td>
    </tr>

    <!-- Promedio de Pasajeros -->
    <tr>
        <th scope="row">
            <acme:print code="customer.customers-dashboard.label.average-passenger" />
        </th>
        <td>
            <acme:print value="${passengersBooking.average}" />
        </td>
    </tr>

    <!-- Pasajero M�nimo -->
    <tr>
        <th scope="row">
            <acme:print code="customer.customers-dashboard.label.min-passenger" />
        </th>
        <td>
            <acme:print value="${passengersBooking.min}" />
        </td>
    </tr>

    <!-- Pasajero M�ximo -->
    <tr>
        <th scope="row">
            <acme:print code="customer.customers-dashboard.label.max-passenger" />
        </th>
        <td>
            <acme:print value="${passengersBooking.max}" />
        </td>
    </tr>
     <!-- Pasajero desviacion -->
    <tr>
        <th scope="row">
            <acme:print code="customer.customers-dashboard.label.devv-passenger" />
        </th>
        <td>
            <acme:print value="${passengersBooking.deviationsss}" />
        </td>
    </tr>
</table>

<!-- Destinos m�s recientes -->
<h3>
    <acme:print code="customer.customers-dashboard.last-five-destinations" />
</h3>
<table class="table table-sm">
    <tr>
        <th scope="row">
            <acme:print code="customer.customers-dashboard.label.last-five-destinations" />
        </th>
        <td>
             <acme:print value="${theLastFiveDestinations}" />
        </td>
    </tr>
</table>

<!-- Dinero gastado en el �ltimo a�o -->
<h3>
    <acme:print code="customer.customers-dashboard.money-spent-last-year" />
</h3>
<table class="table table-sm">
    <tr>
        <th scope="row">
            <acme:print code="customer.customers-dashboard.label.money-spent" />
        </th>
        <td>
            <acme:print value="${moneySpentInBookingDuringLastYear}" />
        </td>
    </tr>
</table>

<h3>
    <acme:print code="customer.customers-dashboard.numBYTravelClass" />
</h3>
<table class="table table-sm">
    <tr>
        <th scope="row">
            <acme:print code="customer.customers-dashboard.label.totalNumTravelClassEconomy" />
        </th>
        <td>
            <acme:print value="${totalNumTravelClassEconomy}" />
        </td>
    </tr>
    <tr>
        <th scope="row">
            <acme:print code="customer.customers-dashboard.label.totalNumTravelClassBusiness" />
        </th>
        <td>
            <acme:print value="${totalNumTravelClassBusiness}" />
        </td>
    </tr>
</table>

<acme:return />
