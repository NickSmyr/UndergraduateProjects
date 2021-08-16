function my_main(){
    
    /*Custom message if values are missing */
    const allInputs = document.querySelectorAll("fieldset input");
    allInputs.forEach(element => {
        element.addEventListener("input", function (event) {
            if (element.validity.valueMissing) {
                element.setCustomValidity("Αυτό το πεδίο είναι υποχρεωτικό");
            }
            else{
                element.setCustomValidity("")
            }
            
        })
    });
        
    const email = document.querySelector("fieldset input[name=\"email\"]");

    email.addEventListener("input", function (event) {
        if (email.validity.typeMismatch) {
            email.setCustomValidity("Παρακαλώ εισάγετε μία έγκυρη διεύθυνση email");
        } else {
            email.setCustomValidity("");
        }
      });

    const password = document.querySelector("fieldset input[name=\"password\"]");
    const passwordConfirm = document.querySelector("fieldset input[name=\"passwordconfirm\"]");
    passwordConfirm.addEventListener("input", function (event) {
        if (passwordConfirm.value != password.value){
            passwordConfirm.setCustomValidity("Οι κωδικοί πρέπει να είναι οι ίδιοι");
        }
        else {
            passwordConfirm.setCustomValidity("");
        }
    });
    
    
}


window.addEventListener("load" , my_main)



