<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="assistant.tutorial-session.list.label.title" path="title" width="20%"/>	
	<acme:list-column code="assistant.tutorial-session.list.label.recap" path="recap" width="40%"/>
	<acme:list-column code="assistant.tutorial-session.list.label.duration" path="duration" width="20%"/>
	<acme:list-column code="assistant.tutorial-session.list.label.theory" path="isTheory" width="20%"/>
</acme:list>

<acme:button test="${showCreate}" code="assistant.tutorial-session.list.button.create" action="/assistant/tutorial-session/create?masterId=${masterId}"/>