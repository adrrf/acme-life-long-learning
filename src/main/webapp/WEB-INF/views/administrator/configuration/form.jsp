<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.configuration.form.label.currency" path="currency"/>	
	<acme:input-textbox code="administrator.configuration.form.label.acceptedCurrencies" placeholder="field1;field2;field3..." path="acceptedCurrencies"/>
	<acme:input-textbox code="administrator.configuration.form.label.spamwordsen" placeholder="field1;field2;field3..." path="spamWordsEn"/>
	<acme:input-textbox code="administrator.configuration.form.label.spamwordses" placeholder="field1;field2;field3..." path="spamWordsEs"/>

	<acme:submit code="administrator.configuration.form.button.update" action="/administrator/configuration/update"/>
</acme:form>