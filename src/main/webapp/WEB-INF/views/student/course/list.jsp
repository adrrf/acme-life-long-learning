<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="student.course.list.label.code" path="code" width="10%"/>
	<acme:list-column code="student.course.list.label.title" path="title" width="40%"/>
	<acme:list-column code="student.course.list.label.recap" path="recap" width="40%"/>
	<acme:list-column code="student.course.list.retailPrice" path="retailPrice" width="40%"/>
	<acme:list-column code="student.course.list.label.link" path="link" width="40%"/>
</acme:list>