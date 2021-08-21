const counterOffer = require('../models/counterOffer')
const task = require("../models/task")
const config = require("../config/config")
const Account = require('../models/account')

function postOffer(req,res) {
    data = {
        task_id: req.body.task_id,
        lancer_id: req.payload.username,
        reason: req.body.reason,
        offer: req.body.offer
    }

    task.getStatus(data,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
            return
        }

        if (result[0]!=undefined) {
            if (result[0].status!=config.constant.STATUS.PENDING) {
                res.send({
                    exitcode: 4,
                    message: "Task status not valid"
                })
                return;
            } else {       
                counterOffer.postOffer(data,(err,result)=>{
                    if (err) {
                        res.send({
                            exitcode: 1,
                            message: err
                        })
                    }

                    if (result) {
                        res.send({
                            exitcode: 0,
                            message: "Create counter-offer successfully"
                        })
                    }
                })
            }
        } else {
            res.send({
                exitcode: 1,
                message: "Task not found"
            })
        }
    })

}

function getByRequest(req,res) {
    data = {
        task_id: req.body.task_id
    }

    counterOffer.getByRequest(data,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
        }

        if (result) {
            res.send({
                exitcode: 0,
                message: "Get counter-offer by request successfully",
                offers: result
            })
        }
    })
}

function getMoney(username) {
    data = {
        username: username
    }
    Account.getMoney(data,(err,result)=>{
        if (err || result[0]!=undefined) {
            return null;
        }
        result = result[0]
        coin = result.coin
        return coin
    })
}

function accept(req,res){
    data = {
        client_id: req.payload.username,
        task_id: req.body.task_id,
        lancer_id: req.body.lancer_id
    }

    task.viewDetail(data,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
            return
        }

        if (result[0]!=undefined) {
            currentTask = result[0]
            if (currentTask.status!=config.constant.STATUS.PENDING) {
                res.send({
                    exitcode: 4,
                    message: "Task status not valid"
                })
                return;
            } else {
                coin = getMoney(data.client_id)
                if (coin<currentTask.offer) {
                    res.send({
                        exitcode: 105,
                        message: "You do not have enough money"
                    })
                } else {
                    counterOffer.accept(data,(err,result)=>{
                        if (err) {
                            res.send({
                                exitcode: 1,
                                message: err
                            })
                        }
                
                        if (result) {
                            res.send({
                                exitcode: 0,
                                message: "Accept counter-offer successfully"
                            })
                        }
                    })
                }
            }
        } else {
            res.send({
                exitcode: 1,
                message: "Task not found"
            })
        }
    })
}

module.exports = {
    postOffer,
    getByRequest,
    accept
}