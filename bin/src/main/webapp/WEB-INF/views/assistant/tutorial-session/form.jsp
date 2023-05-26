<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="assistant.tutorial-session.form.label.title" path="title"/>
	<acme:input-textarea code="assistant.tutorial-session.form.label.recap" path="recap"/>
	<acme:input-checkbox code="assistant.tutorial-session.form.label.isTheory" path="isTheory"/>
	<acme:input-moment code="assistant.tutorial-session.form.label.startTime" path="startTime"/>
	<acme:input-moment code="assistant.tutorial-session.form.label.endTime" path="endTime"/>

	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="assistant.tutorial-session.form.button.update" action="/assistant/tutorial-session/update"/>
			<acme:submit code="assistant.tutorial-session.form.button.delete" action="/assistant/tutorial-session/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.tutorial-session.form.button.create" action="/assistant/tutorial-session/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
