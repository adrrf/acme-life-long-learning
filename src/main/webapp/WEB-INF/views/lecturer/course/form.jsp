<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			<acme:input-textbox readonly="true" code="lecturer.course.form.label.code" path="code"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:input-textbox code="lecturer.course.form.label.code" path="code"/>
		</jstl:when>
	</jstl:choose>
	<acme:input-textbox code="lecturer.course.form.label.title" path="title"/>
	<acme:input-textarea code="lecturer.course.form.label.recap" path="recap"/>
	<acme:input-money code="lecturer.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-money readonly="true" code="lecturer.course.form.label.exchange" path="exchange"/>
	<acme:input-url code="lecturer.course.form.label.link" path="link"/>
	<acme:input-checkbox readonly="true" code="lecturer.course.form.label.isTheory" path="isTheory"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="lecturer.course.form.buttton.lectures" action="/lecturer/lecture/list?masterId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="lecturer.course.form.buttton.lectures" action="/lecturer/lecture/list?masterId=${id}"/>
			<acme:submit code="lecturer.course.form.button.update" action="/lecturer/course/update"/>
			<acme:submit code="lecturer.course.form.button.delete" action="/lecturer/course/delete"/>
			<acme:submit code="lecturer.course.form.button.publish" action="/lecturer/course/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.course.form.button.create" action="/lecturer/course/create"/>
		</jstl:when>
	</jstl:choose>

</acme:form> 