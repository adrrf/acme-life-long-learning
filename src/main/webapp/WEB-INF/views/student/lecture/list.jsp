<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="student.lecture.list.label.title" path="title" width="10%"/>
	<acme:list-column code="student.lecture.list.label.recap" path="recap" width="40%"/>
	<acme:list-column code="student.lecture.list.label.learningTime" path="learningTime" width="40%"/>
	<acme:list-column code="student.lecture.list.label.body" path="body" width="40%"/>
	<acme:list-column code="student.lecture.list.label.isTheory" path="theory" width="40%"/>
	<acme:list-column code="student.lecture.list.label.link" path="link" width="40%"/>
</acme:list>