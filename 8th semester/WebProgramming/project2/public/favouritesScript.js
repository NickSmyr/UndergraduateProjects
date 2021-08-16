
function main(){
    const handlebars = window.Handlebars
    // Get the header
    var header = document.querySelector("header");
    // Get the offset position of the navbar
    var sticky = header.offsetTop;

    var currentEpoch = 0
    var worksGlobal = []

    // Filter a works array based on string text
    // Return a list of fitlered works
    function filtercontents(str , works){
        worksResult = []
        works.forEach(element =>{
            if (element.author.toLowerCase().includes(str) || element.description.toLowerCase().includes(str) || element.title.toLowerCase().includes(str)){
                worksResult.push(element)
            }
        })
        return worksResult
    }

    // When the user scrolls the page, execute myFunction
    window.onscroll = function() {
        if (window.pageYOffset > sticky) {
        header.classList.add("sticky");
        } else {
        header.classList.remove("sticky");
        }
        
    };
    // Add all favourites initially
    whenSearch("",0)
    searchInput = document.querySelector("#searchInput")
    searchInput.addEventListener("input", function(event){
        searchStr = searchInput.value
        currentEpoch +=1
        // Immediate invoked function expression
        setTimeout(function(searchStr, epoch){
            return function(){
                // If the "timestamp" of this async func is smaller than the up to date timestampt this means
                // another async function is waiting to execute or has exectued and therefore this method
                // shouldn't do anything
                if (epoch < currentEpoch){
                    return
                }
                selectedWorks = filtercontents(searchStr, worksGlobal)
                updateContents(selectedWorks)
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
        url = "../favourites/?search=" + encodeURIComponent(searchStr) 
        console.log("fetching url " + url)
        fetch(url, init).then(response => response.json())
                        .then(obj => {
                            console.log("Received object ", obj)
                            works =[]
                            obj.forEach(element => {
                                var res=  element
                                // ETL for data
                                title = res._title
                                subtitle = res._subtitle
                                author = res._author
                                comments = res._comments
                                id = res._workId
                                works.push({title: title, description:subtitle, author:author, comments:comments, id:id})
                            }); 
                            worksGlobal = works    
                            updateContents(works) 
                        })
                        .catch(reason => console.log("Error on fetch " + reason))
    }
    function updateContents(works){
        // works must be a list of "work"  objects
        // each work has id, title, description, author, comments properties
        //Reset contents
        document.querySelector("#booklist").innerHTML = ""
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
                        <p class="comments">{{this.comments}}</p>
                        <button class="isedit">
                            <div class="favouritebookpicture">
                                <img src="https://cdn3.iconfinder.com/data/icons/media-player-music-video-minimalist-outline-1/48/Video_player_pencil_edit-512.png" width="100%">
                            </div>
                        </button>
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
        document.querySelectorAll(".isfavourite").forEach(element => {
            element.style.backgroundColor = "green"
            element.addEventListener("click" , event=>{
                // Remove from database
                const encodeGetParams = p => 
                Object.entries(p).map(kv => kv.map(encodeURIComponent).join("=")).join("&");

                let myHeaders = new Headers()
                myHeaders.append("Accept", "application/json")
                let init = {
                    method: "DELETE",
                    headers: myHeaders
                }
                workId = element.parentElement.querySelector(".workId").innerHTML
                const params = {
                    workId: workId,
                };

                url = "../favourites?" + encodeGetParams(params)

                console.log("fetching url " + url)
                fetch(url, init).catch(reason => console.log(reason))
                // Remove from the list
                element.parentElement.parentElement.remove()
                // Removing from global work list as well
                for (let i = 0 ; i < worksGlobal.length ; i++){
                    if (worksGlobal[i].id == workId ){
                        worksGlobal.splice(i , 1)
                        break
                    }
                }
                
            })
        }
        )
        document.querySelectorAll(".isedit").forEach(element => {
            element.addEventListener("click" , event =>{
                workId = element.parentElement.querySelector(".workId").innerHTML
                window.location.replace("./edit.html?workId=" + encodeURIComponent(workId));
            })
        })
    }
    
}
window.addEventListener("load" , main)