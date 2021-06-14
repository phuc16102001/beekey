const Account = require('./../entity/account')

function login(req,res,next){
}

function signup(req,res,next){

}

function getInformation(req,res,next){
    Account.getAll((err,account)=>{
        if (err) throw err
        res.json(account)
        res.end()
    })
}

module.exports = {
    login,
    signup,
    getInformation
}