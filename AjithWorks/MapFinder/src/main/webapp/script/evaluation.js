// Initialize Values


let states = localStorage.getItem("selected_states").split(",");
let shownStates = [];
let weaker_states = [];
let findState = '';
let selectedState = '';
let timer, time , timeLeft;
let score = 0;
let allowClick = false;
let isPaused = false;


// Get State Name when clicked


document.addEventListener("DOMContentLoaded", () => {
    let polygons = document.querySelectorAll('polygon[data-info]');
    polygons.forEach(poly => {
        // let name = poly.dataset.info;
        poly.addEventListener('click', () => {
            if (allowClick) handleClick(poly);
        });
    });
});


// Show State

function show_state(state){
    console.log(`Clicked state : ${state.getAttribute('data-info')}`);
}

//  Speak State

function speak(state) {
    if ('speechSynthesis' in window) {
        let utter = new SpeechSynthesisUtterance(state);
        utter.lang = 'en-US';
        window.speechSynthesis.speak(utter);
    }
}

// Start Game

function start_game() {
    time=Number(document.querySelector("#timeRange").value);
    if(time<=0){
        alert("Please select valid time to play");
        time=Number(document.querySelector("#timeRange").value);  
        return;
    }
    document.querySelector("#timeRange").style.display="none";
    document.querySelector("#timerDisplay").style.display="flex";
    if (shownStates.length == states.length) {
        localStorage.setItem("weakerstates", JSON.stringify(weaker_states));
        document.getElementById("stateDisplay").innerText = "All states shown!";
        document.getElementById("timerDisplay").innerText = "";
        return;
    }

    
    console.log(time);
    
    
        let remainingStates = states.filter(s => !shownStates.includes(s));
        let randomIndex = Math.floor(Math.random() * remainingStates.length);
        findState = remainingStates[randomIndex];

        document.getElementById("stateDisplay").innerText = findState;
        document.getElementById("stateDisplay").style.fontWeight = 'bold';
        speak(findState);

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
        score += 1;
    } else {
        weaker_states.push(findState);
        let correctElement = document.querySelector(`[data-info="${findState}"]`);
        if (correctElement) correctElement.setAttribute('fill', 'red');
    }

    if (score > 0 && score % 3 == 0) {
        document.getElementById("hintBtn").style.display = "block";
    } else {
        document.getElementById("hintBtn").style.display = "none";
    }

    shownStates.push(findState);
    document.getElementById("score").innerText = `Score: ${score}`;

    setTimeout(start_game, 1500); // next question
}

// Time Up 
function timeUp() {
    allowClick = false;
    let correctElement = document.querySelector(`[data-info="${findState}"]`);
    if (correctElement) correctElement.setAttribute('fill', 'red');
    weaker_states.push(findState);
    shownStates.push(findState);

    setTimeout(start_game, 1500);
}

//  Timer 
function startTimer(seconds) {
    timeLeft = seconds;
    isPaused = false;
    document.getElementById("pauseBtn").innerText = "Pause";
    document.getElementById("timerDisplay").innerHTML = `Time: <span>${timeLeft}</span>s`;

    clearInterval(timer);
    timer = setInterval(() => {
        if (!isPaused) {
            timeLeft--;
            document.getElementById("timerDisplay").innerHTML = `Time: <span>${timeLeft}</span>s`;
            if (timeLeft <= 0) {
                clearInterval(timer);
                timeUp();
            }
        }
    }, 1000);
}

//  Pause Action
function togglePause() {
    isPaused = !isPaused;
    document.getElementById("pauseBtn").innerText = isPaused ? "Resume" : "Pause";
}

// Reset Game 
function reset_game() {

    clearInterval(timer);
    allowClick = false;
    shownStates = [];
    weaker_states = [];
    score = 0;
    findState = '';
    selectedState = '';
    make_color();

    document.getElementById("stateDisplay").innerText = "";
    document.getElementById("timerDisplay").innerText = "";
    document.getElementById("score").innerText = "Score: 0";
    document.getElementById("pauseBtn").innerText = "Pause";
    window.location.reload();
}

// Reset polygon colors 

function make_color() {
    document.querySelectorAll('polygon[data-info]').forEach(p => p.setAttribute('fill', 'lightgray'));
}

//  Show hint (blink) 
function showHint() {
    let selectedState = document.querySelector(`[data-info="${findState}"]`);
    let blinkCount = 0;
    let maxBlinks = 3;
    weaker_states.push(selectedState)
    let blinkInterval = setInterval(() => {
        selectedState.setAttribute('fill', blinkCount % 2 == 0 ? 'yellow' : 'lightgray');
        blinkCount++;
        if (blinkCount > maxBlinks) clearInterval(blinkInterval);
    }, 300);
    selectedState.setAttribute('fill','lightgray')
}

//  Speak current state to user
function speakState(current_State) {
    speak(current_State);
}

function nextPage(state){
      console.log(state);
      let a=confirm("Are you sure you want to switch games ? \nYour current progress will be lost");
      if(a){
        window.location.href=state;
      }
      else{
        togglePause();
      }
    }

