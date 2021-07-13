const counterOffer = require('../models/counterOffer')
const task = require("../models/task")
const config = require("../config/config")

function postOffer(req,res) {
    console.log("Make counter-offer")
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

        if (result) {
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
        }
    })

}

function getByRequest(req,res) {
    console.log("Get counter-offer by request")
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
    console.log("Accept counter-offer")
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

        if (result) {
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
        }
    })
}

function decline(req,res){
    console.log("Decline counter-offer")

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

        if (result) {
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
        }
    })

}

module.exports = {
    postOffer,
    getByRequest,
    accept,
    decline
}