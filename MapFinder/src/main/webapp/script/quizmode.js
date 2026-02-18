// Questions Bank 

let questions = [
  {
    q: "What is the capital of Tamil Nadu?",
    options: ["Chennai", "Madurai", "Salem", "Coimbatore"],
    answer: 0
  },
  {
    q: "What is the capital of Maharashtra?",
    options: ["Nagpur", "Mumbai", "Pune", "Thane"],
    answer: 1
  },
  {
    q: "What is the capital of Karnataka?",
    options: ["Mysuru", "Bengaluru", "Mangalore", "Hubli"],
    answer: 1
  },
  {
    q: "What is the capital of Rajasthan?",
    options: ["Jodhpur", "Udaipur", "Jaipur", "Ajmer"],
    answer: 2
  },
  {
    q: "What is the capital of Kerala?",
    options: ["Kochi", "Thiruvananthapuram", "Kozhikode", "Alappuzha"],
    answer: 1
  },
  {
    q: "Who is known as the Missile Man of India?",
    options: ["A.P.J. Abdul Kalam", "Vikram Sarabhai", "C.V. Raman", "Homi Bhabha"],
    answer: 0
  },
  // {
  //   q: "How many states are in India?",
  //   options: ["28", "29", "27", "26"],
  //   answer: 0
  // },
  {
    q: "National animal of India?",
    options: ["Lion", "Elephant", "Tiger", "Peacock"],
    answer: 2
  },
  {
    q: "Which is the largest planet?",
    options: ["Earth", "Mars", "Jupiter", "Saturn"],
    answer: 2
  },
  {
    q: "Who wrote the Indian Constitution?",
    options: ["Mahatma Gandhi", "B.R. Ambedkar", "Jawaharlal Nehru", "Sardar Patel"],
    answer: 1
  },
  // {
  //   q: "Tamil Nadu is famous for?",
  //   options: ["Tea", "Silk sarees", "Spices", "Apples"],
  //   answer: 1
  // },
  // {
  //   q: "Punjab is famous for?",
  //   options: ["Wheat farming", "Tea gardens", "Apples", "Coconut"],
  //   answer: 0
  // },
  // {
  //   q: "Kerala is famous for?",
  //   options: ["Beaches & Backwaters", "Coal mines", "Diamond mines", "Tea gardens"],
  //   answer: 0
  // },
  // {
  //   q: "Assam is famous for?",
  //   options: ["Tea", "Cotton", "Spices", "Jewels"],
  //   answer: 0
  // },
  // {
  //   q: "Rajasthan is famous for?",
  //   options: ["Deserts", "Coffee", "Silk", "Tea"],
  //   answer: 0
  // },
  // {
  //   q: "How many Union Territories are in India?",
  //   options: ["6", "7", "8", "9"],
  //   answer: 2
  // },
  // {
  //   q: "Which UT has India’s President as its head?",
  //   options: ["Delhi", "Chandigarh", "Puducherry", "All UTs"],
  //   answer: 3
  // },
  // {
  //   q: "Which UT is a group of islands?",
  //   options: ["Ladakh", "Daman & Diu", "Lakshadweep", "Delhi"],
  //   answer: 2
  // },
  // {
  //   q: "Which UT has French heritage?",
  //   options: ["Daman and Diu", "Puducherry", "Delhi", "Chandigarh"],
  //   answer: 1
  // },
  // {
  //   q: "Which UT is India’s capital?",
  //   options: ["Delhi", "Ladakh", "Andaman", "Puducherry"],
  //   answer: 0
  // },
  // {
  //   q: "India’s Independence Day?",
  //   options: ["15 August", "26 January", "2 October", "1 May"],
  //   answer: 0
  // },
  // {
  //   q: "National flower of India?",
  //   options: ["Rose", "Lotus", "Lily", "Marigold"],
  //   answer: 1
  // },
  // {
  //   q: "Which ocean is south of India?",
  //   options: ["Arctic", "Atlantic", "Indian", "Pacific"],
  //   answer: 2
  // },
  // {
  //   q: "Currency of India?",
  //   options: ["Dollar", "Euro", "Rupee", "Yen"],
  //   answer: 2
  // },
  {
    q: "Prime Minister of India (2025)?",
    options: ["Narendra Modi", "Rahul Gandhi", "Manmohan Singh", "Amit Shah"],
    answer: 0
  }
];



// Initialize Variables


let score = 0, current_index = 0, timer;


// Start Quiz

function start_quiz() {

    document.querySelector("#start_btn").style.visibility = "hidden";
    if (current_index >= questions.length) {
        alert(`Quiz Finished...!\nYour Quiz Score is ${score}`);
        window.location.href = "certificate.html";
    }

    document.getElementById("qno").innerHTML = `<span>Q</span> ${current_index + 1} / 10`;
    document.getElementById("scr").innerText = `Score : ${score}`;

    let qtn = questions[current_index];
    document.getElementById("qtn").innerText = qtn.q;

    let options = document.querySelector(".options");
    options.innerHTML = "";
    qtn.options.forEach((ele, i) => {
        let btn = document.createElement("button");
        btn.innerText = ele;
        btn.className = "option";
        btn.onclick = () => checkAnswer(i, btn);
        options.appendChild(btn);
    });

    startTimer();
}

// Start Timer

function startTimer() {
    let time = 10;
    clearInterval(timer);
    document.getElementById("time").textContent = `Time Left: ${time}s`;
    timer = setInterval(() => {
        time--;
        document.getElementById("time").textContent = `Time Left: ${time}s`;
        if (time <= 0) {
            clearInterval(timer);
            revealAnswer(-1);
        }
    }, 1000);
}

// Check Answer Call

function checkAnswer(index, btn) {
    clearInterval(timer);
    revealAnswer(index, btn);
}

// Show Output

function revealAnswer(selectedIndex) {
    let q = questions[current_index];
    let optionBtns = document.querySelectorAll(".option");

    optionBtns.forEach((o, i) => {
        if (i == q.answer){

        o.classList.add("correct");
        }
        else if (i == selectedIndex) { 
          o.classList.add("wrong");
        }
        o.disabled = true;
    });

    if (selectedIndex == q.answer) score++;

    document.getElementById("scr").textContent = `Score : ${score}`;

    setTimeout(() => {
        current_index++;
        start_quiz();
    }, 1500);
}

// --- To give sample for sign up --- 

// localStorage.clear();
