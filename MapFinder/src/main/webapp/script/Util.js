API =(function(){
    return{
            get:async function(url){
                    if(!!url){
                    let response=await fetch(url);

                        if(!response.ok){
                            throw new Error("fetching data is failed");
                        }

                    return{
                        
                        "data":await response.json()
                    }
                    
                    }else{
                        throw new Error("Url not found");
                    }
            }
        }
}) ();

SESSION=(function (){
	return {
		checkSession:async function (){
		        try{
		            let res=await fetch("/MapFinder/checksession");
		            let data=await res.json();
		
		            if(data.status==="failed"){
		                localStorage.clear();
		                console.log("session not exist");
		                return false;
		            }
		
		            localStorage.setItem("username",data.message);
		            return true;
		
		        } catch(err){
		            console.log(err);
		            return false;
		        }
		    }	
	}
})();


WINDOW=(function(){
	return {
		changeUrl:function(uri){
			  window.location.replace(uri);
		},
		
		blockGoBack:function(){
			history.pushState(null, null, location.href);
	    	 window.onpopstate = function () {
	    	        history.go(1);
	    	 };
		}
		
	}
})();


NOTIFICATION=(function(){
    return{
        send:function(head,message){
            checkBrowser();

            const option={
                body:message,
                icon:"./icons/colorized-trophy.png",
                vibrate:[200,100,200]
                
            }

            if (Notification.permission === "granted") {

                new Notification(head,option);

            }else if(Notification.permission==="default"){

                Notification.requestPermission().then(permission=>{

                    if (permission=="granted") {

                        new Notification(head,option);

                    }
                    
                })
            }
        }
    }
}) ();




function checkBrowser() {
    if (!("Notification" in window)) {
        alert("This browser does not support notification");
        return;
    }
}





// ------------------- Notification Dropdown System -----------------------
NOTIFY_PANEL = (function () {

    let panel = null;

    (function injectStyle(){
        if(document.getElementById("notify-panel-style")) return;

        const style = document.createElement("style");
        style.id = "notify-panel-style";

        style.innerHTML = `
            .notify-panel {
                position: absolute;
                width: 420px;
				height: 100%;
                background: #fff;
                box-shadow: 0 8px 20px rgba(0,0,0,0.15);
                border-radius: 6px;
                overflow: hidden;
                z-index: 1000;
                animation: slideDown 0.2s ease;
                font-family: Arial, sans-serif;
            }

            .notify-panel::before {
                content: "";
                position: absolute;
                top: -8px;
                right: 20px;
                border-width: 8px;
                border-style: solid;
                border-color: transparent transparent #fff transparent;
            }

            .notify-item {
                padding: 12px;
                border-bottom: 2px solid #eee;
                font-size: 14px;
                background-color: #F9F9FD;
                display:flex;
                gap:1rem;
            }

            .notify-item:last-child {
                border-bottom: none;
            }

            .notify-actions {
                display: flex;
                gap: 8px;
            }

            .notify-btn {
                padding: 4px 10px;
                font-size: 12px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            .accept-btn {
                background: #28a745;
                color: white;
            }

            .reject-btn {
                background: #dc3545;
                color: white;
            }
            .no-notifications{
				text-align:center;
				padding:1.2rem;
			}
        `;

        document.head.appendChild(style);
    })();

    function closePanel() {
        if (panel) {
            panel.remove();
            panel = null;
            document.removeEventListener("click", outsideClickHandler);
			document.body.style.background = "linear-gradient(to right bottom, #FFF7EE, #F5FEF8)";
        }
    }

    function outsideClickHandler(e) {
        if (panel && !panel.contains(e.target) && !e.target.closest("#notification")) {
            closePanel();
        }
    }

    function buildPanel(notifications) {

        closePanel();

        panel = document.createElement("div");
        panel.className = "notify-panel";
		document.body.style.background = "rgba(0,0,0,0.4)";

        if (!notifications || notifications.length === 0) {
            panel.innerHTML = `<div class="notify-item no-notifications">No Notifications</div>`;
        } else {
            notifications.forEach(item => {
				
                const div = document.createElement("div");
                div.className = "notify-item";

                div.innerHTML = `<div>${item.message}</div>`;

                if (item.type === "friend_request") {

                    const actionDiv = document.createElement("div");
                    actionDiv.className = "notify-actions";

                    const acceptBtn = document.createElement("button");
                    acceptBtn.className = "notify-btn accept-btn";
                    acceptBtn.innerText = "Accept";
                    acceptBtn.onclick = function (e) {
	 					e.stopPropagation(); 
                        NOTIFY_PANEL.accept(item.senderId);
                        div.remove();
                    };

                    const rejectBtn = document.createElement("button");
                    rejectBtn.className = "notify-btn reject-btn";
                    rejectBtn.innerText = "Reject";
                    rejectBtn.onclick = function (e) {
	 					e.stopPropagation(); 
                        NOTIFY_PANEL.reject(item.senderId);
                        div.remove();
                    };

                    actionDiv.appendChild(acceptBtn);
                    actionDiv.appendChild(rejectBtn);
                    div.appendChild(actionDiv);
                }

                panel.appendChild(div);
            });
        }

        // Position panel under notification button
        const button = document.getElementById("notification");
        const rect = button.getBoundingClientRect();

        panel.style.top = rect.bottom + window.scrollY + "px";
        panel.style.left = rect.right - 420 + window.scrollX + "px";

        document.body.appendChild(panel);

        setTimeout(() => {
            document.addEventListener("click", outsideClickHandler);
        }, 0);
    }

    return {

        toggle: function (notifications) {
            if (panel) {
                closePanel();
            } else {
                buildPanel(notifications);
            }
        },

        accept: function (id) {
            console.log("Accepted friend request:", id);
            fetch("/MapFinder/friendrequest/accept?friendId="+id).then((res)=>res.json())
			.then((msg)=>{
				console.log(msg);
			})
        },

        reject: function (id) {
            console.log("Rejected friend request:", id);
            fetch("/MapFinder/friendrequest/reject?friendId="+id).then((res)=>res.json())
			.then((msg)=>{
				console.log(msg);
			})
        },

        close: function () {
            closePanel();
        }
    };

})();


MODAL = (function () {

    function injectStyles() {
        if (document.getElementById("custom-modal-styles")) return;

        const style = document.createElement("style");
        style.id = "custom-modal-styles";
        style.innerHTML = `
            .custom-overlay {
                position: fixed;
                inset: 0;
                background: rgba(0,0,0,0.4);
                display: flex;
                justify-content: center;
                align-items: flex-start;
                padding-top: 80px;
                z-index: 9999;
            }

            .custom-modal {
                background: #fff;
                width: 600px;
                max-width: 90%;
                padding: 25px;
                border-radius: 8px;
                box-shadow: 0 8px 25px rgba(0,0,0,0.2);
                font-family: Arial, sans-serif;
                animation: fadeIn 0.2s ease-in-out;
            }

            .custom-modal h3 {
                margin: 0 0 10px;
            }

            .custom-modal p {
                margin: 0;
                font-size: 14px;
                line-height: 1.5;
                overflow-wrap: anywhere;   /* FIXES LONG TEXT */
                word-break: break-word;
            }

            .custom-buttons {
                margin-top: 25px;
                display: flex;
                justify-content: flex-end;
                gap: 10px;
            }

            .custom-btn {
                padding: 6px 15px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 13px;
            }

            .btn-ok {
                background-color: #28a745;
                color: #fff;
            }

            .btn-cancel {
                background-color: #dc3545;
                color: #fff;
            }

            @keyframes fadeIn {
                from { opacity: 0; transform: translateY(-10px); }
                to { opacity: 1; transform: translateY(0); }
            }
        `;
        document.head.appendChild(style);
    }

    function createModal(heading, message, type) {
        injectStyles();

        return new Promise((resolve) => {
            const oldModal = document.getElementById("custom-modal");
            if (oldModal) oldModal.remove();

            const overlay = document.createElement("div");
            overlay.id = "custom-modal";
            overlay.className = "custom-overlay";

            const modalBox = document.createElement("div");
            modalBox.className = "custom-modal";

            const title = document.createElement("h2");
            title.textContent = heading;

            const msg = document.createElement("p");
            msg.textContent = message;

            const btnContainer = document.createElement("div");
            btnContainer.className = "custom-buttons";

            const okBtn = document.createElement("button");
            okBtn.textContent = "OK";
            okBtn.className = "custom-btn btn-ok";

            okBtn.onclick = function () {
                overlay.remove();
                resolve(true);
            };

            btnContainer.appendChild(okBtn);

            if (type === "confirm") {
                const cancelBtn = document.createElement("button");
                cancelBtn.textContent = "Cancel";
                cancelBtn.className = "custom-btn btn-cancel";

                cancelBtn.onclick = function () {
                    overlay.remove();
                    resolve(false);
                };

                btnContainer.appendChild(cancelBtn);
            }

            modalBox.appendChild(title);
            modalBox.appendChild(msg);
            modalBox.appendChild(btnContainer);
            overlay.appendChild(modalBox);
            document.body.appendChild(overlay);
            
            
            
           function handleEnterKey(event) {
                if (event.key === "Enter") {
                    console.log("Enter clicked");
                    overlay.remove();
                    resolve(true);
                    document.removeEventListener("keydown", handleEnterKey);
                }
            }

            document.addEventListener("keydown", handleEnterKey); 
        });
    }

    return {
        show: function (heading, message, type = "alert") {
            return createModal(heading, message, type);
        }
    };

})();


// ------------------- WIN CELEBRATION SYSTEM -----------------------
CELEBRATION = (function () {

    let overlay, flashCanvas, fctx, cCanvas, cctx;
    let sparkles = [];
    let flashRunning = false;
    let flashRAF;

    // ---------- Inject CSS ----------
    function injectStyle() {

        if (document.getElementById("celebration-style")) return;

        const style = document.createElement("style");
        style.id = "celebration-style";

        style.innerHTML = `
        #celebration-overlay{
            position:fixed;
            inset:0;
            background:rgba(0,0,0,0.4);
            display:flex;
            justify-content:center;
            align-items:center;
            z-index:10000;
        }

        .celebration-card{
            background:#fff;
            padding:40px;
            border-radius:20px;
            text-align:center;
            width:420px;
            box-shadow:0 20px 60px rgba(0,0,0,0.2);
            font-family:Nunito, sans-serif;
            position:relative;
        }

        .celebration-title{
            font-size:28px;
            color:#ff6b9d;
            font-weight:bold;
        }

        .celebration-msg{
            margin-top:15px;
            color:#666;
            font-weight:600;
        }

        .celebration-btn{
            margin-top:25px;
            padding:10px 25px;
            border:none;
            border-radius:30px;
            background:linear-gradient(135deg,#ff6b9d,#ffa75c);
            color:white;
            cursor:pointer;
            font-weight:bold;
        }

        #confetti-canvas{
            position:fixed;
            inset:0;
            pointer-events:none;
            z-index:10001;
        }
        `;

        document.head.appendChild(style);
    }

    // ---------- Build HTML ----------
    function buildUI(username, message) {

        remove();

        overlay = document.createElement("div");
        overlay.id = "celebration-overlay";

        overlay.innerHTML = `
            <div class="celebration-card">
                <h2 class="celebration-title">Congratulations ${username}!</h2>
                <div class="celebration-msg">${message}</div>
                <button class="celebration-btn" onclick='window.location.reload();'>Awesome</button>
            </div>
        `;

        document.body.appendChild(overlay);

        overlay.querySelector(".celebration-btn").onclick = close;

        // canvas
        cCanvas = document.createElement("canvas");
        cCanvas.id = "confetti-canvas";
        document.body.appendChild(cCanvas);
        cctx = cCanvas.getContext("2d");

        resizeCanvas();
        startConfetti();
    }

    function resizeCanvas(){
	 	if(!cCanvas) return; 
        cCanvas.width = window.innerWidth;
        cCanvas.height = window.innerHeight;
    }

    window.addEventListener("resize", resizeCanvas);

    // ---------- SIMPLE CONFETTI ----------
    let confetti = [];

    function createPiece(){
        return {
            x: Math.random()*cCanvas.width,
            y: -20,
            vy: 3+Math.random()*3,
            size: 6+Math.random()*6,
            color: `hsl(${Math.random()*360},80%,60%)`
        };
    }

    function startConfetti(){
        for(let i=0;i<120;i++) confetti.push(createPiece());
        flashRunning = true;
        loop();
    }

    function loop(){
        if(!flashRunning) return;

        cctx.clearRect(0,0,cCanvas.width,cCanvas.height);

        confetti.forEach(p=>{
            p.y += p.vy;
            cctx.fillStyle=p.color;
            cctx.fillRect(p.x,p.y,p.size,p.size);
        });

        confetti = confetti.filter(p=>p.y < cCanvas.height+20);

        flashRAF = requestAnimationFrame(loop);
    }

    // ---------- CLOSE ----------
    function close(){
        remove();
    }

    function remove(){
        flashRunning=false;
        cancelAnimationFrame(flashRAF);

        if(overlay) overlay.remove();
        if(cCanvas) cCanvas.remove();

        overlay=null;
        confetti=[];
    }

    // ---------- PUBLIC API ----------
    return {
        show:function(username,message){
            injectStyle();
            buildUI(username,message);
        },
        close:close
    };

})();





