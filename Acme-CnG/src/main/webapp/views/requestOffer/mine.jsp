<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h2>
	<spring:message code="myRequests" var="myRequests" />
	<jstl:out value="${myRequests}" />
</h2>

<display:table pagesize="5" class="displaytag" name="requests"
	requestURI="requestOffer/customer/myRequestsandOffers.do" id="row">

	<spring:message code="requestOffer.title" var="requestOfferTitle" />
	<display:column property="title" title="${requestOfferTitle}"
		sortable="true" />

	<spring:message code="requestOffer.descripction"
		var="requestOfferDescription" />
	<display:column property="description"
		title="${requestOfferDescription}" sortable="true" />

	<spring:message code="requestOffer.moment" var="requestOfferMoment" />
	<display:column property="moment" title="${requestOfferMoment}"
		sortable="true" />

	<spring:message code="requestOffer.originPlace"
		var="requestOfferOriginPlace" />
	<display:column title="${requestOfferOriginPlace}" sortable="true">
		<jstl:out value="${row.originPlace.address }" />
		<jstl:out
			value="(${row.originPlace.length},${row.originPlace.latitude })" />
	</display:column>

	<spring:message code="requestOffer.destinationPlace"
		var="requestOfferDestinationPlace" />
	<display:column title="${requestOfferDestinationPlace}" sortable="true">
		<jstl:out value="${row.destinationPlace.address }" />
		<jstl:out
			value="(${row.destinationPlace.length},${row.destinationPlace.latitude })" />
	</display:column>

	<display:column>
			<a
				href="comment/listComments.do?commentableEntityId=${row.id}">
				<spring:message code="list.comments" />
			</a>
	</display:column>

</display:table>
<br>
<br>

<h2>
	<spring:message code="myOffers" var="myOffers" />
	<jstl:out value="${myOffers}" />
</h2>
<display:table pagesize="5" class="displaytag" name="offers"
	requestURI="requestOffer/customer/myRequestsandOffers.do" id="off">

	<spring:message code="requestOffer.title" var="OfferTitle" />
	<display:column property="title" title="${OfferTitle}" sortable="true" />

	<spring:message code="requestOffer.descripction" var="OfferDescription" />
	<display:column property="description" title="${OfferDescription}"
		sortable="true" />

	<spring:message code="requestOffer.moment" var="OfferMoment" />
	<display:column property="moment" title="${OfferMoment}"
		sortable="true" />

	<spring:message code="requestOffer.originPlace" var="OfferOriginPlace" />
	<display:column title="${OfferOriginPlace}" sortable="true">
		<jstl:out value="${off.originPlace.address }" />
		<jstl:out
			value="(${off.originPlace.length},${off.originPlace.latitude })" />
	</display:column>

	<spring:message code="requestOffer.destinationPlace"
		var="OfferDestinationPlace" />
	<display:column title="${OfferDestinationPlace}" sortable="true">
		<jstl:out value="${off.destinationPlace.address }" />
		<jstl:out
			value="(${off.destinationPlace.length},${off.destinationPlace.latitude })" />
	</display:column>


</display:table>
<br>
<br>

<jstl:if test="${not empty banned}">
	<h2>
		<spring:message code="mybanned" var="mybanned" />
		<jstl:out value="${mybanned}" />
	</h2>
	<display:table pagesize="5" class="displaytag" name="banned"
		requestURI="requestOffer/customer/myRequestsandOffers.do" id="ban">

		<spring:message code="requestOffer.title" var="banTitle" />
		<display:column property="title" title="${banTitle}" sortable="true" />

		<spring:message code="requestOffer.descripction" var="banDescription" />
		<display:column property="description" title="${banDescription}"
			sortable="true" />

		<spring:message code="requestOffer.moment" var="banMoment" />
		<display:column property="moment" title="${banMoment}" sortable="true" />

		<spring:message code="requestOffer.originPlace" var="banOriginPlace" />
		<display:column title="${banOriginPlace}" sortable="true">
			<jstl:out value="${ban.originPlace.address }" />
			<jstl:out
				value="(${ban.originPlace.length},${ban.originPlace.latitude })" />
		</display:column>

		<spring:message code="requestOffer.destinationPlace"
			var="banDestinationPlace" />
		<display:column title="${banDestinationPlace}" sortable="true">
			<jstl:out value="${ban.destinationPlace.address }" />
			<jstl:out
				value="(${ban.destinationPlace.length},${ban.destinationPlace.latitude })" />
		</display:column>

		<spring:message code="requestOffer.type" var="bantype" />
		<display:column title="${bantype}" sortable="true">

			<jstl:choose>
				<jstl:when test="${ban.requestOrOffer == 'REQUEST' }">
					<spring:message code="requestOffer.type.request" var="banRequest" />
					<jstl:out value="${banRequest}" />
				</jstl:when>
				<jstl:when test="${ban.requestOrOffer == 'OFFER' }">
					<spring:message code="requestOffer.type.offer" var="banOffer" />
					<jstl:out value="${banOffer}" />
				</jstl:when>
			</jstl:choose>


		</display:column>


	</display:table>
</jstl:if>


<security:authorize access="hasRole('CUSTOMER')">

	<a href="requestOffer/customer/create.do"> <spring:message
			code="requestOffer.create" />
	</a>


</security:authorize>