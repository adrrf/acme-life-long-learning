<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>


	<jstl:choose>
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:input-textbox readonly="true"
				code="student.enrolment.form.label.code" path="code" />
			<acme:input-textbox code="student.enrolment.form.label.motivation"
				path="motivation" />
			<acme:input-textarea code="student.enrolment.form.label.goals"
				path="goals" />
			<acme:input-integer readonly="true"
				code="student.enrolment.form.label.time" path="estimatedTime" />
			<acme:input-textbox code="student.enrolment.form.label.card"
				path="card" />
			<acme:input-textbox code="student.enrolment.form.label.holder"
				path="holder" />
			<acme:button code="student.enrolment.form.buttton.activities"
				action="/student/activity/list?masterId=${id}" />
		</jstl:when>
		<jstl:when
			test="${acme:anyOf(_command, 'show|update|delete|finalise') && draftMode == true}">
			<acme:input-textbox readonly="true"
				code="student.enrolment.form.label.code" path="code" />
			<acme:input-textbox code="student.enrolment.form.label.motivation"
				path="motivation" />
			<acme:input-textarea code="student.enrolment.form.label.goals"
				path="goals" />
			<acme:input-integer readonly="true"
				code="student.enrolment.form.label.time" path="estimatedTime" />
			<acme:button code="student.enrolment.form.buttton.activities"
				action="/student/activity/list?masterId=${id}" />

			<acme:input-textbox code="student.enrolment.form.label.card"
				path="card" />
			<acme:input-textbox code="student.enrolment.form.label.holder"
				path="holder" />
			<acme:input-textbox code="student.enrolment.form.label.CVV"
				path="CVV" />
			<acme:input-textbox code="student.enrolment.form.label.expiryDate"
				path="expiryDate" />

			<acme:submit code="student.enrolment.form.button.update"
				action="/student/enrolment/update" />
			<acme:submit code="student.enrolment.form.button.delete"
				action="/student/enrolment/delete" />
			<acme:submit code="student.enrolment.form.button.finalise"
				action="/student/enrolment/finalise" />
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:input-textbox code="student.enrolment.form.label.code"
				path="code" />
			<acme:input-textbox code="student.enrolment.form.label.motivation"
				path="motivation" />
			<acme:input-textarea code="student.enrolment.form.label.goals"
				path="goals" />
			<acme:input-integer readonly="true"
				code="student.enrolment.form.label.time" path="estimatedTime" />
			<acme:submit code="student.enrolment.form.button.create"
				action="/student/enrolment/create?courseId=${courseId}" />
		</jstl:when>
	</jstl:choose>

</acme:form>