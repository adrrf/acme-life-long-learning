<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="student.activity.list.label.title" path="title" width="20%"/>	
	<acme:list-column code="student.activity.list.label.recap" path="recap" width="40%"/>
	<acme:list-column code="student.activity.list.label.duration" path="duration" width="20%"/>
	<acme:list-column code="student.activity.list.label.theory" path="isTheory" width="20%"/>
	<acme:list-column code="student.activity.list.label.link" path="link" width="20%"/>
	
</acme:list>

<acme:button test="${showCreate}" code="student.activity.list.button.create" action="/student/activity/create?masterId=${masterId}"/>