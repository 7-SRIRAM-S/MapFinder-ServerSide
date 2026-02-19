// Inititalize Variables

let states=JSON.parse(localStorage.getItem("weakerstates"));
let shownStates = [];
let weaker_states=[];
let findState = '';
let selectedState = '';
let timer,time;
let isPaused = false;
let score=0;

// Start Game

function start_game() {

    time=Number(document.querySelector("#timeRange").value);
    if(time==0){
        alert("Please select valid time to play");
        time=Number(document.querySelector("#timeRange").ariaValueNow);  
        return;
    }

    document.querySelector("#timeRange").style.display="none";
    document.querySelector("#timerDisplay").style.display="flex";
    if (shownStates.length == states.length) {
        localStorage.setItem("weakerstates",JSON.stringify(weaker_states));
        document.getElementById("stateDisplay").innerText = "All states shown!";
        document.getElementById("timerDisplay").innerText = "";
        return;
    }

   
    
    // console.log(time);
    
   
        let remainingStates = states.filter(s => !shownStates.includes(s));

        let randomIndex = Math.floor(Math.random() * remainingStates.length);
        findState = remainingStates[randomIndex];

        shownStates.push(findState);

        document.getElementById("stateDisplay").innerText = findState;
        document.getElementById("stateDisplay").style.fontWeight = 'bold'; 
        speakState(findState);

        startTimer(time);
    
}

// Show blink hints 

function showHint() {

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


// Check if user has Weaker States and handle it 

document.addEventListener("DOMContentLoaded", () => {
    let list = document.getElementById("stateList");
    let count=1;
    if(states==null||states.length==0){
        alert("You have now mastered the course. Good to go and try other modes");
        window.location.href = "index.html";
        return;
    }

    // Show Weaker States in Page

    states.forEach(state => {
      let li = document.createElement("li");
      if(state.length!=undefined){
        li.textContent = `${count}. ${state}`;
        count++;
        list.appendChild(li);
      }
    });
  });
console.log();


// --- To give sample for sign up --- 

// localStorage.clear();