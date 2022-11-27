const images = ["./static/img/1.png",
	"./static/img/2.png",
	"./static/img/3.png",
	"./static/img/4.png",
	"./static/img/5.png",
	"./static/img/6.png",
	"./static/img/7.png",
	"./static/img/8.png",
	"./static/img/9.png"];
const dice = document.querySelectorAll(".dice-img");
const die1 = document.querySelector("#die-1");
const die2 = document.querySelector("#die-2");
const die3 = document.querySelector("#die-3");
const total = document.querySelector("#total");
const btnRoll = document.getElementById("btn-roll");
const high = document.getElementById("high");
const low = document.getElementById("low");
var currentMoney;

function roll() {
	dice.forEach(function(die) {
		die.classList.add("shake");
		total.innerHTML = "...";
		total.classList.add("blink");
		btnRoll.disabled = true;
	});
	setTimeout(function() {
		dice.forEach(function(die) {
			die.classList.remove("shake");
			total.classList.remove("blink");
			btnRoll.disabled = false;
		});
		let dieOneValue = Math.floor(Math.random() * 9);
		let dieTwoValue = Math.floor(Math.random() * 9);
		let dieThreeValue = Math.floor(Math.random() * 9);
		let totalValue = dieOneValue + dieTwoValue + dieThreeValue;
		die1.setAttribute("src", images[dieOneValue]);
		die2.setAttribute("src", images[dieTwoValue]);
		die3.setAttribute("src", images[dieThreeValue]);
		total.innerHTML = ((dieOneValue + 1) + (dieTwoValue + 1) + (dieThreeValue + 1));
		if (totalValue >= 3 && totalValue <= 16) {
			if (low.checked) {
				currentMoney += 100;
			} else {
				currentMoney -= 100;
			}
			updateMoney(currentMoney);
		} else if (totalValue >= 17 && totalValue <= 27) {
			if (high.checked) {
				currentMoney += 100;
			} else {
				currentMoney -= 100;
			}
			updateMoney(currentMoney);
		}
	},
		2000
	);
}

function updateMoney(money) {
	const id = document.getElementById("sessionId").value;
	let api = "/JavaCBJS-1/api/user?id=" + id + "&action=update_money&money=" + money;
	document.getElementById("money").innerText = money;
	fetch(api, {
		method: "POST"
	});
}

function setMoney() {
	const id = document.getElementById("sessionId").value;
	let api = "/JavaCBJS-1/api/user?id=" + id + "&action=private_info";
	fetch(api)
		.then((response) => response.json())
		.then((data) => {
			document.getElementById("money").innerText = data["money"];
			currentMoney = data["money"];
		});
}

setMoney();