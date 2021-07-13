const jwt = require('jsonwebtoken')
const Account = require('../models/account')
const config = require('../config/config')

function login(req,res){
    console.log("Login")
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
            account = account[0]
            payload = {
                username: account.username,
            }
            res.send({
                exitcode: 0,
                token: jwt.sign(payload,config.server.secret,{
                    expiresIn: config.server.expTime
                }),
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

function signup(req,res){
    console.log("Signup")
    data = {
        username: req.body.username,
        password: req.body.password,
        phone: req.body.phone,
        email: req.body.email,
        name: req.body.name
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

function getInformation(req,res){
    console.log("Get information")
    data = {
        username: req.payload.username
    }

    Account.getInformation(data,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
        }

        if (result && result.length>0) {
            account = result[0]
            res.send({
                exitcode: 0,
                message: "Successfully get information",
                username: account.username,
                name: account.name,
                phone: account.phone,
                email: account.email,
                coin: account.coin
            })
        } else {
            res.send({
                exitcode: 1,
                message: 'Failed to get information'
            })
        }
    })
}

function changePassword(req,res) {
    console.log("Change password")
    data = {
        username: req.payload.username,
        newPassword: req.body.newPassword,
        oldPassword: req.body.oldPassword
    }
    Account.getPassword(data,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: res
            })
        }
        
        if (result && result.length>0) {
            oldPassword = result[0].password
            if (oldPassword!=data.oldPassword) {
                res.send({
                    exitcode: 104,
                    message: "Old password not correct"
                })
                return;
            }

            Account.changePassword(data,(err,result)=>{
                if (err) {
                    res.send({
                        exitcode: 1,
                        message: err
                    })
                }

                if (result) {
                    res.send({
                        exitcode: 0,
                        message: "Change password successfully"
                    })
                }
            })
        } else {
            res.send({
                exitcode: 3,
                message: "Username not found"
            })
        }
    })
}

function changeInformation(req,res) {
    console.log("Change information")
    changes = {
        phone: req.body.phone,
        name: req.body.name,
    }
    Object.keys(changes).forEach(key=>{
        if (changes[key]===undefined) {
            delete changes[key]
        }
    })
    data = {
        changes,
        username: req.payload.username
    }
    Account.changeInformation(data,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
            return;
        }

        affectedRows = result.affectedRows
        if (affectedRows!=null && affectedRows>0){
            res.send({
                exitcode: 0,
                message: "Change successfully"
            })
        } else {
            res.send({
                exitcode: 3,
                message: "Username not found"
            })
        }
    });
}

function topUp(req,res){
    console.log("Top up")
    data = {
        username: req.payload.username,
        topUpValue: req.body.topUpValue
    }
    Account.topUp(data,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
        }

        if (result) {
            res.send({
                exitcode: 0,
                message: "Top-up successfully"
            })
        }
    })
}

module.exports = {
    login,
    signup,
    getInformation,
    changePassword,
    changeInformation,
    topUp
}