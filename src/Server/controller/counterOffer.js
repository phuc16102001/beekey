const counterOffer = require('../models/counterOffer')
const task = require("../models/task")
const config = require("../config/config")

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

function accept(req,res){
    data = {
        task_id: req.body.task_id,
        lancer_id: req.body.lancer_id
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
        } else {
            res.send({
                exitcode: 1,
                message: "Task not found"
            })
        }
    })
}

function decline(req,res){
    data = {
        task_id: req.body.task_id,
        lancer_id: req.body.lancer_id
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
                counterOffer.decline(data,(err,result)=>{
                    if (err) {
                        res.send({
                            exitcode: 1,
                            message: err
                        })
                    }
            
                    if (result) {
                        res.send({
                            exitcode: 0,
                            message: "Decline counter-offer successfully"
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

module.exports = {
    postOffer,
    getByRequest,
    accept,
    decline
}