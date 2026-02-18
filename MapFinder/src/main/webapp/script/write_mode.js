// Initialize Variables

let clickX, clickY,currentState,states=[];


// Get State Names

document.addEventListener("DOMContentLoaded", () => {
  let polygons = document.querySelectorAll('polygon[data-info]');
  states = Array.from(polygons).map(p => p.getAttribute('data-info'));
});


// Show State Name InputBox

function get_input(e){
    if (currentState.dataset.answered == "true"){ 
        alert('Already Answered');
        return;
    } 

    let svg = document.querySelector('.map'); 
    let rect = svg.getBoundingClientRect();

    
    clickX = e.clientX - rect.left;
    clickY = e.clientY - rect.top;

    let box = document.querySelector('.input-wrap');
    box.style.left = (clickX + rect.left) + 'px'; 
    box.style.top = (clickY + rect.top) + 'px';
    box.style.display = 'flex';
    document.querySelector("input").focus()
    
    document.querySelector("input").onkeydown = function(ev){
        if(ev.key == 'Enter'){
            give_input();
        }
    };
}

// Show Current State

function show_state(state) {
    currentState = state;
  }
  
// Get Input 

function give_input() {

    if (currentState.dataset.answered == "true"){
        console.log("Already exist");
        return;
    } 

    let correctName = currentState.dataset.info.toLowerCase().replace(/\s+/g, '');
    let userInput = document.querySelector("input").value.toLowerCase().replace(/\s+/g, '');
    console.log(userInput,correctName);
    if (userInput == correctName) {
      currentState.setAttribute("fill", "green");
    } else {
      alert(`Correct Answer is ${correctName}`);
      currentState.setAttribute("fill", "red");
    }

    currentState.dataset.answered = "true";
    document.querySelector("input").value='';
    document.querySelector(".input-wrap").style.display = "none";
  
  }


// Show State Names in Page

  document.addEventListener("DOMContentLoaded", () => {
    let list = document.getElementById("stateList");
    let sidelist=document.getElementById("aside");
    let count=1;
    states.forEach(state => {
      let li = document.createElement("li");
      li.textContent = `${count}. ${state}`;
      count++;
      if(count>20){
        sidelist.appendChild(li);
      }
      else{
        list.appendChild(li);
      }
    });
  });


   function hidestateList() {
    let btn = document.getElementById("hide-btn");
    let list = document.getElementById("stateList");
    let sidelist = document.getElementById("aside");
    console.log(sidelist);

    if (btn.innerText == "Hide") {
      list.style.visibility = "hidden";
      sidelist.style.visibility = "hidden";
      btn.innerText = "Show";
    } else {
      list.style.visibility = "visible";
      sidelist.style.visibility = "visible";
      btn.innerText = "Hide";
    }

  }
