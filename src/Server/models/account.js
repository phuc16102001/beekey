const sql = require('./db')

const accountType = {
    admin: 0,
    user: 1
}
const accountCoinDefault = 0

const Account = function(account) {
    this.username = account.username;
    this.name = account.name;
    this.password = account.password;
    this.phone = account.phone;
    this.address = account.address;
    this.type = account.type;
    this.coin = account.coin;
}

Account.signup = function(data,resultCallback) {
    data.type = accountType.user
    data.coin = accountCoinDefault
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

Account.getPassword = function(data,resultCallBack){
    sql.query("SELECT password FROM ACCOUNT WHERE username=?",[data.username],(err,res)=>{
        if (err){
            console.log("Fail to get password: ",err)
            resultCallBack(err,null)
            return;
        }

        resultCallBack(null,res)
    })
}

Account.changePassword = function(data,resultCallBack) {
    sql.query("UPDATE ACCOUNT SET password=? WHERE username=?",[data.newPassword,data.username],(err,res)=>{
        if (err) {
            console.log("Fail to change password: ",err)
            resultCallBack(err,null)
            return;
        }

        resultCallBack(null,res)
    })
}

Account.changeInformation = function(data,resultCallBack){
    console.log(data.username)
    console.log(data.changes)
    sql.query(
        "UPDATE ACCOUNT SET ? WHERE username=?",
        [data.changes,data.username],
        (err,res)=>{
            if (err) {
                resultCallBack(err,null)
                return
            }

            resultCallBack(null,res)
        })
}

module.exports = Account