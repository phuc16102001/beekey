const sql = require('./db')

const CounterOffer = function(){};

CounterOffer.postOffer = function(data,resultCallback) {
    sql.query("INSERT INTO COUNTER_OFFER SET ?",
        data,
        function(err,res){
            if (err) {
                console.log("Fail to create: ",err);
                resultCallback(err,null);
                return;
            }
            resultCallback(null,res)
        }
    );
}

CounterOffer.getByRequest = function(data,resultCallback) {
    sql.query("SELECT task_id, lancer_id, reason, offer FROM COUNTER_OFFER WHERE task_id=?",
        data.task_id,
        function(err,res){
            if (err) {
                console.log("Fail to create: ",err);
                resultCallback(err,null);
                return;
            }
            resultCallback(null,res)
        }
    );
}

module.exports = CounterOffer