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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="requestOffer">

	<fieldset>
		<legend>
			<spring:message code="requestOffer.type" />
		</legend>
		
		<input type="radio" name="requestOrOffer" value="${request}" checked="checked">
		<spring:message code="requestOffer.request" />
		<input type="radio" name="requestOrOffer" value="${offer}">
		<spring:message code="requestOffer.offer" />
		
		
		
	</fieldset>
	

	<fieldset>
		<legend>
			<spring:message code="requestOffer.info" />
		</legend>

		<acme:textbox code="requestOffer.title" path="title" />
		<br>
		<acme:textarea code="requestOffer.description" path="description" />
		<br>
		<acme:textbox code="requestOffer.moment" path="moment" />
		<br>

	</fieldset>

	<fieldset>
		<legend>
			<spring:message code="requestOffer.originPlace" />
		</legend>

		<acme:textbox code="place.address" path="originaddress" />
		<br>
		<acme:textbox code="place.length" path="originlength" />
		<br>
		<acme:textbox code="place.latitude" path="originlatitude" />
		<br>

	</fieldset>

	<fieldset>
		<legend>
			<spring:message code="requestOffer.destinationPlace" />
		</legend>

		<acme:textbox code="place.address" path="destinationaddress" />
		<br>
		<acme:textbox code="place.length" path="destinationlength" />
		<br>
		<acme:textbox code="place.latitude" path="destinationlatitude" />
		<br>

	</fieldset>



	<acme:submit name="save" code="requestOffer.save" />

	<acme:cancel url="requestOffer/customer/myRequestsandOffers.do" code="requestOffer.cancel" />

</form:form>