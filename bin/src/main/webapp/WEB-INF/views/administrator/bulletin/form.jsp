<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-moment readonly="true"
		code="administrator.bulletin.form.label.instantiationMoment"
		path="instantiationMoment" />
	<acme:input-textbox code="administrator.bulletin.form.label.title"
		path="title" />
	<acme:input-textarea code="administrator.bulletin.form.label.message"
		path="message" />
	<acme:input-money code="administrator.bulletin.form.label.flag"
		path="flag" />
	<acme:input-url code="administrator.bulletin.form.label.link" path="link" />

</acme:form>