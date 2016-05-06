<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>forDit</title>
<meta charset="UTF-8">
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
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
					<li><a href="Forma19UserStart" method="POST"><i
							class="icons"
							style="background: url(images/novosti.png) no-repeat"></i>Форма
							19 (сотрудники)</a></li>
					<li><a href="Forma19DitStart"><i class="icons"
							style="background: url(images/stati.png) no-repeat"></i>Форма 19
							(отдел)</a></li>
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
			<div width="1000">
				<p class="headerP">Расчет плотности ошибок.</p>
			</div>
			<br>

			<form action="PlotnostUser" method="GET">


				<select size="1" name="thisUser" onchange="">
					<option selected name="tUser" value="${userId}"><c:out
							value="${nameUser}" /></option>
					<c:forEach items="${users}" var="user">
						<option value="<c:out value="${user.id}" />"><c:out
								value="${user.name}" /></option>
					</c:forEach>
				</select> <select size="1" name="thisPeriod" onchange="">
					<option selected name="tPeriod" value="${periodId}"><c:out
							value="${periodName}" /></option>
					<c:forEach items="${periods}" var="period">
						<option value="<c:out value="${period.id}" />"><c:out
								value="${period.name}" /></option>
					</c:forEach>
					<input type="text" name="inPlotnost" value="" />

				</select> <input type="submit" name="submit" value="отправить" />



			</form>
			<br>
			<p>Плотность предыдущего квартала = ${lastPlotnost}</p>

			<br>


			<table border="1" width="100%">
				<tr>
					<td width="10%" class="tablecent"><b><c:out
								value="ошибки " /></b></td>
					<td width="25%" class="tablecent"><b><c:out
								value="списания раб " /></b></td>
					<td width="25%" class="tablecent"><b><c:out
								value="списания тест " /></b></td>
					<td width="25%" class="tablecent"><b><c:out
								value="плотность " /></b></td>
					<td width="15%" class="tablecent"><b><c:out
								value="Динамика " /></b></td>
				</tr>
				<tr>
					<td width="10%" class="tableText"><b><c:out
								value="${clientNseSize}  " /></b></td>
					<td width="25%" class="tableText"><b><c:out
								value="${sumTaskSheetDevelopers} " /></b></td>
					<td width="25%" class="tableText"><b><c:out
								value="${sumTaskSheetTester} " /></b></td>
					<td width="25%" class="tableText"><b><c:out
								value="${plotnost} " /></b></td>
					<td width="15%" class="tableText"><b><c:out
								value="${result}  " /></b></td>
				</tr>


			</table>


			<br> <br>
			<p>
				<button id="slide" href="#">Показать детализацию</button>
			</p>
			<br>

			
			<table border="1" width="100%" class="slideTable"
				style="display: none">
				<tr><td>Списания тестировщика</td></tr>
				<tr>
					<td width="25%" class="tablecent"><b><c:out
								value="id списания" /></b></td>

					<td width="25%" class="tablecent"><b><c:out
								value="дата списания " /></b></td>
					<td width="25%" class="tablecent"><b><c:out
								value="Задача " /></b></td>
					<td width="25%" class="tablecent"><b><c:out
								value="время" /></b></td>
					
				</tr>


				<c:forEach items="${testerTimeSheet}" var="tts">


					<tr>
						<td><c:out value="${tts.id}" /></td>
						<td><fmt:formatDate type="date" value="${tts.dateIn}" /></td>
						<td><c:out value="${tts.clientOrderId}" /></td>
						<td><c:out value="${tts.workTime}" /></td>
					</tr>

				</c:forEach>
				<br>
			</table>
			<br>
			<br>
			
			<table border="1" width="100%" class="slideTable"
				style="display: none">
				<tr><td>Списания разработчиков</td></tr>
				<tr>
					<td width="20%" class="tablecent"><b><c:out
								value="Фио" /></b></td>
					<td width="20%" class="tablecent"><b><c:out
								value="id списания" /></b></td>

					<td width="20%" class="tablecent"><b><c:out
								value="дата списания " /></b></td>
					<td width="20%" class="tablecent"><b><c:out
								value="Задача " /></b></td>
					<td width="20%" class="tablecent"><b><c:out
								value="время" /></b></td>
					
				</tr>


				<c:forEach items="${developersTimeSheet}" var="tts">
					
					

					<tr>
						<td><c:out value="${tts.name}" /></td>
						<td><c:out value="${tts.id}" /></td>
						<td><fmt:formatDate type="date" value="${tts.dateIn}" /></td>
						<td><c:out value="${tts.clientOrderId}" /></td>
						<td><c:out value="${tts.workTime}" /></td>
					</tr>

				</c:forEach>
				<br>
			</table>





		</div>
</body>
</html>