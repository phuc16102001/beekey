const sql = require('./db')

const Feedback = function(){};

Feedback.post = function(data,resultCallback) {
    sql.query("INSERT INTO FEEDBACK(title,description,user_id,task_id,lancer_id) SELECT (?,?,?,?,lancer_id) FROM TASK WHERE task_id=?",
    [data.title,data.description,data.user_id,data.task_id,data.task_id],
    function(err,res){
        if (err) {
            console.log("Fail to create: ",err);
            resultCallback(err,null);
            return;
        }

        resultCallback(null,res)
    });
}

Feedback.get = function(resultCallback) {
    sql.query("SELECT * FROM FEEDBACK WHERE lancer_id=?",[data.username],function(err,res) {
        if (err) {
            console.log("Fail to get: ",err);
            resultCallback(err,null);
            return;
        }

        resultCallback(null,res)
    })
}

module.exports = Feedback