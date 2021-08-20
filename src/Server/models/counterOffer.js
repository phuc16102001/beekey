const sql = require('./db')
const config = require('../config/config')
const CounterOffer = function(){};

CounterOffer.postOffer = function(data,resultCallback) {
    values = [data]
    let sqlString = `
        start transaction;

        INSERT INTO COUNTER_OFFER SET ?;

        commit;
    `
    sql.query(sqlString,values,function(err,res){
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
    values = [data.task_id]
    let sqlString = `
        start transaction;
    
        set @task_id=?;

        SELECT * FROM COUNTER_OFFER
        WHERE task_id=@task_id;

        commit;
    `

    sql.query(sqlString,values,function(err,res){
            if (err) {
                console.log("Fail to get: ",err);
                resultCallback(err,null);
                return;
            }
            resultCallback(null,res)
        }
    );
}

CounterOffer.decline = function(data,resultCallback) {
    values = [data.task_id,data.lancer_id]
    let sqlString = `
        start transaction;

        set @task_id=?;
        set @lancer_id=?;

        DELETE FROM COUNTER_OFFER 
        WHERE task_id=@task_id and lancer_id=@lancer_id;

        commit;
    `
    sql.query(sqlString,values,function(err,res){
            if (err) {
                console.log("Fail to make decline request: ",err);
                resultCallback(err,null);
                return;
            }
            resultCallback(null,res)
        }
    );
}

CounterOffer.accept = function(data,resultCallback) {
    values = [data.lancer_id, data.task_id, config.constant.STATUS.ACCEPTED]
    let sqlString = `
        start transaction;

        set @lancer_id = ?;
        set @task_id = ?;
        set @accept_status = ?;

        UPDATE TASK 
        SET status = @accept_status, lancer_id=@lancer_id
        WHERE task_id=@task_id;

        DELETE FROM COUNTER_OFFER
        WHERE task_id=@task_id;

        commit;
    `

    sql.query(sqlString,values,(err,res)=>{
        if (err) {
            console.log("Fail to make accept request: ",err);
            resultCallback(err,null);
            return;    
        }
        resultCallback(null,res)
    })
}

module.exports = CounterOffer