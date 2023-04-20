<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="student.course.form.label.code" path="code"/>
	<acme:input-textbox code="student.course.form.label.title" path="title"/>
	<acme:input-textarea code="student.course.form.label.recap" path="recap"/>
	<acme:input-money code="student.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-url code="student.course.form.label.link" path="link"/>
	<acme:input-textbox readonly="true" code="student.course.form.label.lecturerId" path="lecturerId"/>
	
</acme:form>

<acme:button code="student.course.enrolment.button.create" action="/student/enrolment/create?courseId=${id}" />
<acme:button code="student.course.form.button.lectures" action="/student/lecture/list?masterId=${id}"/>

