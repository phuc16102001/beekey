const sql = require('./db')

const Category = function(){};

Category.get = function(data,resultCallback) {
    sql.query("SELECT * FROM CATEGORY",function(err,res){
        if (err) {
            console.log("Fail to get: ",err);
            resultCallback(err,null);
            return;
        }

        resultCallback(null,res)
    });
}


module.exports = Category