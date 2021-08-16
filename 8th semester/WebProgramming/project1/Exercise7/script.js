function hideCookiePopup(){
    var a = document.querySelector(".cookie-popup")
    a.style.display = "none"
} 

function my_main(){
    l = document.querySelectorAll("p")

    // Annoying cookie popup code
    popup = document.querySelector(".cookie-popup .accept")
    popup.onclick = hideCookiePopup
    popup = document.querySelector(".cookie-popup .reject")
    popup.onclick = hideCookiePopup

    // Make paragraphs in main bold whenever they are double clicked
    l = document.querySelectorAll("main  p")
    l.forEach(element => {
        element.addEventListener("dblclick" , function(event){
            console.log("double cliked")
            event.target.style.fontWeight = "bold"
        })    
    });
    l = document.querySelectorAll("body h2")
    l.forEach(element => {
        element.addEventListener("click" , function(event){
            previousHTML = event.target.innerHTML
            newHTML = previousHTML.substr(1)
            event.target.innerHTML = newHTML
        })
    });
    
    today = new Date().getDay()
    if (today == 0){
        today = "Κυριακή"
    }
    else if (today == 1){
        today = "Δευτέρα"
    }
    else if (today == 2){
        today = "Τρίτη"
    }
    else if (today == 3){
        today = "Τετάρτη"
    }
    else if (today == 4){
        today = "Πέμπτη"
    }
    else if (today == 5){
        today = "Παρασκευή"
    }
    else if (today == 6){
        today = "Σάββατο"
    }
    else {
        today = "Σφάλμα ημέρας"
    }
    document.querySelector(".date-holder").innerHTML = "Σήμερα είναι: " + today

    // Annoying before you leave popup, its so annoying i had to comment it out while writing the rest of the js code
    document.querySelector("body").addEventListener("mouseleave" , function(event){
        alert("Before you go...\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nPlease give 10 to this assignment!")
    });
}


window.onload = my_main

