
function main(){
    const handlebars = window.Handlebars
    // Get the header
    var header = document.querySelector("header");
    // Get the offset position of the navbar
    var sticky = header.offsetTop;

    // When the user scrolls the page, execute myFunction
    window.onscroll = function() {
        if (window.pageYOffset > sticky) {
        header.classList.add("sticky");
        } else {
        header.classList.remove("sticky");
        }
        
    };
    //Get parameters
    const urlParams = new URLSearchParams(window.location.search);
    const workId = urlParams.get('workId');
    let myHeaders = new Headers()
    myHeaders.append("Accept", "application/json")
    let init = {
        method: "GET",
        headers: myHeaders
    }

    const encodeGetParams = p => 
    Object.entries(p).map(kv => kv.map(encodeURIComponent).join("=")).join("&");

    const params = {
        workId: workId,
    };

    url =  "../favourites?" + encodeGetParams(params)

    console.log("fetching url " + url)
    fetch(url, init)
        .then(value => value.json())
        .then(value => {
            console.log("Got item ")
            console.log(value)
            if (value._comments == undefined){
                value._comments = ""
            }
            document.querySelector("#author").value = value._author
            document.querySelector("#comments").value = value._comments
            document.querySelector("#subtitle").value = value._subtitle
            document.querySelector("#title").value = value._title
            document.querySelector("#id").innerHTML = value._workId            
        })
        .catch(reason => console.log(reason))

    document.querySelector("#submit").addEventListener("click" , event => {
        author = document.querySelector("#author").value
        comments = document.querySelector("#comments").value
        subtitle = document.querySelector("#subtitle").value
        title = document.querySelector("#title").value 
        id = document.querySelector("#id").innerHTML

        let myHeaders = new Headers()
        myHeaders.append("Accept", "application/json")
        let init = {
            method: "PUT",
            headers: myHeaders
        }

        const encodeGetParams = p => 
        Object.entries(p).map(kv => kv.map(encodeURIComponent).join("=")).join("&");

        const params = {
            workId : id,
            author : author,
            comments : comments,
            subtitle : subtitle,
            title : title
        };

        url =  "../favourites?" + encodeGetParams(params)

        console.log("fetching url " + url)
        fetch(url, init)
            .then(value => {
                window.location.replace("./favourites.html");
            })
            .catch(reason => console.log(reason))
    })


    
}
window.addEventListener("load" , main)