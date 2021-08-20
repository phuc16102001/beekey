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
        SELECT * FROM COUNTER_OFFER
        WHERE task_id=?;
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
    values = [data.client_id,data.lancer_id, data.task_id, config.constant.STATUS.ACCEPTED]
    let sqlString = `
        start transaction;

        set @client_id = ?;
        set @lancer_id = ?;
        set @task_id = ?;
        set @accept_status = ?;
        set @new_offer = (
            SELECT offer
            FROM COUNTER_OFFER
            WHERE task_id=@task_id and lancer_id=@lancer_id
        );

        UPDATE TASK 
        SET status = @accept_status, lancer_id=@lancer_id, offer = @new_offer
        WHERE task_id=@task_id;

        UPDATE ACCOUNT 
        SET coin = coin - @new_offer
        WHERE username = @client_id

        UPDATE ACCOUNT 
        SET coin = coin + @new_offer
        WHERE username = 'admin'

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