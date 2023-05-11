<%@page language="java"%>


<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="auditor.auditing-record.form.label.subject" path="subject"/>
	<acme:input-textarea code="auditor.auditing-record.form.label.assessment" path="assessment"/>
	<acme:input-moment code="auditor.auditing-record.form.label.startPeriod" path="startPeriod"/>
	<acme:input-moment code="auditor.auditing-record.form.label.finishPeriod" path="finishPeriod"/>
	<acme:input-select path="mark" code="auditor.auditing-record.form.label.mark" choices="${markes}" />
	<acme:input-url code="auditor.auditing-record.form.label.link" path="link"/>

	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="auditor.auditing-record.form.button.update" action="/auditor/auditing-record/update"/>
			<acme:submit code="auditor.auditing-record.form.button.delete" action="/auditor/auditing-record/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.auditing-record.form.button.create" action="/auditor/auditing-record/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>