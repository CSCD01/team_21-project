<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
	<head>
		<title>TRUNK-4762 TestCase</title>
	</head>

	<body>
		<table>
			<tr>
				<!--Display the superscript text 3-->
				<td>Units: <c:out value="${'<sup>3</sup>'}" escapeXml="false"/></td>
			</tr>
			<tr>
				<!--Display the subscript text 3-->
				<td>Units: <c:out value="${'<sub>3</sub>'}" escapeXml="false"/></td>
			</tr>
			<tr>
				<!--Display the plain text “cell/mm” with a superscript text 3-->
				<td>Units: <c:out value="${'cell/mm <sup>3</sup>'}" escapeXml="false"/></td>
			</tr>
			<tr>
				<!--Display the plain text "cell/mm" with a subscript text 3-->
				<td>Units: <c:out value="${'cell/mm <sub>3</sub>'}" escapeXml="false"/></td>
			</tr>
			<tr>
				<!--Alert-->
				<td>Units: <c:out value="${'<script>alert('xss')</script>'}" escapeXml="false"/></td>
			</tr>
		</table>
	</body>
</html>