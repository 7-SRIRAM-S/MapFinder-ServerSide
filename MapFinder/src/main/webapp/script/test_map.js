// Initialize Variables 

let states = [];
let shownStates = [];
let weaker_states = [];
let remainingStates = [];
let findState = '';
let selectedState = '';
let timer, time = 0, timeLeft;
let score = 0;
let allowClick = false;
let isPaused = false;
let hintsCount=0;
let startBtn=document.querySelector("#startBtn");
let hintBtn=document.querySelector("#hintBtn");

if(startBtn){
	startBtn.addEventListener("click",()=>{
	startBtn.style.display="none";
	localStorage.removeItem("attemptId");
	storeAttempt();
	start_game();
	make_color();
})
}



// Get State Names 

document.addEventListener("DOMContentLoaded", () => {
	localStorage.removeItem("attemptId");
	initGame();
    let polygons = document.querySelectorAll('polygon[data-info]');
    states = Array.from(polygons).map(p => p.getAttribute('data-info'));
    polygons.forEach(poly => {
        // let name = poly.dataset.info;
        poly.addEventListener('click', () => {
            if (allowClick) handleClick(poly);
        });
    });
});

// Show Clicked State

function show_state(state){
    console.log(`Clicked state : ${state.getAttribute('data-info')}`);
}

//  Speak State Name

function speak(state) {
    if ('speechSynthesis' in window) {
        let utter = new SpeechSynthesisUtterance(state);
        utter.lang = 'en-US';
        window.speechSynthesis.speak(utter);
    }
}

//  Start Game 

function start_game() {
	
	document.getElementById("hintBtn").style.display = "block";
    time=Number(document.querySelector("#timeRange").value);
    if(time==0){
        MODAL.show("Choose Valid Time","Please select valid time to play");
        time=Number(document.querySelector("#timeRange").value);  
        return;
    }
    if(hintBtn&&hintsCount>0) {
		if(hintsCount<10){
			hintBtn.innerText=`Hint ${hintsCount}`;
		}
		else{
			hintBtn.innerText=`Hints ${hintsCount}`;
		}
	}
    
    document.querySelector("#timeRange").style.display="none";
    document.querySelector("#timerDisplay").style.display="flex";

    if (shownStates.length == states.length) {
		endGame();
        localStorage.setItem("weakerstates", JSON.stringify(weaker_states));
        CELEBRATION.show(localStorage.getItem("username"),"Nice Game Completed");
        document.getElementById("stateDisplay").innerText = " All states shown!";
        document.getElementById("timerDisplay").innerText = "";
        return;
    }

    
    // console.log(time);
    
   
        remainingStates = states.filter(s => !shownStates.includes(s));
        let randomIndex = Math.floor(Math.random() * remainingStates.length);
        findState = remainingStates[randomIndex];

        document.getElementById("stateDisplay").innerText = findState;
        document.getElementById("stateDisplay").style.fontWeight = 'bold';
//        speak(findState);
        // console.log("Length :"+remainingStates.length);
        allowClick = true;
        startTimer(time);
    
}

//  Handle Click

function handleClick(poly) {
    allowClick = false;
    clearInterval(timer);

    selectedState = poly.dataset.info;
    if (selectedState.toLowerCase().trim() == findState.toLowerCase().trim()) {
        poly.setAttribute('fill', 'green');
        score += 1 ;
        updateAttempt(); 
    } else {
		storeWeakerState(findState,selectedState);
//        weaker_states.push(findState);
        let correctElement = document.querySelector(`[data-info="${findState}"]`);
        if (correctElement) correctElement.setAttribute('fill', 'red');
        poly.style.stroke = 'red';
        poly.style.strokeWidth = '3px';

        setTimeout(() => {
            poly.style.stroke = '';     
            poly.style.strokeWidth = ''; 
        }, 1000);
    }
//    if (score > 0 && score % 5 == 0 && remainingStates.length!=1) {
//        document.getElementById("hintBtn").style.display = "block";
//    } else {
//        document.getElementById("hintBtn").style.display = "none";
//    }

		

    shownStates.push(findState);
    document.getElementById("score").innerText = `Score: ${score}`;
	
	updateBackend();
	
	
    setTimeout(start_game, 1500);
    
    
    
  
}

// Time Up 

function timeUp() {
    allowClick = false;
    let correctElement = document.querySelector(`[data-info="${findState}"]`);
    if (correctElement) correctElement.setAttribute('fill', 'red');
//    weaker_states.push(findState);
	storeWeakerState(findState,selectedState);
    shownStates.push(findState);

    setTimeout(start_game, 1500);
}

//  Timer 

function startTimer(seconds) {
	if(hintsCount<=0){
		document.getElementById("hintBtn").style.display = "none";
	}
    timeLeft = seconds;
    isPaused = false;
    document.getElementById("pauseBtn").innerText = "Pause";
    document.getElementById("timerDisplay").innerHTML = `Time: <span>${timeLeft}</span>s`;

    let stateBox = document.getElementById("stateDisplay");
    stateBox.style.border = "2px solid green";

    clearInterval(timer);
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

//  Pause Toggle 

function togglePause() {
    isPaused = !isPaused;
    document.getElementById("pauseBtn").innerText = isPaused ? "Resume" : "Pause";
}

//  Reset Game 

function reset_game() {
	endGame()
    clearInterval(timer);
    allowClick = false;
    shownStates = [];
    weaker_states = [];
    score = 0;
    findState = '';
    selectedState = '';
    make_color();

    document.getElementById("stateDisplay").innerText = "find State";
    document.getElementById("timerDisplay").innerText = "";
    document.getElementById("score").innerText = "Score: 0";
    document.getElementById("pauseBtn").innerText = "Pause";
    window.location.reload();
    return;
}

// Reset polygon colors

function make_color() {
    document.querySelectorAll('polygon[data-info]').forEach(p => p.setAttribute('fill', 'lightgray'));
}

//  Show hint (blink) 

function showHint() {
	console.log(hintsCount);
	if(hintsCount<=0){
		document.getElementById("hintBtn").style.display = "none";
		return;
	}
    let selectedState = document.querySelector(`[data-info="${findState}"]`);
    let blinkCount = 0;
    let maxBlinks = 3;
//    weaker_states.push(selectedState)
    let blinkInterval = setInterval(() => {
        selectedState.setAttribute('fill', blinkCount % 2 == 0 ? 'yellow' : 'lightgray');
        blinkCount++;
        if (blinkCount > maxBlinks) clearInterval(blinkInterval);
    }, 300);
    selectedState.setAttribute('fill','lightgray');
    hintsCount--;
    
    	console.log("after used"+hintsCount);
}


function nextPage(state){
      //console.log(state);
      let a=confirm("Are you sure you want to switch games ? \nYour current progress will be lost.");
      if(a){
        window.location.href=state;
      }
      else{
        togglePause();
        // window.location.reload();
      }
}

// Init Game to Get Hints

function initGame(){
	fetch("/MapFinder/gamemode?mode=learn-mode").then((res)=>res.json())
	.then((data)=>{
		hintsCount=Number(data.hintsCount);
	})
	.catch((err)=>{
		console.log(err);
	})
}

function storeAttempt(){
	
	if(localStorage.getItem("attemptId")==undefined||
		localStorage.getItem("attemptId")==null){

		fetch("/MapFinder/attempt?mode=learn-mode").then((res)=>res.json())
		.then((data)=>{
			console.log(data);
			let attemptId = Number(data.data.attemptId);
	        if(!isNaN(attemptId) && attemptId > 0) {
	            localStorage.setItem("attemptId", attemptId);
	            console.log("Attempt ID stored:", attemptId);
	        }else{
	             console.error("Invalid attemptId:", data);
	       	}
		})
		.catch((err)=>{
			console.log(err);
		})
	
	}
	else{
		console.log("already attempt stored");
	}
}





function updateHint(correctState,wrongState){
	let userName=localStorage.getItem("username");
	if(!userName){
		userName="Boss";
		return;
	}
	let attemptId=localStorage.getItem("attemptId");
	if(attemptId==null|| isNaN(attemptId) || attemptId < 0) {
		return;
	}
	 
	data={correctState,wrongState,userName,attemptId};
	fetch("/MapFinder/errorstates",{
		method:"POST",
		headers:{
			"Content-Type":"application/json"
		},
		body:JSON.stringify(data)
	}).then((res)=>res.json())
	.then((data)=>{
		console.log(data);
	})
	.catch((err)=>{
		console.log(err);
	})
}

function updateAttempt(){
	let percentage = timeLeft / time;
	let userScore = Math.round(percentage * 20);
	let attemptId=localStorage.getItem("attemptId");
	if(attemptId==null||isNaN(attemptId) || attemptId < 0) {
		return;
	}
	data={attemptId,userScore};
	fetch("/MapFinder/attempt",{
		method:"POST",
		headers:{
			"Content-Type":"application/json"
		},
		body:JSON.stringify(data)
	}).then((res)=>res.json())
	.then((data)=>{
		console.log(data);
	})
	.catch((err)=>{
		console.log(err);
	})
}

function updateBackend(){
	let userName=localStorage.getItem("username");
		if(!userName){
			userName="Boss";
			return;
		}
		let attemptId=localStorage.getItem("attemptId");
			if(attemptId==null||isNaN(attemptId) || attemptId < 0) {
				return;
			}
		let isGameFinished=false;
		data={attemptId, hintsCount ,userName, isGameFinished};
		
		
	    
		fetch("/MapFinder/gamemode",{
			method:"POST",
			headers:{
				"Content-Type":"application/json"
			},
			body:JSON.stringify(data)
		}).then((res)=>res.json())
		.then((data)=>{
			console.log(data);
		})
		.catch((err)=>{
			console.log(err);
		});
}	
	function endGame(){
		let userName=localStorage.getItem("username");
		if(!userName){
			userName="Boss";
			return;
		}
		let attemptId=localStorage.getItem("attemptId");
			if(isNaN(attemptId) || attemptId < 0) {
				return;
			}
		let isGameFinished=true;
		data={attemptId, hintsCount ,userName,isGameFinished};
		
		
	    
		fetch("/MapFinder/gamemode",{
			method:"POST",
			headers:{
				"Content-Type":"application/json"
			},
			body:JSON.stringify(data)
		}).then((res)=>res.json())
		.then((data)=>{
			console.log(data);
		})
		.catch((err)=>{
			console.log(err);
		})
		
		localStorage.removeItem("attemptId");

		
	}
	
	window.addEventListener("beforeunload", endGame);
	function storeWeakerState(correctState,wrongState){
		updateHint(correctState,wrongState);
	}
	
	window.addEventListener("popstate", (event) => {
	  	endGame();
	  	
	});

	
	
