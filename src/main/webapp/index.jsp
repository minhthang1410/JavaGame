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
	<link href="https://fonts.googleapis.com/css2?family=VT323&display=swap" rel="stylesheet">
	<link href="https://unpkg.com/nes.css@2.3.0/css/nes.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="./static/css/style.css">
</head>
<body>
	<%
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
		<jsp:include page="<%=langPath%>"></jsp:include>
		<div class="lang-btn-div">
			<a class="nes-btn <%=classBtnLang%>" href="?lang=<%=nextParam%>">VN</a>
		</div>
	</div>
	    <div class="container">
        <div class="main-content nes-container with-title">
            <h3 class="title">Ranking</h3>
            <div class="ranking">
                <!-- Ranking Table -->
                <div class="ranking-table">
                    <table>
                        <thead>
                            <tr>
                                <th style="padding-right: 15px;">ID</th>
                                <th style="padding-right: 80px;">Username</th>
                                <th style="padding-right: 80px;">Money</th>
                                <th>Info</th>
                            </tr>
                        </thead>
                        <tbody id="ranking-table-body"></tbody>
                    </table>
                    <br>
                    <p><i>billionare is here !!</i></p>
                </div>
                <!-- View info page -->
                <div class="view-info-page nes-container">
                    <div class="info-container">
                        <img id="avatar" src="" alt="avatar"/>
                        <div class="info-public">
                            <ul>
                                <li>User ID:</li>
                                <li>Username:</li>
                                <li>Creditcard:</li>
                            </ul>
                        </div>
                        <div class="info-public-value">
                            <ul>
                                <li id="id"></li>
                                <li id="username"></li>
                                <li>*********</li>
                            </ul>
                        </div>
                    </div>
                    <div class="info-description">
                        <label for="textarea_field">Biography</label>
                        <textarea id="bio" rows="3" class="nes-textarea" readonly>Introduce youself</textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="./static/js/index.js"></script>
</body>
</html>