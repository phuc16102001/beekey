const Account = require('../models/account')

function login(req,res,next){
    data = {
        username: req.body.username,
        password: req.body.password,
    }
    Account.getInformation(data,(err,account)=>{
        if (err) {
            throw err
            res.json({
                exitcode: 404,
                message: err
            })
        }
        
        res.json(account)
        res.end()
    })
}

function signup(req,res,next){

}

function getInformation(req,res,next){
}

module.exports = {
    login,
    signup,
    getInformation
}