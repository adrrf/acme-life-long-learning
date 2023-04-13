<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>
	<acme:input-textbox code="student.enrolment.form.label.motivation" path="motivation"/>
	<acme:input-textarea code="student.enrolment.form.label.goals" path="goals"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="student.enrolment.form.buttton.activities" action="/student/enrolment/list?masterId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="student.enrolment.form.buttton.activities" action="/student/enrolment/list?masterId=${id}"/>
			<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
			<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
			<acme:submit code="student.enrolment.form.button.publish" action="/student/enrolment/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="student.enrolment.form.button.create"
				action="/student/enrolment/create?courseId=${courseId}" />
		</jstl:when>
	</jstl:choose>

</acme:form>