function displayRankTable() {
	const rankingTableBody = document.getElementById("ranking-table-body");
	fetch('/JavaCBJS-1/api/user?action=ranking')
		.then((response) => response.json())
		.then((data) => {
			view(data[0]["id"]);
			for (i = 0; i < 5; i++) {
				var tr = document.createElement("tr");
				rankingTableBody.appendChild(tr);
				var td1 = document.createElement("td");
				var td2 = document.createElement("td");
				var td3 = document.createElement("td");
				var td4 = document.createElement("td");
				td1.innerText = data[i]["id"];
				td2.innerText = data[i]["username"];
				td3.innerText = data[i]["money"];
				td4.innerHTML = "<button class='table-view-btn' onclick='view(" + td1.innerText + ")'>View</button>";
				rankingTableBody.appendChild(tr).appendChild(td1);
				rankingTableBody.appendChild(tr).appendChild(td2);
				rankingTableBody.appendChild(tr).appendChild(td3);
				rankingTableBody.appendChild(tr).appendChild(td4);
			}
		});
}
displayRankTable();

function view(id) {
	fetch("/JavaCBJS-1/api/user?id=" + id + "&action=public_info")
		.then((response) => response.json())
		.then((data) => {
			document.getElementById("id").innerText = data["id"];
			document.getElementById("username").innerText = data["username"];
			document.getElementById("bio").value = data["bio"];
			document.getElementById("avatar").src = "/JavaCBJS-1/upload/" + data["avatar"];
		});
}