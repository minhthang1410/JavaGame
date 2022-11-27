function profile() {
	const id = document.getElementById("sessionId").value;
	console.log(id);
	const api = "/JavaCBJS-1/api/user?id=" + id + "&action=private_info";
	
	fetch(api)
	.then((response) => response.json())
	.then((data) => {
		document.getElementById("id").innerText = data["id"];
		document.getElementById("money").innerText = data["money"];
		document.getElementById("email").innerText = data["email"];
		document.getElementById("credit_card").innerText = data["credit_card"];
		document.getElementById("username").innerText = data["username"];
		document.getElementById("bio").placeholder = data["bio"];
		document.getElementById("avatar").src = "/JavaCBJS-1/upload/" + data["avatar"];
	});
}

profile();