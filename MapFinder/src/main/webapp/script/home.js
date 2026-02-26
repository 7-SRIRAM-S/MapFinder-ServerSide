(() => {


	Home = (function() {

		var Api = {

			Show: {
				UserDetailUrl: "/MapFinder/dashboard/userdetails",
				TopPlayerListUrl: "/MapFinder/dashboard/topplayers",
				NotificationListUrl: "/MapFinder/dashboard/announcements",
				Notifications: "/Mapfinder/friendrequest/getrequests"
			}
		}

		var Img = {
			portyPapper: "./icons/party-popper.png",
			tropy: "./icons/trophy(1).png",
			lightning: "./icons/lightning.png",
			clock: "./icons/clock.png"
		}

		return {
			init: function() {
				Home.show.UserDetail();
				Home.show.TopPlayersList();
				Home.show.NotificationList();
			},
			show: {
				UserDetail: async function() {
					var data = await Home.get.UserDetail();
					let apiName = data?.data?.USERNAME;
					let localName = localStorage.getItem("username");
					if (apiName) {
						$(".head-name").text(apiName);
						localStorage.setItem("username", apiName);
					} else if (localName) {
						$(".head-name").text(localName);

					}
					if (emptyCheck(data.data.HINTS)) {
						$(".hint-count").text(data.data.HINTS);
					}

					if (emptyCheck(data.data.POINTS)) {
						$(".point-count").text(data.data.POINTS);
					}

					if (emptyCheck(data.data.CERTIFICATE)) {
						$(".certificate-count").text(data.data.CERTIFICATE);
					}

				},
				TopPlayersList: async function() {
					let data = await Home.get.TopPlayersList();

					if (data.data.length == 0) {
						$("#top-player-list").html("<div class='no-player'>No Player found</div>");
						return;
					}

					if (data.data.length < 5) {
						for (i = data.data.length + 1; i <= 5; i++) {
							$(`.top-${i}`).hide();
						}
					}

					for (let i = 0; i < data.data.length; i++) {
						$(`#top-${i + 1}-name`).text(data.data[i].userName);
						$(`#top-${i + 1}-points`).text(data.data[i].totalScore);
					}

				},
				NotificationList: async function() {



					let data = await Home.get.NotificationList();


					if (data.data.length == 0) {
						$("#notification-container").html("<div class='no-player'>No Message For You </div>");
						return;
					}

					for (let i = 0; i < data.data.length; i++) {
						$($(".notifiction-message")[i]).html(buildMessage(data.data[i].message, Img));
						$($(".notification-time")[i]).html(`<img src=${Img.clock} id="clock">` + dateDifference(data.data[i].createdAt));
					}
				}
			},
			get: {
				UserDetail: async function() {

					let url = Api.Show.UserDetailUrl
					try {
						let response = await API.get(url);
						return response.data;
					} catch {
						//                    window.location.assign("/MapFinder/home.html");
					}
				},
				TopPlayersList: async function() {

					let url = Api.Show.TopPlayerListUrl
					try {
						let response = await API.get(url);
						return response.data;
					} catch {
					}
				},
				NotificationList: async function() {
					let url = Api.Show.NotificationListUrl
					try {
						let response = await API.get(url);
						return response.data;
					} catch {
					}
				},
				Notification: async function() {
					let url = Api.Show.Notifications
					try {
						let response = await API.get(url);
						return response.data;
					} catch {
					}

				}
			}
		}
	})();

	Home.init();

	let notificationBtn = document.querySelector("#notification");

	if (notificationBtn) {
		notificationBtn.addEventListener("click", async () => {
			try {
				const res = await fetch("/MapFinder/notifications");
				const msg = await res.json();
				const data = msg.data;
				NOTIFY_PANEL.toggle(data);
			} catch (err) {
				console.error(err);
			}
		});
	}

	function buildMessage(value, Img) {
		if (!value) {
			return "message not found";
		}
		let data = value.toLowerCase();
		if (data.includes("hint") || data.includes("good")) {
			return `<img  src=${Img.portyPapper} >  ` + value;
		} else if (data.includes("certificate") || data.includes("earn")) {
			return `<img  src=${Img.tropy} >  ` + value;
		} else if (data.includes("challenged") || data.includes("mode")) {
			return `<img src=${Img.lightning}>  ` + value;
		} else {
			return value;
		}
	}

	function dateDifference(value) {
		let currentTime = new Date();
		let previousTime = new Date(value);
		let diff = currentTime - previousTime;

		let day = Math.floor(diff / (1000 * 60 * 60 * 24));
		let hour = Math.floor(diff / (1000 * 60 * 60));
		let minute = Math.floor(diff / (1000 * 60));

		if (day == 1) {
			return "1 day ago";
		}
		else if (day > 1) {
			return day + " days ago";
		}

		if (hour == 1) {
			return "1 hours ago";
		} else if (hour > 1) {
			return hour + " hours ago";
		}

		if (minute < 10 && minute > 0) {
			return "few minute ago";
		} else {
			return minute + " minutes ago"
		}
	}

	function emptyCheck(value) {
		if ((value !== undefined) && (value !== null)) {
			return true;
		} else {
			//        window.location.assign("home");
			return;
		}
	}




	document.addEventListener("visibilitychange", async function() {
		if (!document.hidden) {
			let session = await SESSION.checkSession();
			if (session) {
				WINDOW.blockGoBack();
			}
			else {
				logout();
			}
		}
	});






	window.onload = async function() {
		let session = await SESSION.checkSession();
		if (!session) {
			WINDOW.changeUrl("signin");
		}
		WINDOW.blockGoBack();
	}

	window.addEventListener("popstate", async (event) => {
		console.log("clicking back button");
		let session = await SESSION.checkSession();
		if (session) {
			console.log("local is alive");

			WINDOW.blockGoBack();
		}
		else {
			console.log("no local going to signin");

			WINDOW.changeUrl("signin");
		}
	});

	const user = document.querySelector("#user");



	document.addEventListener("DOMContentLoaded", setUserName);
	window.addEventListener("pageshow", setUserName);

	function setUserName() {

		username = localStorage.getItem("username") || "Boss";

		const userIcon = document.querySelector("#user img");
		if (userIcon) {
			userIcon.title = "Hello " + username + "!";
		}

		const userName = document.querySelector(".head-name");
		if (userName) {
			userName.textContent = username;
		}
	}

	const logoutBtn = document.querySelector("#logout-btn");
	if (logoutBtn) {
		logoutBtn.addEventListener("click", () => {
			logoutBtn.disabled = true;
			logout();
		});
	}


	function logout() {
		console.log("log out...")
		localStorage.clear();
		fetch("/MapFinder/logout").then(res => res.json())
			.then((data) => {
				if (data.status === "success") {
					localStorage.clear();
					console.log(data);
					WINDOW.changeUrl("signin");
				}
			}).catch((err) => console.log(err));
	}

	window.playGame = function(gameType) {
		switch (gameType) {
			case 'learnMode':
				window.location.href = "test_map.html";
				break;
			case 'writeMode':
				window.location.href = "write_mode.html";
				break;
			case 'rectifyMode':
				window.location.href = "rectifymode.html";
				break;
			case 'selfMode':
				window.location.href = "self_evaluate_mode.html";
				break;
			case 'botMode':
				window.location.href = "botmode.html";
				break;
			case 'quizMode':
				window.location.href = "quizmode.html";
		}
	}



		
	//	
	//	fetch("/MapFinder/friendrequest/reject").then((res)=>res.text())
	//	.then((msg)=>{
	//		console.log(msg);
	//	})





})();
