<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>forDit</title>
<meta charset="UTF-8">
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
					<li><a href="TestServlet2" method="POST"><i class="icons"
							style="background: url(images/novosti.png) no-repeat"></i>Форма
							19 (сотрудники)</a></li>
					<li><a href="Forma19Dit"><i class="icons"
							style="background: url(images/stati.png) no-repeat"></i>Форма 19
							(отдел)</a></li>
					<li><a href="#"><i class="icons"
							style="background: url(images/free.png) no-repeat"></i></a></li>
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
			<div width="1000">
				<p class="headerP">Форма №19, KPI Эффективность тестирования</p>
				<p class="headerP">Отдел тестирования.</p>
			</div>
			<br>

			<form action="Forma19Dit" method="GET">
				<input type="date" name="dateStart" value="${dateStart}" /> <input
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
					<td width="25%" class="tablecent"><b><c:out
								value="Количество ошибок 1 приоритета " /></b></td>
					<td width="25%" class="tablecent"><b><c:out
								value="Количество внешних ошибок 1 приоритета " /></b></td>
					<td width="15%" class="tablecent"><b><c:out
								value="KPI тестирования " /></b></td>
				</tr>
				<tr>
					<td width="10%" class="tableText"><b><c:out
								value="${sizeCo}  " /></b></td>
					<td width="25%" class="tableText"><b><c:out
								value="${clientNseSize} " /></b></td>
					<td width="25%" class="tableText"><b><c:out
								value="${kolDitNseFirst} " /></b></td>
					<td width="25%" class="tableText"><b><c:out
								value="${clientFirst} " /></b></td>
					<td width="15%" class="tableText"><b><c:out
								value="${kpi}  " /></b></td>
				</tr>


			</table>
			<br>
			<br>
			<p>Детализация</p>
			<br>
			<table border="1" width="100%">
				<tr>
					<td width="10%" class="tablecent"><b><c:out
								value="Задача " /></b></td>
					<td width="10%" class="tablecent"><b><c:out
								value="Проект " /></b></td>
					<td width="10%" class="tablecent"><b><c:out value="Дата " /></b></td>
					<td width="70%" class="tablecent"><b><c:out
								value="Название " /></b></td>
				</tr>
				<c:forEach items="${clientOrder}" var="co">
					<tr>
						<td><c:out value="${co.id}" /></td>
						<td><c:out value="${co.projectId}" /></td>
						<td><fmt:formatDate type="date" value="${co.inDateTime}" /></td>
						<td><c:out value="${co.suject}" /></td>
					</tr>
				</c:forEach>




				</div>

				</div>
</body>
</html>