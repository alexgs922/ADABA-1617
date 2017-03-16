<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme-CnG Co., Inc." />
</div>
<!-- Ocultado porque aparecia en este proyecto  -->
<!--  "src/main/webapp/views/master-page/header.jsp" -->
<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="banner/administrator/list.do"><spring:message code="master.page.administrator.list" /></a></li>
					<li><a href="requestOffer/listRequests.do"><spring:message code="master.page.customer.listRequests" /></a></li>
					<li><a href="requestOffer/listOffers.do"><spring:message code="master.page.customer.listOffers" /></a></li>					
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message	code="master.page.message" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="message/create.do"><spring:message code="master.page.message.create" /></a></li>	
					<li><a href="message/listSentMessages.do"><spring:message code="master.page.message.sent" /></a></li>
					<li><a href="message/listReceivedMessages.do"><spring:message code="master.page.message.received" /></a></li>				
				</ul>
			</li>
			
			
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="requestOffer/customer/listRequests.do"><spring:message code="master.page.customer.listRequests" /></a></li>
					<li><a href="requestOffer/customer/listOffers.do"><spring:message code="master.page.customer.listOffers" /></a></li>
					<li><a href="application/customer/listMyApplications.do"><spring:message code="master.page.customer.listMyApplications" /></a></li>
					<li><a href="application/customer/listMyRequestOfferApplications.do"><spring:message code="master.page.customer.listMyRequestOfferApplications" /></a></li>
					<li><a href="requestOffer/customer/create.do"><spring:message code="master.page.customer.create" /></a></li>
					<li><a href="requestOffer/customer/myRequestsandOffers.do"><spring:message code="master.page.mine" /></a></li>					
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message	code="master.page.message" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="message/create.do"><spring:message code="master.page.message.create" /></a></li>	
					<li><a href="message/listSentMessages.do"><spring:message code="master.page.message.sent" /></a></li>
					<li><a href="message/listReceivedMessages.do"><spring:message code="master.page.message.received" /></a></li>				
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="customer/register.do"><spring:message code="master.page.register" /></a></li>
			
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>				
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

