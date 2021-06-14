const sql = require('./db')

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
    sql.query("INSERT INTO ACCOUNT SET ?",account,function(err,res){
        if (err) {
            console.log("Fail to create: ",err);
            resultCallback(err,null);
        }

        console.log("Created account: ",{id:res.insertedID});
        resultCallback(null,{id:res.insertedID})
    });
}

Account.login = function(account,resultCallback){
    sql.query("SELECT * FROM ACCOUNT WHERE USERNAME=? AND PASSWORD=?",[account.username,account.password],function(err,res){
        if (err){
            console.log("Fail to login: ",err)
            resultCallback(err,null)
        }

        console.log("Login account: ",res)
        resultCallback(null,res)
    });
}

Account.getInformation = function(account,resultCallback) {
    sql.query("SELECT * FROM ACCOUNT WHERE USERNAME=?",[account.username],(err,res)=>{
        if (err) {
            console.log("Fail to get information: ",err)
            resultCallback(err,null)
        }

        console.log("Get account: ",res)
        resultCallback(null,res)
    })
}

module.exports = Account