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
	<div class="main">
		<header class="column">
			<h1 class="blink">Bijuu Sicbo</h1>
			<h2>Login</h2>
		</header>
		<div class="column">
			<form method="post">
				<div class="nes-field">
					<label for="username">Username</label> 
					<input type="text" id="username" name="username" class="nes-input" placeholder="Enter Username" required>
				</div>
				<div class="nes-field">
					<label for="password">Password</label> 
					<input id="password" name="password" type="password" class="nes-input" placeholder="Enter Password" required>
				</div>
				${authNoti }
				<button formaction="/JavaCBJS-1/auth?action=login" type="submit" class="nes-btn is-primary">
					Login
				</button>
				<button formaction="/JavaCBJS-1/auth?action=signup" type="submit" class="nes-btn is-success">
					Sign in
				</button>
			</form>
		</div>
	</div>
</body>
</html>