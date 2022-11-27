<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Game</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=VT323&display=swap"
	rel="stylesheet">
<link href="https://unpkg.com/nes.css@2.3.0/css/nes.min.css"
	rel="stylesheet" />
<link rel="stylesheet" href="./static/css/style.css">
</head>
<body>
	<%
	Object obj = request.getSession().getAttribute("id");
	String id = String.valueOf(obj);
	String param = request.getParameter("lang");
	String classBtnLang = "";
	String langPath = "./static/html/";
	String cookieLang = "en.html";
	String nextParam = "vi";
	if (request.getCookies() != null) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("lang")) {
		cookieLang = cookie.getValue();
		if (cookie.getValue().equals("vi.html")) {
			classBtnLang = "is-success";
			nextParam = "en";
		}
		if (param != null) {
			if (param.equals("vi")) {
				cookie.setValue("vi.html");
				cookieLang = cookie.getValue();
				nextParam = "en";
				classBtnLang = "is-success";
				cookie.setMaxAge(0);
				Cookie newCookieLang = new Cookie("lang", "vi.html");
				response.addCookie(cookie);
				response.addCookie(newCookieLang);
			} else if (param.equals("en")) {
				cookie.setValue("en.html");
				cookieLang = cookie.getValue();
				nextParam = "vi";
				classBtnLang = "";
				cookie.setMaxAge(0);
				Cookie newCookieLang = new Cookie("lang", "en.html");
				response.addCookie(cookie);
				response.addCookie(newCookieLang);
			}
		}
			} else {
				Cookie initCookieLang = new Cookie("lang", "en.html");
				response.addCookie(initCookieLang);
			}
		}
	} else {
		Cookie initCookieLang = new Cookie("lang", "en.html");
		response.addCookie(initCookieLang);
	}

	langPath = langPath + cookieLang;
	%>

	<div class="header">
		<jsp:include page='<%=langPath%>'></jsp:include>
		<div class="lang-btn-div">
			<a class="nes-btn <%=classBtnLang%>" href="?lang=<%=nextParam%>">VN</a>
		</div>
	</div>
	<div class="container">
		<div class="main-content nes-container with-title">
			<h3 class="title">Game</h3>
			<input type="hidden" id="sessionId" value="<%=id%>">
			<h3>
				Your Money: <span id="money"></span>
			</h3>
			<div class="game">
				<div class="game-content">
					<label> <input id="low" type="radio" class="nes-radio" name="answer"
						checked /> <span class="nes-text is-error">LOW (3-16)</span>
					</label>
				</div>
				<div class="game-content-dice">
					<div class="dice-wrapper">
						<div class="dice-img">
							<img id="die-1" src="./static/img/9.png">
						</div>
						<div class="dice-img">
							<img id="die-2" src="./static/img/9.png">
						</div>
						<div class="dice-img">
							<img id="die-3" src="./static/img/9.png">
						</div>
					</div>
					<p style="font-size: 30px;">
						Your roll is <span id="total">27</span>
					</p>
					<button onclick="roll()" id="btn-roll">ROLL</button>
				</div>
				<div class="game-content">
					<label> <input id="high" type="radio" class="nes-radio" name="answer"
						checked /> <span class="nes-text is-success">HIGH (17-27)</span>
					</label>
				</div>
			</div>
		</div>
	</div>

	<script src="./static/js/game.js"></script>
</body>
</html>