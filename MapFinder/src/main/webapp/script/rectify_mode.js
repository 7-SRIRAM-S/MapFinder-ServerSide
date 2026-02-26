// Inititalize Variables

let states=JSON.parse(localStorage.getItem("weakerStates"));
let shownStates = [];
let weaker_states=[];
let findState = '';
let selectedState = '';
let timer,time = 0, timeLeft;
let isPaused = false;
let score=0;
let hintsCount=0;
let startBtn=document.querySelector("#startBtn");
let hintBtn=document.querySelector("#hintBtn");

if(startBtn){
	startBtn.addEventListener("click",()=>{
	startBtn.style.display="none";
	start_game();
	make_color();
})
}



// Start Game

function start_game() {
	initGame();
    time=Number(document.querySelector("#timeRange").value);
    if(time==0){
        alert("Please select valid time to play");
        time=Number(document.querySelector("#timeRange").ariaValueNow);  
        return;
    }

    document.querySelector("#timeRange").style.display="none";
    document.querySelector("#timerDisplay").style.display="flex";

    if(hintBtn&&hintsCount>0) {
     if(hintsCount<10){
          hintBtn.innerText=`Hint ${hintsCount}`;
     }
     else{
          hintBtn.innerText=`Hints ${hintsCount}`;
     }
}

    if (shownStates.length == states.length) {
        localStorage.setItem("weakerstates",JSON.stringify(weaker_states));
        endGame();
        document.getElementById("stateDisplay").innerText = "All states shown!";
        document.getElementById("timerDisplay").innerText = "";
        return;
    }

   
    
    // console.log(time);
    
   
        let remainingStates = states.filter(s => !shownStates.includes(s.wrongAnswer));

        let randomIndex = Math.floor(Math.random() * remainingStates.length);
        findState = remainingStates[randomIndex].wrongAnswer;

        shownStates.push(findState);

        document.getElementById("stateDisplay").innerText = findState;
        document.getElementById("stateDisplay").style.fontWeight = 'bold'; 
        speakState(findState);

        startTimer(time);
    
}

// Show blink hints 

function showHint() {
    console.log(hintsCount);
	if(hintsCount<=0){
		document.getElementById("hintBtn").style.display = "none";
		return;
	}
    let polygon = document.querySelector(`[data-info="${findState}"]`);
    let blinkCount = 0;
    let maxBlinks = 3; 

    let blinkInterval = setInterval(() => {
        polygon.setAttribute('fill', (blinkCount % 2 == 0) ? 'yellow' : 'lightgray');
        blinkCount++;
        if (blinkCount > maxBlinks) {
            clearInterval(blinkInterval);
            polygon.setAttribute('fill', 'lightgray'); 
        }
    }, 300); 
    hintsCount--;
    
    	console.log("after used"+hintsCount);
  
}



// Timer Start 

function startTimer(seconds) {

    timeLeft = seconds;
    isPaused = false;
    document.getElementById("pauseBtn").innerText = "Pause";
    document.getElementById("timerDisplay").innerHTML = `Time: <span>${timeLeft}</span>s`;
    document.getElementById("score").innerText=`Score : ${score}`;

    clearInterval(timer);

    let stateBox = document.getElementById("stateDisplay");
    stateBox.style.border = "2px solid green";


    timer = setInterval(() => {
        if (!isPaused) {
            timeLeft--;
            document.getElementById("timerDisplay").innerHTML = `Time: <span>${timeLeft}</span>s`;
            if (timeLeft <= 3) {
                stateBox.style.border = "8px solid red";
            }
            if (timeLeft <= 0) {
                clearInterval(timer);
                timeUp();
            }
        }
    }, 1000);
}

// Pause Toggle 

function togglePause() {
    isPaused = !isPaused;
    document.getElementById("pauseBtn").innerText = isPaused ? "Resume" : "Pause";
}


// Show State

function show_state(state) {

    clearInterval(timer);
    selectedState = state.dataset.info;

    if (selectedState.toLowerCase().trim() == findState.toLowerCase().trim()) {
        state.setAttribute("fill", "green");
        score+=1
    } else {
        let correctElement = document.querySelector(`[data-info="${findState}"]`);
        weaker_states.push(findState);
        if (correctElement) correctElement.setAttribute("fill", "red");
    }
    if(score>0){
    if(score%7==0){
        document.getElementById("hintBtn").style.display="block";
    }
    else{
        document.getElementById("hintBtn").style.display="none";
    }
}
    setTimeout(start_game, 1500);
}

// Time Up

function timeUp() {
    const correctElement = document.querySelector(`[data-info="${findState}"]`);
    if (correctElement) correctElement.setAttribute("fill", "red");
    weaker_states.push(findState);
    setTimeout(start_game, 1500);
}

// Reset Function 

function reset_game() {

    clearInterval(timer);        
    isPaused = false;

    document.getElementById("stateDisplay").innerText = "";
    document.getElementById("timerDisplay").innerText = "";
    document.getElementById("pauseBtn").innerText = "Pause";
    document.getElementById("score").innerText="0";


    findState = '';
    selectedState = '';
    score=0;
    endGame();
    window.location.reload();
    return;
}

// console.log(score);

// Speak State Name 
function speakState(state) {
    if ('speechSynthesis' in window) {
        const utterance = new SpeechSynthesisUtterance(state);
        utterance.lang = 'en-US'; 
        window.speechSynthesis.speak(utterance);
    } else {
        console.log("Speech Synthesis not supported in this browser.");
    }
}

// Color States

function make_color(){
    document.querySelectorAll('polygon[data-info]').forEach(p => p.setAttribute('fill', 'lightgray'));

}


document.querySelector("#download").addEventListener("click",()=>{
	document.querySelector("#download").disabled=true;
	window.location.href = "/MapFinder/errorstates?download=true";
})


// Check if user has Weaker States and handle it 

document.addEventListener("DOMContentLoaded", () => {
 
    if(states==null||states.length==0){
	console.log("working");
        fetch("/MapFinder/errorstates").then((res)=>res.json())
		.then((msg)=>{
			localStorage.setItem("weakerStates",JSON.stringify(msg.data));
			states=JSON.parse(localStorage.getItem("weakerStates"));
			console.log(msg);
			renderStates();
		})
    }
    else {
		renderStates();
	}
    
  });
console.log();

function renderStates(){
	   let list = document.getElementById("stateList");
    let count=1;
	 // Show Weaker States in Page
	    states.forEach(state => {
	      let li = document.createElement("li");
	      if(state.length!=0){
	        li.textContent = `${count}. ${state.wrongAnswer}`;
	        count++;
	        list.appendChild(li);
	      }
	     
	    });
}


function initGame(){
//	fetch("/MapFinder/gamemode?mode=rectify-mode").then((res)=>res.json())
//	.then((data)=>{
//		hintsCount=Number(data.hintsCount);
//		console.log(hintsCount);
//	})
//	.catch((err)=>{
//		console.log(err);
//	})
//	
//	
//	if(localStorage.getItem("attemptId")===undefined||
//		localStorage.getItem("attemptId")===null||
//		localStorage.getItem("attemptId")<=0){
//			console.log(localStorage.getItem("attemptId"));
//			storeAttempt();
//	}
}


function storeAttempt(){
//	fetch("/MapFinder/attempt?mode=rectify-mode").then((res)=>res.json())
//	.then((data)=>{
//		let attemptId = Number(data.data.attemptId);
//        if(!isNaN(attemptId) && attemptId > 0) {
//            localStorage.setItem("attemptId", attemptId);
//            console.log("Attempt ID stored:", attemptId);
//        }else{
//             console.error("Invalid attemptId:", data);
//       	}
//	})
//	.catch((err)=>{
//		console.log(err);
//	})
}







function updateHint(correctState,wrongState){
//	
//	let userName=localStorage.getItem("username");
//	if(!userName){
//		userName="Boss";
//		return;
//	}
//	let attemptId=localStorage.getItem("attemptId");
//	if(isNaN(attemptId) || attemptId < 0) {
//		return;
//	}
//	 
//	data={correctState,wrongState,userName,attemptId};
//	fetch("/MapFinder/errorstates",{
//		method:"POST",
//		headers:{
//			"Content-Type":"application/json"
//		},
//		body:JSON.stringify(data)
//	}).then((res)=>res.json())
//	.then((data)=>{
//		console.log(data);
//	})
//	.catch((err)=>{
//		console.log(err);
//	})
}



function updateAttempt(){
//	let percentage = timeLeft / time;
//	let userScore = Math.round(percentage * 20);
//	let attemptId=localStorage.getItem("attemptId");
//	if(isNaN(attemptId) || attemptId < 0) {
//		return;
//	}
//	data={attemptId,userScore};
//	fetch("/MapFinder/attempt",{
//		method:"POST",
//		headers:{
//			"Content-Type":"application/json"
//		},
//		body:JSON.stringify(data)
//	}).then((res)=>res.json())
//	.then((data)=>{
//		console.log(data);
//	})
//	.catch((err)=>{
//		console.log(err);
//	})
}

function updateBackend(){
//	let userName=localStorage.getItem("username");
//		if(!userName){
//			userName="Boss";
//			return;
//		}
//		let attemptId=localStorage.getItem("attemptId");
//			if(isNaN(attemptId) || attemptId < 0) {
//				return;
//			}
//		let isGameFinished=false;
//		data={attemptId, hintsCount ,userName, isGameFinished};
//		
//		
//	    
//		fetch("/MapFinder/gamemode",{
//			method:"POST",
//			headers:{
//				"Content-Type":"application/json"
//			},
//			body:JSON.stringify(data)
//		}).then((res)=>res.json())
//		.then((data)=>{
//			console.log(data);
//		})
//		.catch((err)=>{
//			console.log(err);
//		});
}	
	function endGame(){
//		let userName=localStorage.getItem("username");
//		if(!userName){
//			userName="Boss";
//			return;
//		}
//		let attemptId=localStorage.getItem("attemptId");
//			if(isNaN(attemptId) || attemptId < 0) {
//				return;
//			}
//		let isGameFinished=true;
//		data={attemptId, hintsCount ,userName,isGameFinished};
//		
//		
//	    
//		fetch("/MapFinder/gamemode",{
//			method:"POST",
//			headers:{
//				"Content-Type":"application/json"
//			},
//			body:JSON.stringify(data)
//		}).then((res)=>res.json())
//		.then((data)=>{
//			console.log(data);
//		})
//		.catch((err)=>{
//			console.log(err);
//		})
//		
//		localStorage.removeItem("attemptId");

		
	}
	
	window.addEventListener("beforeunload", endGame);
	function storeWeakerState(correctState,wrongState){
		updateHint(correctState,wrongState);
	}
	
	window.addEventListener("popstate", (event) => {
	  	endGame();
	  	
	});


// --- To give sample for sign up --- 

// localStorage.clear();