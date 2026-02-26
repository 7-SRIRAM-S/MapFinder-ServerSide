var userName="";

const input = document.getElementById('input');
const clearBtn = document.getElementById('clear-btn');

let showingFriendList=false;

input.addEventListener('input', () => {
  clearBtn.style.display = input.value ? 'block' : 'none';
});

clearBtn.addEventListener('click', () => {
  input.value = '';
  clearBtn.style.display = 'none';
  input.focus();
  LeaderBoard.init();
  LeaderBoard.show.TopThreePlayers();
});

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
			userName=localStorage.getItem("username");
            $("#back").on("click", () => {
                window.location.assign("/MapFinder/home.html");
            });

            $("#global, #friend").click(function () {
			    $("#global, #friend").removeClass("active-btn");
				
			    $(this).addClass("active-btn");
			});


            $("#input").on("input", () => {
                LeaderBoard.show.search();
            })
            
            			
			$("#friend").on("click" , ()=>{
				
				showingFriendList=true;
				LeaderBoard.show.friend();
			})

			$("#global").on("click" , ()=>{
				showingFriendList=false;
				LeaderBoard.show.view();

			})
			$("#global").addClass("active-btn");
			


            LeaderBoard.show.view();
        },
        show:{
            view: async function(){

		    data = await LeaderBoard.get.view();
		
		    if (!data || data.length === 0) {
		        $(".ranking-container").html("<div id='no-data'>No Friends Found</div>");
		        return;
		    }
		
			data=data.data
			
		    for (let i = 0; i < Math.min(3, data.length); i++) {
		        $(`.user-name-${i + 1}`).text(data[i].userName || "");
		        $(`.user-points-${i + 1}`).text(data[i].totalScore || 0);
		    }
		
		    const container = $(".ranking-container");
		    container.empty();
		    for (let i = 0; i < data.length; i++) {
			
		        const element = buildHtml(
		            i + 1,
		            data[i].userName,
		            data[i].totalScore,
		            data[i].totalCertificate,
		            data[i].isFriend,
		            data[i].isAlreadyRequested
		        );
		
		        container.append(element);
		    }
		},
            
            friend: async function(){

				const container = $(".ranking-container");
				container.empty();
				let found =false
				
				for (let i = 0; i < data.length; i++) {
					if(data[i].isFriend){

						const element = buildHtml(
						    i + 1,
						    data[i].userName,
						    data[i].totalScore,
						    data[i].totalCertificate,
						    data[i].isFriend
						);
						found= true;

						container.append(element);
					}
				}
				
				if(!found){
				container.append("<div id='no-data'>No Friends Found</div>");
					
				}
			},

            
            
            
            
            search: async function() {
                let value = $("#input").val().toLowerCase().trim();

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
				            i+1,
				            data[i].userName,
				            data[i].totalScore,
				            data[i].totalCertificate,
				            data[i].isFriend,
				            data[i].isAlreadyRequested
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


function giveFriendRequest(friendName){
	console.log("friend request to "+friendName+" from "+userName);
	data={userName,friendName};
	fetch("/MapFinder/friendrequest/",{
		method:"POST",
		headers:{
           	'Content-Type':'application/json'
        },
		body:JSON.stringify(data)
	}).then((res)=>res.json())
	.then((response)=>{
		console.log(response);
		if(response.status==="failed"){
			console.log(response.message);
			alert(response.message);
		}
		else{
			MODAL.show("Friend Requested","friend requested successfully");
			LeaderBoard.show.view();
			console.log(response.status,response.message)
		}
	})
	
	
}

function buildHtml(count, name, points, certificate, isFriend, isAlreadyRequested) {

	
    const ranking = document.createElement("div");
    ranking.className = "ranking";

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

    const userAchievement = document.createElement("div");
    userAchievement.className = "user-achivement";


	if(showingFriendList){
		

		const challengeBtn=document.createElement("button");
		challengeBtn.innerText="Challenge";
		challengeBtn.className="challenge-button";
		challengeBtn.id="challengeBtn";
		challengeBtn.addEventListener("click",()=>{
			challengeBtn.disabled=true;
			giveChallenge(name,challengeBtn);
		})
		
		userAchievement.appendChild(challengeBtn);
	}
    
        if(userName!=name) {
    
		    const friendRequest = document.createElement("div");
		    friendRequest.className = "friend-request";
		
		    
		    
		
			
			const addUserImg = document.createElement("img");
			if(isFriend){
				addUserImg.src = "./icons/friends.png";
				addUserImg.title = "Friend";
							        addUserImg.alt = "friend";

			}
			else if(isAlreadyRequested){
				addUserImg.src = "./icons/friend-request.png";
				addUserImg.title = "Friend Requested";
							        addUserImg.alt = "friend";

			}
			else {
			        addUserImg.src = "./images/add-user.png";
			        addUserImg.alt = "friend";
			        addUserImg.title = "Make Friend";
			        addUserImg.onclick=()=>{
						giveFriendRequest(name);
					}
			}
			
		
		
		    friendRequest.appendChild(addUserImg);
		    
		    userAchievement.appendChild(friendRequest);
    
    }
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

    
    userAchievement.appendChild(scoreContainer);
    userAchievement.appendChild(certContainer);

    ranking.appendChild(rankedUserName);
    ranking.appendChild(userAchievement);
    
    if(name==userName){
		ranking.style.backgroundColor="yellow";
	}

    return ranking;
}





function giveChallenge(name,challengeBtn){
	console.log(challengeBtn);
//	const challengeBtn=document.querySelector("#challengeBtn");
//	console.log(challengeBtn);
if(challengeBtn){
		challengeBtn.innerText="Challenging...";
}
data={challengerName:userName,opponentName:name};
	fetch("/MapFinder/challenge",{
		method:"POST",
		headers:{
			"Content-Type":"application/json"
		},
		body: JSON.stringify(data)
	}).then((res)=>res.json())
	.then((msg)=>{
		console.log(msg.message);
		if(msg.message&&msg.message=="active"){
			challengeBtn.innerText="Challenging...";
		}
		else{
			challengeBtn.innerText="Challenge Sent";
		}
	})

}






