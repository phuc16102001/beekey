const sql = require('./db')
const config = require('../config/config')
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

CounterOffer.decline = function(data,resultCallback) {
    sql.query("DELETE FROM COUNTER_OFFER WHERE task_id=? AND lancer_id=?",
        [data.task_id,data.lancer_id],
        function(err,res){
            if (err) {
                console.log("Fail to delete: ",err);
                resultCallback(err,null);
                return;
            }
            resultCallback(null,res)
        }
    );
}

CounterOffer.accept = function(data,resultCallback) {
    sql.query("UPDATE TASK SET status=?, lancer_id=? WHERE task_id=?; DELETE FROM COUNTER_OFFER WHERE task_id=?",
        [config.constant.STATUS.ACCEPTED,data.lancer_id,data.task_id,data.task_id],
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