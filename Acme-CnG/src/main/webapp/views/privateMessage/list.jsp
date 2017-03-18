<%--

 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->


<display:table pagesize="10" class="displaytag" name="messages"
	requestURI="${requestURI}" id="row">

	<jstl:if test="${requestURI == 'message/listReceivedMessages.do'}">

		<spring:message code="message.sender" var="messageSender" />
		<display:column property="sender.userAccount.username"
			title="${messageSender}" sortable="false" />

	</jstl:if>

	<jstl:if test="${requestURI == 'message/listSentMessages.do'}">

		<spring:message code="message.recipient" var="messageRecipient" />
		<display:column property="recipient.userAccount.username"
			title="${messageRecipient}" sortable="false" />

	</jstl:if>




	<spring:message code="message.title" var="messageTitle" />
	<display:column property="title" title="${messageTitle}"
		sortable="false" />

	<spring:message code="message.text" var="messageText" />
	<display:column property="text" title="${messageText}" sortable="false" />

	<spring:message code="message.attachments" var="attachMessage" />
	<display:column title="${attachMessage}">

		<jstl:choose>

			<jstl:when test="${fn:contains(row.attachments, ',')}">
				<jstl:set var="attachparts"
					value="${fn:split(row.attachments, ',')}" />


				<jstl:forEach var="i" begin="0" end="${fn:length(attachparts)}">
					<a href="${attachparts[i]}"> <jstl:out
							value="${attachparts[i]}"></jstl:out></a>
						&nbsp; &nbsp;
			</jstl:forEach>

			</jstl:when>
			<jstl:otherwise>
				<a href="${row.attachments}"> <jstl:out
						value="${row.attachments}  "></jstl:out></a>
			</jstl:otherwise>

		</jstl:choose>

	</display:column>

	<spring:message code="message.moment" var="messageMoment" />
	<display:column property="moment" title="${messageMoment}"
		sortable="false" />
		

	<jstl:if test="${requestURI == 'message/listReceivedMessages.do'}">
		<display:column>

			<a href="message/response/create.do?actorId=${row.sender.id}"> <spring:message
					code="message.create" />
			</a>

		</display:column>
	</jstl:if>
	
	<jstl:if test="${requestURI == 'message/listReceivedMessages.do'}">

		<display:column>
		<a href="message/deleteReceived.do?privateMessageId=${row.id}"><spring:message code="message.delete" /></a>
	</display:column>

	</jstl:if>

	<jstl:if test="${requestURI == 'message/listSentMessages.do'}">

		<display:column>
		<a href="message/deleteSent.do?privateMessageId=${row.id}"><spring:message code="message.delete" /></a>
	</display:column>

	</jstl:if>
	
	


</display:table>