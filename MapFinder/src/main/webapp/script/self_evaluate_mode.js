// Initialize variables

let states = [];
let shownStates = [];   
let weaker_states = [];
let findState = '';
let selectedState = '';
let timer, time, timeLeft;
let isPaused = false;
let score = 0;


// Target Elements 

let tooltip    = document.getElementById('tooltip');
let stateList  = document.getElementById('stateList');
let playBtn    = document.getElementById('playBtn');
let clearBtn   = document.getElementById('clearBtn');

//  Speak States 
function speak(state) {
    if ('speechSynthesis' in window) {
        let utter = new SpeechSynthesisUtterance(state);
        utter.lang = 'en-US';
        // window.speechSynthesis.speak(utter);
    }
}


// Show selected states in page

function showStateList() {

    if(states.length==0){
        document.querySelector("#info").style.display="block";
    }
    else{
        document.querySelector("#info").style.display="none";
    }
    
    stateList.innerHTML = '';
    for (let i = 0; i < states.length; i++) {
        let name = states[i];
        let div = document.createElement('div');
        div.className = 'state-item';
        div.innerHTML = `
            <span>${name}</span>
            <button class="delBtn" data-name="${name}"><i class="fa-solid fa-trash"></i></button>
        `;
        stateList.appendChild(div);
    }

    // Handles Delete Action 

    document.querySelectorAll('.delBtn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            let ele=e.target.parentElement;
            let n = ele.dataset.name;
            // console.log(ele,n);
            if (confirm(`Remove ${n}?`)) {
                states = states.filter(s => s !== n);
                showStateList();
            }
        });
    });
}

//  Hover and Click for polygons 

document.querySelectorAll('polygon').forEach(poly => {

    let name = poly.dataset.info;

    poly.addEventListener('mouseenter', () => speak(name));

    poly.addEventListener('mousemove', e => {
        tooltip.textContent = name;
        tooltip.style.display = 'block';
        tooltip.style.left = (e.pageX + 10) + 'px';
        tooltip.style.top  = (e.pageY + 10) + 'px';
    });

    poly.addEventListener('mouseleave', () => tooltip.style.display = 'none');

    poly.addEventListener('click', () => {
        if (!states.includes(name)) {
            states.push(name);
            showStateList();
        }
    });
});

// Play Button with validation 

playBtn.addEventListener('click', () => {
    if (!states.length){
        alert('Select at least one state!');
        window.location.href="self_evaluate_mode.html";
    } 

    
});

// Clear All 

clearBtn.addEventListener('click', () => {
    if (confirm("Remove all selected states?")) {
        states = [];
        showStateList();
    }
});

// Start Game

function start_game() {
    localStorage.setItem("selected_states",states);
    window.location.href="evaluation.html";

}   


// To prevent console errors

function show_state(){

}

function make_colors(){

}