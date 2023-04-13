<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="company.practicum.list.label.code" path="code" width="10%"/>
	<acme:list-column code="company.practicum.list.label.title" path="title" width="40%"/>
	<acme:list-column code="company.practicum.list.label.recap" path="recap" width="40%"/>
	<acme:list-column code="company.practicum.list.label.goals" path="goals" width="40%"/>
</acme:list>

<acme:button code="company.practicum.list.button.create" action="/company/practicum/create"/>