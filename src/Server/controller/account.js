const   jwt = require('jsonwebtoken')
        Account = require('../models/account')
        config = require('../config/config')

function login(req,res,next){
    data = {
        username: req.body.username,
        password: req.body.password,
    }
    Account.login(data,(err,account)=>{
        if (err) {
            res.send({
                exitcode: 1,
                token: '',
                message: err
            })
        }

        if (account.length>0){
            payload = {
                username: account.username,
                type: account.type
            }
            res.send({
                exitcode: 0,
                token: jwt.sign(payload,config.server.secret),
                message: "Login successfully"
            })
        } else {
            res.send({
                exitcode: 104,
                token: '',
                message: "Incorrect password or username"
            })
        }
    })
}

function signup(req,res,next){
    data = {
        username: req.body.username,
        password: req.body.password,
    }

    Account.signup(data,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
        }

        if (result) {
            res.send({
                exitcode: 0,
                message: "Create account successfully"
            })
        }
    })
}

function getInformation(req,res,next){
}

module.exports = {
    login,
    signup,
    getInformation
}