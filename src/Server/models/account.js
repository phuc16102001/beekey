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

Account.signup = function(account,resultCallback) {
    account.type = accountType.user
    account.coin = accountCoinDefault
    sql.query("INSERT INTO ACCOUNT SET ?",account,function(err,res){
        if (err) {
            console.log("Fail to create: ",err);
            resultCallback(err,null);
            return;
        }

        resultCallback(null,account)
    });
}

Account.login = function(account,resultCallback){
    sql.query("SELECT * FROM ACCOUNT WHERE USERNAME=? AND PASSWORD=?",[account.username,account.password],function(err,res){
        if (err){
            console.log("Fail to login: ",err)
            resultCallback(err,null)
            return;
        }

        resultCallback(null,res)
    });
}

Account.getInformation = function(account,resultCallback) {
    sql.query("SELECT * FROM ACCOUNT WHERE USERNAME=?",[account.username],(err,res)=>{
        if (err) {
            console.log("Fail to get information: ",err)
            resultCallback(err,null)
            return;
        }

        resultCallback(null,res)
    })
}

module.exports = Account