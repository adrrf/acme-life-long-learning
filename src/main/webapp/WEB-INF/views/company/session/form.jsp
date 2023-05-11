<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="company.session.form.label.title" path="title"/>
	<acme:input-textarea code="company.session.form.label.recap" path="recap"/>
	<acme:input-moment code="company.session.form.label.startTime" path="startTime"/>
	<acme:input-moment code="company.session.form.label.endTime" path="endTime"/>
	<acme:input-url code="company.session.form.label.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="company.session.form.button.update" action="/company/session/update"/>
			<acme:submit code="company.session.form.button.delete" action="/company/session/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="company.session.form.button.create" action="/company/session/create?masterId=${masterId}"/>
		</jstl:when>
	</jstl:choose>

</acme:form>