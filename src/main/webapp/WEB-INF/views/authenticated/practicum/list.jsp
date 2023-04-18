<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.practicum.list.label.code" path="code" width="10%"/>
	<acme:list-column code="authenticated.practicum.list.label.title" path="title" width="45%"/>
	<acme:list-column code="authenticated.practicum.list.label.recap" path="recap" width="45%"/>	
	<acme:list-column code="authenticated.practicum.list.label.goals" path="goals" width="45%"/>	
</acme:list>