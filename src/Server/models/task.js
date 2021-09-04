const   sql = require('./db')
        config = require("../config/config")

const Task = function(){};

Task.getStatus = function(data,resultCallback) {
    sql.query("SELECT status FROM TASK WHERE task_id=?",
        data.task_id,
        (err,res)=>{
            if (err) {
                console.log("Fail to get: ",err);
                resultCallback(err,null);
                return;
            }

            resultCallback(null,res)
        }
    )
}

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
        }
    )
}

Task.postTask = function(data,resultCallback) {
    data["status"]=config.constant.STATUS.PENDING
    sql.query("INSERT INTO TASK SET ?",
        data,
        (err,res)=>{
            if (err) {
                console.log("Fail to create task: ",err)
                resultCallback(err,null);
                return;
            }

            resultCallback(null,res);
        }
    )
}

Task.getRequestByUsername = function(data,resultCallback) {
    sql.query("SELECT * FROM TASK WHERE user_id=? ORDER BY status desc",
        data.username,
        (err,res)=>{
            if (err) {
                console.log("Fail to get request: ",err)
                resultCallback(err,null)
                return;
            }

            resultCallback(null,res)
        }
    )
}

Task.getTaskByUsername = function(data,resultCallback) {
    sql.query("SELECT * FROM TASK WHERE lancer_id=? ORDER BY status desc",
        data.username,
        (err,res)=>{
            if (err) {
                console.log("Fail to get request: ",err)
                resultCallback(err,null)
                return;
            }

            resultCallback(null,res)
        }
    )
}

Task.done = function(data,resultCallback) {
    values = [data.task_id, config.constant.STATUS.DONE]
    let sqlString = `
        start transaction;

        set @task_id = ?;
        set @done_status = ?;
        set @offer = (
            SELECT offer 
            FROM TASK
            WHERE task_id=@task_id
        );
        set @lancer_id = (
            SELECT lancer_id
            FROM TASK
            WHERE task_id=@task_id
        );

        UPDATE TASK
        SET status = @done_status
        WHERE task_id=@task_id;

        UPDATE ACCOUNT
        SET coin = coin - @offer
        WHERE username = 'admin';

        UPDATE ACCOUNT
        SET coin = coin + @offer
        WHERE username = @lancer_id;

        commit;
    `

    sql.query(sqlString, values,(err,res)=>{
            if (err) {
                console.log("Fail to set status: ",err)
                resultCallback(err,null)
                return;
            }

            resultCallback(null,res)
        }
    )
}

Task.viewDetail = function(data,resultCallback) {
    sql.query("SELECT * FROM TASK WHERE task_id=?",
        [data.task_id],
        (err,res)=>{
            if (err) {
                console.log("Fail to get task detail: ",err)
                resultCallback(err,null)
                return;
            }

            resultCallback(null,res)
        }
    )
}

module.exports = Task