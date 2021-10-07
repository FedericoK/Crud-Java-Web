<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Menu de opciones</title>
</head>
<body>
	<h1>Menu de opciones Productos</h1>
	
	<table border="1">
		<tr>
			<td><a href="producto?opcion=crear">Crear Producto</a></td>		
		</tr>
		<tr>
			<td><a href="producto?opcion=listar">Listar Producto</a></td>
		</tr>
	</table>
</body>
</html>