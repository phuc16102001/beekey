const   sql = require('./db')
        config = require("../config/config")

const Task = function(){};

Task.getByCategory = function(data,resultCallback) {
    sql.query("SELECT * FROM TASK WHERE category_id=? AND status=?",
    [data.category_id,config.constant.STATUS.PENDING],
    (err,res)=>{
        if (err) {
            console.log("Fail to get: ",err);
            resultCallback(err,null);
            return;
        }

        resultCallback(null,res)
    });
}

Task.postTask = function(data,resultCallback) {
    data["status"]=config.constant.STATUS.PENDING
    sql.query("INSERT INTO TASK SET ?",data,(err,res)=>{
        if (err) {
            console.log("Fail to create task: ",err)
            resultCallback(err,null);
            return;
        }

        resultCallback(null,res);
    })
}


module.exports = Task