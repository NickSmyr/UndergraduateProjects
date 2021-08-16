function main(){
    const handlebars = window.Handlebars
    console.log("handlerabars")
    console.log(handlebars)
    // Get the header
    var header = document.querySelector("header");
    // Get the offset position of the navbar
    var sticky = header.offsetTop;

    var currentEpoch = 0

    // When the user scrolls the page, execute myFunction
    window.onscroll = function() {
        if (window.pageYOffset > sticky) {
        header.classList.add("sticky");
        } else {
        header.classList.remove("sticky");
        }
        
    };
    searchInput = document.querySelector("#searchInput")
    searchInput.addEventListener("input", function(event){
        searchStr = searchInput.value
        currentEpoch +=1
        // Immediate invoked function expression
        setTimeout(function(searchStr, currentEpoch){
            return function(){
                whenSearch(searchStr, currentEpoch)
            }

        }(searchStr, currentEpoch), 500)
    })
    // Each time a user changes the input a timeout is activated and after 0.5 seconds it changes the content
    // TO achieve synchronization each function call that is timed with setTimeout has a number indicating
    // the count of changes that happened when the function call started
    function whenSearch(searchStr, epoch){
        console.log("When search with epoch " + epoch + " and searchStr " + searchStr)
        if (epoch < currentEpoch){
            console.log("Change job invalidated")
            return
        }

        //Reset contents
        document.querySelector("#booklist").innerHTML = ""
        let myHeaders = new Headers()
        myHeaders.append("Accept", "application/json")
        let init = {
            method: "GET",
            headers: myHeaders
        }
        url = "https://reststop.randomhouse.com/resources/works/?search=" + encodeURIComponent(searchStr) 
        console.log("fetching url " + url)
        fetch(url, init).then(response => response.json())
                        .then(obj => {
                            console.log("Received object ", obj)
                            works =[]
                            if (!obj.hasOwnProperty('work')){
                                return
                            }
                            // Sometimes the REST api will return a single object instead of a list
                            if (Array.isArray(obj.work)){
                                obj.work.forEach(element => {
                                    var res=  element.titleSubtitleAuth.split(":")
                                    // ETL for data
                                    title = res[0]
                                    subtitle = res[1]
                                    author = res[2]
                                    workId = element.workid
                                    works.push({title: title, description:subtitle, author:author, id:workId})
                                });
                            }
                            else{
                                var res=  obj.work.titleSubtitleAuth.split(":")
                                // ETL for data
                                title = res[0]
                                subtitle = res[1]
                                author = res[2]
                                works.push({title: title, description:subtitle, author:author})
                            }
                            
                            updateContents(works) 
                        })
                        .catch(reason => console.log("Error on getch" + reason))
    }
    function updateContents(works){
        // works must be a list of "work"  objects
        // each work has title, description, author properties
        console.log("Adding " + works.length + " to list")
        let templateStr = `
                {{#each works}}
                <article class="bookitem">
                    <div class="bookitem-container">
                        <h2>{{this.title}}</h2>
                        <div class="imageholder">
                            <img src="https://attackofthefanboy.com/wp-content/uploads/2021/03/minecraft-how-to-make-a-book.jpg" width="100%" height="100%">
                        </div>
                        <p class="description">{{this.description}}</p>
                        <p class="author">{{this.author}}</p>
                        <button class="isfavourite">
                            <div class="favouritebookpicture">
                                <img src="https://iconape.com/wp-content/png_logo_vector/star-2.png" width="100%">
                            </div>
                        </button>
                        <div class="workId" style="display: none">{{this.id}}</div>
                    </div>
                </article>
                {{/each}}     
        `;
        var template = Handlebars.compile(templateStr)
        newHTML = template({ works: works })
        document.querySelector("#booklist").innerHTML += newHTML
        // Favourite button logic
        document.querySelectorAll(".isfavourite").forEach(element =>{
            element.addEventListener('click', event=>{
                element.style.backgroundColor= "green"
                // Add to favourites
                workId = element.parentElement.querySelector(".workId").innerHTML
                title = element.parentElement.querySelector("h2").innerHTML
                subtitle = element.parentElement.querySelector(".description").innerHTML
                author = element.parentElement.querySelector(".author").innerHTML

                let myHeaders = new Headers()
                myHeaders.append("Accept", "application/json")
                let init = {
                    method: "POST",
                    headers: myHeaders
                }

                const encodeGetParams = p => 
                Object.entries(p).map(kv => kv.map(encodeURIComponent).join("=")).join("&");

                const params = {
                    workId: workId,
                    title: title,
                    subtitle: subtitle,
                    author: author
                };

                url = "../favourites?" + encodeGetParams(params)

                console.log("fetching url " + url)
                fetch(url, init).catch(reason => console.log(reason))
                        
            })
        })
    }
    
}
window.addEventListener("load" , main)