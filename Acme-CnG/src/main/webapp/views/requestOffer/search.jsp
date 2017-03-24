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
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>


	<form action="requestOffer/customer/listByKeyword.do" method="get">
	<label><spring:message code="requestOffer.search.keyword"/></label>
	<input type="text" name="keyword"/> <br />
	<br>
	<input type="submit" value="<spring:message code="requestOffer.search" />" /> 	 
	</form>


<display:table pagesize="10" class="displaytag" name="requestOffers"
	requestURI="${requestURI}" id="row">
	

	<jstl:forEach var = "x" items ="${applications}">
		<jstl:if test="${x.requestOffer.id == row.id }">
			<jstl:set var = "applied" value = "${1}"/>
		</jstl:if>
	</jstl:forEach>

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

	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<jstl:choose>
				<jstl:when test="${row.requestOrOffer == 'REQUEST' && row.customer.id != principal.id && applied != 1}">
					<a
						href="requestOffer/customer/applyRequest.do?requestOfferId=${row.id}">
						<spring:message code="requestOffer.apply" />
					</a>
				</jstl:when>
				
				<jstl:when test="${row.requestOrOffer == 'OFFER' && row.customer.id != principal.id && applied != 1}">
					<a
						href="requestOffer/customer/applyOffer.do?requestOfferId=${row.id}">
						<spring:message code="requestOffer.apply" />
					</a>
				</jstl:when>
			
			</jstl:choose>

		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<jstl:choose >
			<jstl:when test="${row.banned == false }">
				<a
					href="requestOffer/administrator/banRequestOffer.do?requestOfferId=${row.id}">
							<spring:message code="requestOffer.ban" />
				</a>
			</jstl:when>
			<jstl:when test="${row.banned == true }">
				<a>
					<spring:message code="requestOffer.banned" />
				</a>
			</jstl:when>
				
			</jstl:choose>

		</display:column>
	</security:authorize>


	<display:column>
			<a
				href="comment/listComments.do?commentableEntityId=${row.id}">
				<spring:message code="list.comments" />
			</a>
	</display:column>

</display:table>




<security:authorize access="hasRole('CUSTOMER')">

	<a
		href="requestOffer/customer/create.do">
		<spring:message code="requestOffer.create" />
	</a>


</security:authorize>

