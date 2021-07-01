const sql = require('./db')

const Task = function(){};

Task.getByCategory = function(data,resultCallback) {
    sql.query("SELECT * FROM TASK WHERE category_id=?",data.category_id,function(err,res){
        if (err) {
            console.log("Fail to get: ",err);
            resultCallback(err,null);
            return;
        }

        resultCallback(null,res)
    });
}


module.exports = Task