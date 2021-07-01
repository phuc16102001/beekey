const sql = require('./db')

const CounterOffer = function(){};

CounterOffer.postOffer = function(data,resultCallback) {
    sql.query("INSERT INTO COUNTER-OFFER SET ?",data,function(err,res){
        if (err) {
            console.log("Fail to create: ",err);
            resultCallback(err,null);
            return;
        }

        resultCallback(null,res)
    });
}


module.exports = CounterOffer