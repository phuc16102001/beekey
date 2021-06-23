const sql = require('./db')

const Report = function(){};

Report.post = function(data,resultCallback) {
    sql.query("INSERT INTO REPORT SET ?",data,function(err,res){
        if (err) {
            console.log("Fail to create: ",err);
            resultCallback(err,null);
            return;
        }

        resultCallback(null,res)
    });
}

Report.get = function(resultCallback) {
    sql.query("SELECT * FROM REPORT",function(err,res) {
        if (err) {
            console.log("Fail to get: ",err);
            resultCallback(err,null);
            return;
        }

        resultCallback(null,res)
    })
}

module.exports = Report