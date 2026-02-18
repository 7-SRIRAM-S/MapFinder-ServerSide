//  Initialize Variables

let clickX, clickY, currentState, states = [];

const stateCapitals = {
  "Tamil Nadu": "Chennai",
  "Kerala": "Thiruvananthapuram",
  "Karnataka": "Bengaluru",
  "Andhra Pradesh": "Amaravati",
  "Telangana": "Hyderabad",
  "Odisha": "Bhubaneswar",
  "Chhattisgarh": "Raipur",
  "Maharashtra": "Mumbai",
  "Gujarat": "Gandhinagar",
  "Rajasthan": "Jaipur",
  "Madhya Pradesh": "Bhopal",
  "Jharkhand": "Ranchi",
  "West Bengal": "Kolkata",
  "Bihar": "Patna",
  "Uttar Pradesh": "Lucknow",
  "Haryana": "Chandigarh",
  "Uttarakhand": "Dehradun",
  "Punjab": "Chandigarh",
  "Himachal Pradesh": "Shimla",
  "Jammu and Kashmir": "Srinagar",
  "Ladakh": "Leh",
  "Goa": "Panaji",
  "Sikkim": "Gangtok",
  "Meghalaya": "Shillong",
  "Assam": "Dispur",
  "Tripura": "Agartala",
  "Mizoram": "Aizawl",
  "Manipur": "Imphal",
  "Nagaland": "Kohima",
  "Arunachal Pradesh": "Itanagar",
  "Delhi": "New Delhi",
  "Dadra & Nagar Haveli and Daman & Diu": "Daman",
  "Puducherry": "Puducherry",
  "Karaikal (Puducherry)": "Karaikal",
  "Mahe (Puducherry)": "Mahe",
  "Yaman (Puducherry)": "Yanam"
};


// Show Capital Names in Tooltip <div>

function get_input(e) {

  if (currentState.dataset.answered == "true") {
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
  document.querySelector("input").focus();

  document.querySelector("input").onkeydown = function (ev) {
    if (ev.key == 'Enter') {
      give_input();
    }
  };
}

// Show States

function show_state(state) {
  currentState = state;
}

// Get Input

function give_input() {

  if (currentState.dataset.answered == "true") {
    console.log("Already exist");
    return;
  }

  let stateName = currentState.dataset.info;
  let correctCapital = stateCapitals[stateName]?.toLowerCase().replace(/\s+/g, '');
  let userInput = document.querySelector("input").value.toLowerCase().replace(/\s+/g, '');

  if (userInput == correctCapital) {
    currentState.setAttribute("fill", "green");
  } else {
    alert(`Correct Answer is ${correctCapital}`);
    currentState.setAttribute("fill", "red");
  }

  currentState.dataset.answered = "true";
  document.querySelector("input").value = '';
  document.querySelector(".input-wrap").style.display = "none";
}


// Collect Capital values and fill in page

document.addEventListener("DOMContentLoaded", () => {
    let polygons = document.querySelectorAll('polygon[data-info]');
    states = Array.from(polygons)
      .map(p => p.getAttribute('data-info'))
      .filter(s => stateCapitals[s]); 
  
    let capitals = states.map(s => stateCapitals[s]); 
  
    let list = document.getElementById("stateList");
    let sidelist = document.getElementById("aside");
    let count = 1;
  
    capitals.forEach(capital => {
      let li = document.createElement("li");
      li.textContent = `${count}. ${capital}`;
      count++;
      if (count > 17) sidelist.appendChild(li);
      else list.appendChild(li);
    });
  });

  function hidestateList() {
    let aside=document.querySelector(".map");
    let btn = document.getElementById("hide-btn");
    let list = document.getElementById("stateList");
    let sidelist = document.getElementById("aside");
    console.log(sidelist);

    if (btn.innerText == "Hide") {
      list.style.visibility = "hidden";
      sidelist.style.visibility = "hidden";
      btn.innerText = "Show";
      aside.style.borderColor="white";
    } else {
      list.style.visibility = "visible";
      sidelist.style.visibility = "visible";
      btn.innerText = "Hide";
      aside.style.borderColor="green";
    }

  }
