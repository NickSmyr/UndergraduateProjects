const {MongoClient} = require('mongodb');
/**
 * Abstract workdao class.
 */
class WorkDAO {

    constructor() {
        if (this.constructor == WorkDAO) {
            throw new Error("Abstract classes can't be instantiated.");
        }
    }
    async init(){
        throw new Error("Method init must be implemented")
    }
    async close(){
        throw new Error("Method close must be implemented")
    }
  
    async alreadyExists(workId) {
        throw new Error("Method 'alreadyExists' must be implemented.");
    }
    /**
     * Updates the work object or adds a new work object to the list if it does not exist
     * @param {A Work object} work 
     */
    async save(work) {
        throw new Error("Method 'alreadyExists' must be implemented.");
    }
    /**
     * Returns all the works stored
     */
    async getAll() {
        throw new Error("Method 'getAll' must be implemented.");
    }
    /**
     * Returns the work matching workID
     * @param {*} workId 
     */
    get(workId){
        throw new Error("Method 'get' must be implemented.");
    }

    delete(workId){
        throw new Error("Method 'delete' must be implemented.");
    }
  }

class WorkMemoryDAO extends WorkDAO{
    constructor(){
        super()
        this._workList = []
    }
    async init(){
        return
    }
    async close(){
        return
    }
    async alreadyExists(workId){
        for (let i=0; i<this._workList.length ; i++){
            if (this._workList[i].workId == workId){
                return true
            }
        }
        return false
    }
    async save(work){
        var index = -1
        for (let i=0; i<this._workList.length ; i++){
            console.log("Current work " , this._workList[i])
            if (this._workList[i].workId == work.workId){
                index = i
            }
        }
        if(index == -1){
            this._workList.push(work)
        }
        else {
            this._workList[index] = work
        }
    }
    async get(workId){
        var index = -1
        for (let i=0; i<this._workList.length ; i++){
            console.log("Current work " , this._workList[i])
            if (this._workList[i].workId == workId){
                index = i
            }
        }
        return this._workList[index]
    }
    async getAll(){
        return this._workList
    }
    async delete(workId){
        for (let i=0; i<this._workList.length ; i++){
            console.log("Current work " , this._workList[i])
            if (this._workList[i].workId == workId){
                this._workList.splice(i,1)
                return
            }
        }
    }

}
class Work{
    constructor(workId, title, subtitle, author, comments){
        this._workId = workId
        this._title = title
        this._subtitle = subtitle
        this._author = author
        this._comments = comments
    }

    get workId(){
        return this._workId
    }
    get title(){
        return this._title
    }
    get subtitle(){
        return this._subtitle
    }
    get comments(){
        return this._comments
    }
    get author(){
        return this._author
    }
    set author(val){
        this._author = val
    }
    set title(val){
        this._title = val
    }
    set subtitle(val){
        this._subtitle = val
    }
    set comments(val){
        this._comments = val
    }
    set workId(val){
        this._workId = val
    }
}
 

class WorkMongoDAO extends WorkDAO{
    constructor(){
        super()
        const uri = 'mongodb://myUserAdmin:admin@localhost:27017/library?authSource=admin';
        this.client = new MongoClient(uri, {
            useNewUrlParser: true,
            useUnifiedTopology: true,
        });
    }
    async init(){
        try{
            await this.client.connect();
            const database = this.client.db('library');
            this.works = database.collection('works');
            console.log("this works " + this.works)
        }
        catch(e){
            console.error(e)
        }
    }
    async close(){
        await client.close();
    }
        
    async alreadyExists(workId){
        const result = await this.works.findOne({_workId: workId})
        return result != null
    }
    async save(work){
        console.log("Saving work :")
        console.log(work)
        // create a filter for a movie to update
        const filter = { _workId: work.workId };
        // this option instructs the method to create a document if no documents match the filter
        const options = { upsert: true };
        // create a document that sets the plot of the movie
        const updateDoc = {
        $set: {
            _workId: work.workId,
            _title: work.title,
            _subtitle: work.subtitle,
            _comments: work.comments,
            _author: work.author,
        },
        };
        const result = await this.works.updateOne(filter, updateDoc, options);
        console.log(
        `${result.matchedCount} document(s) matched the filter, updated ${result.modifiedCount} document(s)`,
        );
    }
    async get(workId){
        const work = await this.works.findOne({_workId: workId})
        return work
    }
    async getAll(){
        var outputList = []
        const cursor = this.works.find({}, {});
        // print a message if no documents were found
        if ((await cursor.count()) === 0) {
          console.log("No documents found!");
          return []
        }
        // replace console.dir with your callback to access individual elements
        await cursor.forEach(element => {
            outputList.push(element)
        });
        return outputList
    }
    async delete(workId){
        const result = await this.works.deleteOne({_workId : workId});
    }

}
exports.Work = Work
exports.WorkDAO = WorkDAO
exports.WorkMemoryDAO = WorkMemoryDAO
exports.WorkMongoDAO = WorkMongoDAO