const sql = require('./../db')

const Account = function(account) {
    this.username = account.username;
    this.password = account.password;
    this.phone = account.phone;
    this.address = account.address;
    this.type = account.type;
    this.coin = account.coin;
}

Account.create = function(newAccount,result) {
    sql.query("INSERT INTO ACCOUNT SET ?",newAccount,function(err,res){
        if (err) {
            console.log("Fail to create: ",err);
            result(err,null);
        }

        console.log("Created account: ",{id:res.insertedID,...newAccount});
        result(null,{id:res.insertedID,...newAccount})
    });
}

Account.getAll = function(result){
    sql.query("SELECT * FROM ACCOUNT",function(err,res){
        if (err) {
            console.log("Fail to get account: ",err);
            result(err,null);
        }

        console.log("Get account");
        result(null,res)
    });
}

module.exports = Account