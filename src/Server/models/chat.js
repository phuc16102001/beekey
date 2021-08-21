const sql = require('./db')

const Chat = function(){};

Chat.send = function(data,resultCallback) {
    let sqlString = `
        INSERT INTO CHAT
        SET ?;
    `
    sql.query(sqlString,data,function(err,res){
        if (err) {
            console.log("Fail to create: ",err);
            resultCallback(err,null);
            return;
        }

        resultCallback(null,res)
    });
}

Chat.fetch = function(data,resultCallback) {
    values = [data.receive_id, data.send_id, data.receive_id, data.send_id]
    let sqlString = `
        SELECT send_id, receive_id, content, date_time
        FROM CHAT
        WHERE (send_id=? and receive_id=?) OR (receive_id=? and send_id=?)
    `
    sql.query(sqlString,values,function(err,res) {
        if (err) {
            console.log("Fail to get: ",err);
            resultCallback(err,null);
            return;
        }

        resultCallback(null,res)
    })
}

module.exports = Chat