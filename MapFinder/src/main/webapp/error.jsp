<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
<style>
        *{
        padding: 0;
        margin: 0;
        box-sizing: border-box; 
        font-family: ui-sans-serif, system-ui, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
        font-weight: 400;
    }

    img{
        width: 20px;
        height: 20px;
    }

    body{
        height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    #error_container{
        background-color: #FFF;
        box-shadow: 5px 5px 1rem  #0000002f;
        border-radius: 20px;
        width: 430px;
        padding: 50px;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        gap: 1.5rem;
        text-align: center;
    }

    #icon{
        background:linear-gradient(to right bottom,#FF5F00,#FC3B2D);
        padding: 20px;
        border-radius: 50%;
    }

    #icon > img{
        width: 60px;
        height: 60px;
    }

    h1{
        font-size: 3rem;
    }

    h3{
        font-size: 1.5rem;
    }

    p{
        font-size: 16px;
        color: #0000008a;
    }

    #button_container{
        display: flex;
        gap: 1rem;   
    }

    button{
        all: unset;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 1rem;
        padding: 10px 15px;
        border-radius: 10px;
        color: #FFF;
        gap: 10px;
    }

    #tryAgain{
        background-color: #FF5F00;
    }
    #home{
        background-color: #193CBB;
    }

    hr{
        width: 100%;
    }

    #error{
        font-size: 15px;
        color: #0000005f;
    }
    button{
    cursor:pointer}
</style>
<body>

    <div id="error_container">
        <div id="icon">
            <img src="./icons/colorized-warning.png" alt="">
        </div>
        <h1>Oops!  😕</h1>
        <h3>Something went wrong</h3>
        <p >We encountered an error. Please try again later or return to the homepage.</p>
        <div id="button_container">
            <button id="tryAgain" onclick="window.location.reload();">
                <img src="./icons/colorized-refresh.png" alt="">
                Try Again
            </button>
            <button id="home" onclick="window.location.href='signin';">
                <img src="./icons/colorized-home.png.png" alt="">
                Go Home
            </button>
        </div>
        <hr>
        <p id="error">Error Code : 500</p>
    </div>
    
</body>
</html>