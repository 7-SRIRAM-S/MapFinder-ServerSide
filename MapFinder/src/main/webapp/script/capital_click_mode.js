// Initialize Variables


let states = [];
let shownStates = [];
let weaker_states = [];
let findCapital = '';
let selectedState = '';
let timer, time = 0, timeLeft;
let score = 0;
let allowClick = false;
let isPaused = false;

// State Capital map object 


let stateCapitals = {
    "Andhra Pradesh": "Amaravati",
    "Arunachal Pradesh": "Itanagar",
    "Assam": "Dispur",
    "Bihar": "Patna",
    "Chhattisgarh": "Raipur",
    "Goa": "Panaji",
    "Gujarat": "Gandhinagar",
    "Haryana": "Chandigarh",
    "Himachal Pradesh": "Shimla",
    "Jharkhand": "Ranchi",
    "karnataka": "Bengaluru",
    "Kerala": "Thiruvananthapuram",
    "Madhya Pradesh": "Bhopal",
    "Maharashtra": "Mumbai",
    "Manipur": "Imphal",
    "Meghalaya": "Shillong",
    "Mizoram": "Aizawl",
    "Nagaland": "Kohima",
    "Odisha": "Bhubaneswar",
    "Punjab": "Chandigarh",
    "Rajasthan": "Jaipur",
    "Sikkim": "Gangtok",
    "Tamil Nadu": "Chennai",
    "Telungana": "Hyderabad",
    "Tripura": "Agartala",
    "Uttar Pradesh": "Lucknow",
    "Uttarakhand": "Dehradun",
    "West Bengal": "Kolkata"
};


// Get State Names & Capital Names


document.addEventListener("DOMContentLoaded", () => {
    states = Object.keys(stateCapitals);
    let polygons = document.querySelectorAll('polygon[data-info]');
    polygons.forEach(poly => {
        // let name = poly.dataset.info;
        poly.addEventListener('click', () => {
            if (allowClick) handleClick(poly);
        });
    });
});


// Speak Capital 


function speak(capital) {
    if ('speechSynthesis' in window) {
        let utter = new SpeechSynthesisUtterance(capital);
        utter.lang = 'en-US';
        window.speechSynthesis.speak(utter);
    }
}

// Start Game


function start_game() {


    time = Number(document.querySelector("#timeRange").value);
    if (time <= 0) {
        MODAL.show("Choose Valid Time","Please select valid time to play");
        return;
    }

    document.querySelector("#timeRange").style.display="none";
    document.querySelector("#timerDisplay").style.display="flex";

    if (shownStates.length == states.length) {
        // localStorage.setItem("weakerstates", JSON.stringify(weaker_states));
        document.getElementById("stateDisplay").innerText = "All capitals done!";
        document.getElementById("timerDisplay").innerText = "";
        return;
    }

    

    let remainingStates = states.filter(s => !shownStates.includes(s));
    let randomState = remainingStates[Math.floor(Math.random() * remainingStates.length)];
    findCapital = stateCapitals[randomState];
    console.log(randomState,findCapital);
    document.getElementById("stateDisplay").innerText = findCapital;
    document.getElementById("stateDisplay").style.fontWeight = 'bold';
    speak(findCapital);

    allowClick = true;
    startTimer(time);
}


// Handle Click


function handleClick(poly) {

    allowClick = false;
    clearInterval(timer);

    selectedState = poly.dataset.info;

    if (stateCapitals[selectedState] == findCapital) {
        poly.setAttribute('fill', 'green');
        score += 1;
    } else {
        weaker_states.push(findCapital);
        let correctState = Object.keys(stateCapitals).find(
            state => stateCapitals[state] == findCapital
        );
        let correctElement = document.querySelector(`[data-info="${correctState}"]`);
        if (correctElement) correctElement.setAttribute('fill', 'red');
    }

    if (score > 0 && score % 5 == 0) {
        document.getElementById("hintBtn").style.display = "block";
    } else {
        document.getElementById("hintBtn").style.display = "none";
    }

    shownStates.push(
        Object.keys(stateCapitals).find(s => stateCapitals[s] == findCapital)
    );
    document.getElementById("score").innerText = `Score: ${score}`;

    setTimeout(start_game, 1500);
}


// Time Up


function timeUp() {
    allowClick = false;
    let correctState = Object.keys(stateCapitals).find(
        s => stateCapitals[s] == findCapital
    );
    let correctElement = document.querySelector(`[data-info="${correctState}"]`);
    if (correctElement) correctElement.setAttribute('fill', 'red');
    weaker_states.push(findCapital);
    shownStates.push(correctState);

    setTimeout(start_game, 1500);
}


// Timer


function startTimer(seconds) {
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


// Pause Action


function togglePause() {
    isPaused = !isPaused;
    document.getElementById("pauseBtn").innerText = isPaused ? "Resume" : "Pause";
}


// Reset Action


function reset_game() {
    clearInterval(timer);
    allowClick = false;
    shownStates = [];
    weaker_states = [];
    score = 0;
    findCapital = '';
    selectedState = '';
    make_color();

    document.getElementById("stateDisplay").innerText = "";
    document.getElementById("timerDisplay").innerText = "";
    document.getElementById("score").innerText = "Score: 0";
    document.getElementById("pauseBtn").innerText = "Pause";
    window.location.reload();
    return;
}


// Make Color


function make_color() {
    document.querySelectorAll('polygon[data-info]').forEach(p => p.setAttribute('fill', 'lightgray'));
}


// Show Hint


function showHint() {
    let correctState = Object.keys(stateCapitals).find(s => stateCapitals[s] == findCapital);
    let selectedState = document.querySelector(`[data-info="${correctState}"]`);
    let blinkCount = 0;
    let maxBlinks = 3;
    let blinkInterval = setInterval(() => {
        selectedState.setAttribute('fill', blinkCount % 2 == 0 ? 'yellow' : 'lightgray');
        blinkCount++;
        if (blinkCount > maxBlinks) clearInterval(blinkInterval);
    }, 300);
    selectedState.setAttribute('fill','lightgray')
}


// Next page check

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
