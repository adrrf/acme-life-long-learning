<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="student.activity.form.label.title" path="title"/>
	<acme:input-textarea code="student.activity.form.label.recap" path="recap"/>
	<acme:input-checkbox code="student.activity.form.label.isTheory" path="isTheory"/>
	<acme:input-moment code="student.activity.form.label.startDate" path="startDate"/>
	<acme:input-moment code="student.activity.form.label.endDate" path="endDate"/>
	<acme:input-moment code="student.activity.form.label.link" path="link"/>
	

	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="student.activity.form.button.update" action="/student/activity/update"/>
			<acme:submit code="student.activity.form.button.delete" action="/student/activity/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="student.activity.form.button.create" action="/student/activity/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
