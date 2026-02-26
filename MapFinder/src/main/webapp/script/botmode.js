// Initialize Variables

let states = [];
let shownStates = [];
let weaker_states = [];
let findState = '';
let selectedState = '';
let timer, time , timeLeft;
let score = 0;
let botScore = 0;
let allowClick = false;
let isPaused = false;


// Get States Values

document.addEventListener("DOMContentLoaded", () => {

    let polygons = document.querySelectorAll('polygon[data-info]');
    states = Array.from(polygons).map(p => p.getAttribute('data-info'));

    // Ensure Single Click

    polygons.forEach(poly => {
        // let name = poly.dataset.info;
        // console.log(name);
        poly.addEventListener('click', () => {
            if (allowClick) {
                handleClick(poly);
            }
        });
    });
});

// Say StateNames

function speak(text) {
    if ('speechSynthesis' in window) {
        let utter = new SpeechSynthesisUtterance(text);
        utter.lang = 'en-US';
        window.speechSynthesis.speak(utter);
    }
}

// Start the game

function start_game() {

    if (shownStates.length == states.length) {
        localStorage.setItem("weakerstates", JSON.stringify(weaker_states));
        document.getElementById("stateDisplay").innerText = "All states shown!";
        document.getElementById("timerDisplay").innerText = "";
        return;
    }

    if (shownStates.length == 0) {
        let selected = document.getElementById('difficultySelect').value;
        if(selected==0){
			MODAL.show("Select Any Option","Plase select difficulty level to play");
//            alert("Select Difficulty level to Play");
            return;
        }
            if (selected == 'easy') {
                time = 15;
            }
            else if(selected == 'medium') {
                time = 10;
            }
            else if (selected == 'hard') {
                time = 5;
            }
        }
     
    let remainingStates = states.filter(s => !shownStates.includes(s));
    let randomIndex = Math.floor(Math.random() * remainingStates.length);
    findState = remainingStates[randomIndex];

    document.getElementById("stateDisplay").innerText = findState;
    document.getElementById("stateDisplay").style.fontWeight = 'bold';
    speak(findState);

    document.querySelector("#difficultySelect").style.display="none";
    document.querySelector("#timerDisplay").style.display="flex";
    
    allowClick = true;
    startTimer(time);
}


// User click handler

function handleClick(poly) {
    
    if (!allowClick) return;

    allowClick = false;
    clearInterval(timer);

    selectedState = poly.dataset.info;

    if (selectedState.toLowerCase().trim() == findState.toLowerCase().trim()) {
        poly.setAttribute('fill', 'green');
        score += 1;
    } else {
        let correctElement = document.querySelector(`[data-info="${findState}"]`);
        if (correctElement) correctElement.setAttribute('fill', 'red');
        weaker_states.push(findState);
        botScore += 1; 
    }

    updateScores();
    shownStates.push(findState);

    setTimeout(start_game, 1500);
}

// Timer
function startTimer(seconds) {
    
    timeLeft = seconds;
    isPaused = false;
    document.getElementById("pauseBtn").innerText = "Pause";
    document.getElementById("timerDisplay").innerHTML = `Time: <span>${timeLeft}</span>`;

    let stateBox = document.getElementById("stateDisplay");
    stateBox.style.border = "2px solid green";

    clearInterval(timer);

    timer = setInterval(() => {
        if (!isPaused) {
            timeLeft--;
            document.getElementById("timerDisplay").innerHTML = `Time: <span>${timeLeft}</span>`;
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

// If user ran out of time & bot answers


function timeUp() {
    allowClick = false;
    let correctElement = document.querySelector(`[data-info="${findState}"]`);
    if (correctElement) correctElement.setAttribute('fill', 'blue'); // blue shows bot answered
    weaker_states.push(findState);
    botScore += 1; 
    shownStates.push(findState);

    updateScores();
    setTimeout(start_game, 1500);
}

// Update the score display


function updateScores() {
    document.getElementById("myscore").innerText = score;
    document.getElementById("botscore").innerText= botScore;
}

// Pause toggle


function togglePause() {
    isPaused = !isPaused;
    document.getElementById("pauseBtn").innerText = isPaused ? "Resume" : "Pause";
}

// Reset the game


function reset_game() {
    clearInterval(timer);
    allowClick = false;
    shownStates = [];
    weaker_states = [];
    score = 0;
    botScore = 0;
    findState = '';
    selectedState = '';
    make_color();

    document.getElementById("stateDisplay").innerText = "Find State";
    document.getElementById("timerDisplay").innerText = "";
    document.getElementById("myscore").innerText = score;
    document.getElementById("botscore").innerText= botScore;
    document.getElementById("pauseBtn").innerText = "Pause";
    document.querySelector("#difficultySelect").style.display="block";
    document.querySelector("#timerDisplay").style.display="none";
    return;
}

// Reset polygon colors


function make_color() {
    document.querySelectorAll('polygon[data-info]').forEach(p => p.setAttribute('fill', 'lightgray'));
}

// Blink hint


function showHint() {
    let selectedState = document.querySelector(`[data-info="${findState}"]`);
    let blinkCount = 0;
    let maxBlinks = 3;
    weaker_states.push(findState);
    let blinkInterval = setInterval(() => {
        selectedState.setAttribute('fill', blinkCount % 2 == 0 ? 'yellow' : 'lightgray');
        blinkCount++;
        if (blinkCount > maxBlinks) 
        {
            clearInterval(blinkInterval);
            selectedState.setAttribute('fill','lightgray');
        }
    }, 300);
  
}
