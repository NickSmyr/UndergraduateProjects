const { time } = require('console')
const express = require('express')
const { title } = require('process')
const app = express()
const port = 8888

const DAO = require("./dao.js")

//const mDAO = new DAO.WorkMemoryDAO()
const mDAO = new DAO.WorkMongoDAO()
mDAO.init().then(result => console.log("DAO initiated"))

app.use('/static' , express.static(__dirname + "/public"))
app.get('/favicon.ico' , (req, res) =>{
  res.sendFile(__dirname + "/favicon.ico")
})
app.get('/', (req, res) =>
{
  res.redirect("/static/")
})

// Get all favourite works
// If a workId is specified then only that work is returned
app.get('/favourites' ,(req , res) =>
{
  var workId = req.query.workId
  if (workId == undefined){
    mDAO.getAll().then(value => res.send(JSON.stringify(value))).catch(console.error)
  }
  else{
    mDAO.get(workId).then(result =>{
      if (result == undefined){
        res.status(401)
        return
      }
      res.send(JSON.stringify(result))
    }).catch(console.error)
    
    
  }
    
})
// Create a new favourite item
app.post('/favourites' ,(req , res) =>
{
  var workId = req.query.workId
  var author = req.query.author
  var subtitle = req.query.subtitle
  var title = req.query.title
  var comments = req.query.comments
  mDAO.alreadyExists(workId).then(result => {
    if (result==true){
      res.status(406).send("Already exists")
      return
    }
    work = new DAO.Work(workId ,title , subtitle ,   author , comments)
    mDAO.save(work).then(result =>{
      res.send("Favourited work " + JSON.stringify(work) )
    }).catch(console.error)
    
  }).catch(console.error)
})

// Update a new favourite item
app.put('/favourites' ,(req , res) =>
{
  var workId = req.query.workId
  var author = req.query.author
  var subtitle = req.query.subtitle
  var title = req.query.title
  var comments = req.query.comments

  work = new DAO.Work(workId ,title , subtitle ,   author , comments)
  mDAO.save(work).then(result => res.send("Favourited work " + JSON.stringify(result)))
    .catch(console.error)
})

// Delete a favourite work
app.delete('/favourites' ,(req , res) =>
{
  var workId = req.query.workId
  mDAO.delete(workId).then(result=> res.send("Deleted work" + workId))
    .catch(console.error)
})

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})
