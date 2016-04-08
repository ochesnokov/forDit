<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>forDit</title>
<meta charset="UTF-8">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="JS/slide.js"></script>
<link href="styles/style.css" rel="stylesheet" type="text/css">
<script src="main.js"></script>
</head>
<body>
	<div id="page_align" class="b3radius">
		<div id="header">
			<div id="header_nav">
				<ul>
					<li><a href="index.jsp">Главная</a></li>
					<li><a href="">Тест</a></li>
					<li><a href="">Тест</a></li>
					<li><a href="#">Тест</a></li>
					<li><a href="#">Тест</a></li>
					<li><a href="#">Тест</a></li>
					<li><a href="#">Тест</a></li>
					<li><a href="#">Тест</a></li>
				</ul>

			</div>
			<div id="header_cont">
				<h1>
					<span style="color: #98CA60">For</span>Dit
				</h1>
				<span class="h1discription">Персональный помощник</span>
				<div class="header_right f_right"></div>
			</div>
		</div>
		<div id="sidebar">
			<div class="sb_header">
				<i class="icons" style="background: url(images/menu.png) no-repeat"></i>Меню
			</div>
			<div class="sb_nav">
				<ul>
					<li><a href="index.jsp"><i class="icons"
							style="background: url(images/glavnaya.png) no-repeat"></i>Главная</a></li>
					<li><a href="Forma19UserStart" method="POST"><i class="icons" 
							style="background: url(images/novosti.png) no-repeat"></i>Форма 19 (сотрудники)</a></li>
					<li><a href="Forma19Dit"><i class="icons" 
							style="background: url(images/stati.png) no-repeat"></i>Форма 19 (отдел)</a></li>
					<li><a href="PlotnostUserStart"><i class="icons" 
							style="background: url(images/free.png) no-repeat"></i>Плотность</a></li>
					<li><a href="#"><i class="icons"
							style="background: url(images/smeshno.png) no-repeat"></i></a></li>
					<li><a href="#"><i class="icons"
							style="background: url(images/glavnaya.png) no-repeat"></i>Element</a></li>
					<li><a href="#"><i class="icons"
							style="background: url(images/free.png) no-repeat"></i>Element</a></li>
					<li><a href="#"><i class="icons"
							style="background: url(images/stati.png) no-repeat"></i>Element</a></li>
				</ul>
			</div>

		</div>
		<div id="content">
			<br> <br>
			<div width="1000"><p class="headerP">Форма №19, KPI Эффективность тестирования</p></div>
			<br>

			<form action="Forma19User" method="GET">
				<select size="1" name="thisUser" onchange="">
					<option selected name="tUser" value="${userId}"><c:out
							value="${nameUser}" /></option>
					<c:forEach items="${users}" var="user">
						<option value="<c:out value="${user.id}" />"><c:out
								value="${user.name}" /></option>
					</c:forEach>
				</select> <input type="date" name="dateStart" value="${dateStart}" /> <input
					type="date" name="dateFinish" value="${dateFinish}" /> <input
					type="submit" name="submit" value="отправить" />

			</form>
			
			<br>
			
			
			<table border="1" width="100%">
				<tr>
					<td width="10%" class="tablecent"><b><c:out
								value="Всего ошибок " /></b></td>
					<td width="25%" class="tablecent"><b><c:out
								value="Количество внешних ошибок " /></b></td>
					<td width="25%" class="tablecent"><b><c:out value="Количество ошибок 1 приоритета " /></b></td>
					<td width="25%" class="tablecent"><b><c:out value="Количество внешних ошибок 1 приоритета " /></b></td>
					<td width="15%" class="tablecent"><b><c:out
								value="KPI тестирования " /></b></td>
				</tr>
				<tr>
					<td width="10%" class="tableText"><b><c:out value="${sizeCo}  " /></b></td>
					<td width="25%" class="tableText"><b><c:out value="${clientNseSize} " /></b></td>
					<td width="25%" class="tableText"><b><c:out value="${kolDitNseFirst} " /></b></td>
					<td width="25%" class="tableText"><b><c:out value="${clientFirst} " /></b></td>
					<td width="15%"class="tableText"><b><c:out value="${kpi}  " /></b></td>
				</tr>
			
		
			</table>
	<br><br>
	<p><button id="slide" href="#">Показать детализацию</button></p>
	<br>
		
			
			
				
					
				<c:forEach items="${manyProducts}" var="mp">
				
				<table border="1" width="100%" class = "slideTable" style=" display:none" >
				<tr><td width="15%"><h2><c:out value="${mp.sysName}" />	</h2></td></tr>
				<tr>
					<td width="10%" class="tablecent"><b><c:out
								value="Задача " /></b></td>
					
					<td width="15%" class="tablecent"><b><c:out
								value="Модуль " /></b></td>
					<td width="15%" class="tablecent"><b><c:out
								value="От имени " /></b></td>
					<td width="10%" class="tablecent"><b><c:out 
								value="Дата " /></b></td>
					<td width="50%" class="tablecent"><b><c:out
								value="Название " /></b></td>
				</tr>
				
				
				<c:forEach items="${clientOrder}" var="co">
				
					<c:if test="${co.productId==mp.id}">
						<tr>
							<td><c:out value="${co.id}" /></td>
							
							<td><c:out value="${co.moduleName}" /></td>
							<td><c:out value="${co.onNameString}" /></td>
							<td><fmt:formatDate type="date" value="${co.inDateTime}" /></td>
							<td><c:out value="${co.suject}" /></td>
						</tr>
					</c:if>
				</c:forEach>
				<br>
				</table>
				<br>	
		</c:forEach>
				
			

	
		


		</div>

	
</body>
</html>