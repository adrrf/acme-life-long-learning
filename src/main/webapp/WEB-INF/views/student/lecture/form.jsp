<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="student.lecture.form.label.title" path="title"/>
	<acme:input-textarea code="student.lecture.form.label.recap" path="recap"/>
	<acme:input-textbox code="student.lecture.form.label.learningTime" path="learningTime"/>
	<acme:input-textbox code="student.lecture.form.label.body" path="body"/>
	<acme:input-checkbox code="student.lecture.form.label.isTheory" path="theory"/>
	<acme:input-url code="student.course.form.label.link" path="link"/>

</acme:form>