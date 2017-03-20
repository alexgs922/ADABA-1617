<%--
 * footer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jsp:useBean id="date" class="java.util.Date" />

<hr />

<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> ACME, Inc.</b>

<br/>

<a href="welcome/about.do">
	<spring:message code="master.page.about" />
</a>

<!-- Css Cookies -->
<spring:url value="/styles/cookies.css" var="cookiesCss" />
<link href="${cookiesCss}" rel="stylesheet" />
<script src="${cookiesCss}"></script>

<!-- Cookies -->
<div id="aceptationBar" style="display: block;">
	<div class="inner">
		<spring:message code="master.page.cookies" />
		<a href="javascript:void(0);" class="ok" onclick="putCookie();">
			<b>OK</b>
		</a>
		 | 
		<a href="welcome/cookiesPolicy.do" target="_blank" class="info">
			<spring:message code="master.page.cookes.information" />
		</a>
	</div>
</div>
 
<script>
	if (getCookie('cookieAdvice') == "1") {
		document.getElementById("aceptationBar").style.display = "none";
	}
	
	function getCookie(c_name) {
		var c_value = document.cookie;
		var c_start = c_value.indexOf(" " + c_name + "=");
		
		if (c_start == -1) {
			c_start = c_value.indexOf(c_name + "=");
		}
		if (c_start == -1) {
			c_value = null;
		} else {
			c_start = c_value.indexOf("=", c_start) + 1;
			var c_end = c_value.indexOf(";", c_start);
			
			if (c_end == -1) {
				c_end = c_value.length;
			}
			
			c_value = unescape(c_value.substring(c_start, c_end));
		}
		return c_value;
	}

	function setCookie(c_name, value, exdays) {
		var exdate = new Date();
		  
		exdate.setDate(exdate.getDate() + exdays);
		var c_value = escape(value)
		  + ((exdays == null) ? "" : "; expires=" + exdate.toUTCString() + "; path=" + "/Acme-BNB");
		  
		document.cookie = c_name + "=" + c_value;
	 }

	function putCookie() {
		
		setCookie('cookieAdvice', '1', 365);
		document.getElementById("aceptationBar").style.display = "none";
	}
</script>
