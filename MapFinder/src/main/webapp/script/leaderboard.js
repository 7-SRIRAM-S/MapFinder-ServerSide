LeaderBoard = (function() {

    var Api = {
        show: {
            LeaderBoardUrl: "/MapFinder/leaderboard"
        }
    };

    let topThreePlayer = "";
    let data;

    return {
        init: function() {
            $("#back").on("click", () => {
                window.location.assign("/MapFinder/home.html");
            });

            $("#global").css({
                "background": "linear-gradient(to right bottom ,#FFBF00,#FF7B00)",
                "color": "white"
            });

            $("#input").on("input", () => {
                LeaderBoard.show.search();
            })

            LeaderBoard.show.view();
        },
        show: {
            view: async function() {
                data = await LeaderBoard.get.view();
                let html = "";
                data = data.data;				
                if(!data){
	                    $(".ranking-container").html("<div id='no-data'>No User Found</div>");
	                    $(".leaderboard-toplist").html("<div id='no-data'>No User Found</div>");
	                    return;
                }
				
                console.log(data);

                for (let i = 0;i < 3;i++) {
                    $(`.user-name-${i + 1}`).text(data[i].userName);
                    $(`.user-points-${i + 1}`).text(data[i].totalScore);
                }

				const container = $(".ranking-container");
				container.empty();

				for (let i = 0; i < data.length; i++) {
				    const element = buildHtml(
				        i + 1,
				        data[i].userName,
				        data[i].totalScore,
				        data[i].totalCertificate,
				        data[i].isFriend
				    );

				    container.append(element);
				}

            },
            search: async function() {
                let value = $("#input").val().toLowerCase().trim();
                //              let  data = await LeaderBoard.get.view();
                //				data = data.data;
//                let count = 1;
                let html = "";

                LeaderBoard.show.TopOnePlayer("", "");

				const container = $(".ranking-container");
				container.empty();

				let count = 1;
				let found = false;

				for (let i = 0; i < data.length; i++) {

				    if (data[i].userName.toLowerCase().includes(value)) {

				        found = true;

				        if (count === 1) {
				            LeaderBoard.show.TopOnePlayer(
				                data[i].userName,
				                data[i].totalScore
				            );
				        }

				        const element = buildHtml(
				            data[i].rankPosition,
				            data[i].userName,
				            data[i].totalScore,
				            data[i].totalCertificate,
				            data[i].isFriend
				        );

				        container.append(element); 
				    }
				}

				if (!found) {
				    container.append("<div id='no-data'>No User Found</div>");
				}
				
                if (value == "") {
                    LeaderBoard.show.TopThreePlayers();
                }

            },
            TopOnePlayer: function(name, point) {
                if (topThreePlayer == "") {
                    topThreePlayer = $(".leaderboard-toplist").html();
                }
                let i = 1

                $(".leaderboard-toplist").html(
                    `<div id="topList">
                    <div class="user-icon">
                        <p>👦</p>
                    </div>
                    <div class="users-details">
                        <h1>👑</h1>
                        <p class="user-name-1">${name}</p>
                        <h2 class="user-points-1">${point}</h2>
                    
                    </div>
                </div>`)
                if (name == "") {
                    $(".leaderboard-toplist").html("<div id='no-data'>No User Found</div>");
                }
            },
            TopThreePlayers: function() {
                $(".leaderboard-toplist").html(topThreePlayer)

            }
        },
        get: {
            view: async function() {
                let url = Api.show.LeaderBoardUrl;

                try {
                    let response = await API.get(url);

                    return response.data;
                } catch {

                }
            }
        }
    }
})();

LeaderBoard.init();


function buildHtml(count, name, points, certificate, isFriend) {

    // Main container
    const ranking = document.createElement("div");
    ranking.className = "ranking";

    // ===== ranked-user-name =====
    const rankedUserName = document.createElement("div");
    rankedUserName.className = "ranked-user-name";

    const pointingPosition = document.createElement("div");
    pointingPosition.className = "pointing-position";
    pointingPosition.textContent = count;

    const nameWrapper = document.createElement("div");

    const h2 = document.createElement("h2");
    h2.textContent = `👦 ${name}`;

    nameWrapper.appendChild(h2);
    rankedUserName.appendChild(pointingPosition);
    rankedUserName.appendChild(nameWrapper);

    // ===== user-achievement =====
    const userAchievement = document.createElement("div");
    userAchievement.className = "user-achivement";

    // ---- friend request ----
    const friendRequest = document.createElement("div");
    friendRequest.className = "friend-request";

    const addUserImg = document.createElement("img");
    if (!isFriend) {
        addUserImg.src = "./images/add-user.png";
        addUserImg.alt = "adduser";
        addUserImg.title = "Make Friend";
    }
    else {
        addUserImg.src = "./icons/friends.png";
        addUserImg.alt = "friend";

    }


    friendRequest.appendChild(addUserImg);
    // ---- score section ----
    const scoreContainer = document.createElement("div");

    const scoreTitle = document.createElement("div");

    const trophyImg = document.createElement("img");
    trophyImg.src = "./images/trophy.png";
    trophyImg.alt = "trophy";

    scoreTitle.appendChild(trophyImg);
    scoreTitle.append(" Score");

    const scoreText = document.createElement("p");
    scoreText.className = "scoreText";
    scoreText.textContent = points;

    scoreContainer.appendChild(scoreTitle);
    scoreContainer.appendChild(scoreText);

    // ---- certificate section ----
    const certContainer = document.createElement("div");

    const certTitle = document.createElement("div");

    const medalImg = document.createElement("img");
    medalImg.src = "./images/medal.png";
    medalImg.alt = "medal";

    certTitle.appendChild(medalImg);
    certTitle.append(" Certs");

    const certText = document.createElement("p");
    certText.className = "scoreText";
    certText.textContent = certificate;

    certContainer.appendChild(certTitle);
    certContainer.appendChild(certText);

    // Append all to userAchievement
    userAchievement.appendChild(friendRequest);
    userAchievement.appendChild(scoreContainer);
    userAchievement.appendChild(certContainer);

    // Append everything to ranking
    ranking.appendChild(rankedUserName);
    ranking.appendChild(userAchievement);

    return ranking;
}