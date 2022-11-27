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
			<h3 class="title">Profile</h3>
			<input type="hidden" id="sessionId" value="<%=id%>">
			<h3>
				Your Money: <span id="money"></span>
			</h3>
			<div class="profile">
				<div class="profile-avatar">
					<img id="avatar" src="" alt="avatar">
					<form method="post" enctype="multipart/form-data">
						<label class="nes-btn">
							<span>Select your file</span>
							<input type="file" name="file" size="60">
						</label>
						<input type="hidden" name="id" value="<%=id%>">
						<button formaction="/JavaCBJS-1/api/user?action=update_avatar" class="nes-btn is-primary">Update Avatar</button>
					</form>
					<h3 class="nes-text is-success" style="display: none;">Update success</h3>
				</div>

				<div class="profile-info">
					<div class="profile-info-column-credential">
						<ul>
							<li>
								<div class="profile-info-column-credential-list">
									<ul class="profile-info-column-credential-list-item">
										<li>User ID:</li>
										<li>Username:</li>
										<li>Creditcard:</li>
									</ul>
									<ul class="profile-info-column-credential-list-item">
										<li id="id"></li>
										<li id="username"></li>
										<li id="credit_card"></li>
									</ul>
								</div>
							</li>
							<li>Email: <span id="email"></span></li>
						</ul>

						<p class="blink">Upload file for KYC and received 10.000 coin (front, back id card and 1 selfie):</p>
						<form method="post" enctype="multipart/form-data">
							<label class="nes-btn"> 
								<span>Select your file (.zip)</span>
								<input type="file" name="file" size="60">
							</label>
							<input type="hidden" name="id" value="<%=id%>">
							<button formaction="/JavaCBJS-1/api/user?action=update_kyc" class="nes-btn is-primary">Upload</button>
						</form>
						<h3 class="nes-text is-success" style="display: none;">Upload success</h3>
					</div>
					<div class="profile-info-column-bio">
						<form method="post">
							<div class="nes-field">
								<label for="name_field">Creditcard</label>
								<input type="text" name="credit_card" class="nes-input" placeholder="Update your credit card">
							</div>
							<div class="nes-field">
								<label for="name_field">Email</label>
								<input type="text" name="email" class="nes-input" placeholder="Update your email">
							</div>
							<label for="textarea_field">Biography</label>
							<textarea id="bio" name="bio" class="nes-textarea" rows="2" placeholder="Update your biography" maxlength="100"></textarea>
							<input type="hidden" name="id" value="<%=id%>">
							<button formaction="/JavaCBJS-1/api/user?action=update_profile" class="nes-btn is-primary">Save change</button>
							<h3 class="nes-text is-success" style="display: none;">Update success</h3>
						</form>

					</div>
				</div>
			</div>

		</div>
	</div>

	<script src="./static/js/profile.js"></script>
</body>
</html>