const sql = require('./db')
const config = require('../config/config')

const Account = function(){};

Account.signup = function(data,resultCallback) {
    data.coin = config.constant.DEFAULT_COIN
    sql.query("INSERT INTO ACCOUNT SET ?",data,function(err,res){
        if (err) {
            console.log("Fail to create: ",err);
            resultCallback(err,null);
            return;
        }

        resultCallback(null,data)
    });
}

Account.login = function(data,resultCallback){
    sql.query("SELECT * FROM ACCOUNT WHERE USERNAME=? AND PASSWORD=?",[data.username,data.password],function(err,res){
        if (err){
            console.log("Fail to login: ",err)
            resultCallback(err,null)
            return;
        }

        resultCallback(null,res)
    });
}

Account.getInformation = function(data,resultCallback) {
    sql.query("SELECT * FROM ACCOUNT WHERE username=?",[data.username],(err,res)=>{
        if (err) {
            console.log("Fail to get information: ",err)
            resultCallback(err,null)
            return;
        }

        resultCallback(null,res)
    })
}

Account.getPassword = function(data,resultCallback){
    sql.query("SELECT password FROM ACCOUNT WHERE username=?",[data.username],(err,res)=>{
        if (err){
            console.log("Fail to get password: ",err)
            resultCallback(err,null)
            return;
        }

        resultCallback(null,res)
    })
}

Account.changePassword = function(data,resultCallback) {
    sql.query("UPDATE ACCOUNT SET password=? WHERE username=?",[data.newPassword,data.username],(err,res)=>{
        if (err) {
            console.log("Fail to change password: ",err)
            resultCallback(err,null)
            return;
        }

        resultCallback(null,res)
    })
}

Account.changeInformation = function(data,resultCallback){
    sql.query(
        "UPDATE ACCOUNT SET ? WHERE username=?",
        [data.changes,data.username],
        (err,res)=>{
            if (err) {
                resultCallback(err,null)
                return
            }

            resultCallback(null,res)
        })
}

Account.topUp = function(data,resultCallback) {
    sql.query(
        "UPDATE ACCOUNT SET coin=coin+? WHERE username=?",
        [data.topUpValue,data.username],
        (err,res)=>{
            if (err) {
                resultCallback(err,null)
                return
            }

            resultCallback(null,res)
        }
    )
}

module.exports = Account