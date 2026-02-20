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
		
		            console.log("session exist");
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


function checkBrowser(){
    if (!("Notification" in window)) {
        alert("This browser does not support notification");
        return;
    } 
}