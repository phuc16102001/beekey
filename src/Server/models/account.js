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

Account.create = function(resultCallback) {
    sql.query("INSERT INTO ACCOUNT SET ?",newAccount,function(err,res){
        if (err) {
            console.log("Fail to create: ",err);
            resultCallback(err,null);
        }

        console.log("Created account: ",{id:res.insertedID,...newAccount});
        resultCallback(null,{id:res.insertedID,...newAccount})
    });
}

Account.login = function(data,resultCallback){
    sql.query("SELECT * FROM ACCOUNT WHERE USERNAME=? AND PASSWORD=?",[data.username,data.password],function(err,res){
        if (err){
            console.log("Fail to login: ",err)
            resultCallback(err,null)
        }

        console.log("Login account: ",res)
        resultCallback(null,res)
    });
}

Account.getAll = function(resultCallback){
    sql.query("SELECT * FROM ACCOUNT",function(err,res){
        if (err) {
            console.log("Fail to get account: ",err);
            resultCallback(err,null);
        }

        console.log("Get account");
        resultCallback(null,res)
    });
}

module.exports = Account