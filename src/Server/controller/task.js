const Task = require('../models/task')
const config = require('../config/config')

function getTaskByCategory(req,res) {
    console.log("Get task by category")
    data = {
        category_id: req.body.category_id
    }

    Task.getByCategory(data,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
        }

        if (result) {
            res.send({
                exitcode: 0,
                message: "Get task by category successfully",
                tasks: result
            })
        }
    })
}

function getRequestByUsername(req,res) {
    console.log("Get request by username")
    data = {
        username: req.payload.username
    }

    Task.getRequestByUsername(data,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
        }

        if (result) {
            res.send({
                exitcode: 0,
                message: "Get request by username successfully",
                tasks: result
            })
        }
    })
}

function getTaskByUsername(req,res){
    console.log("Get task by username")
    data = {
        username: req.payload.username
    }

    Task.getTaskByUsername(data,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
        }

        if (result) {
            res.send({
                exitcode: 0,
                message: "Get task by username successfully",
                tasks: result
            })
        }
    })
}

function postTask(req,res){
    console.log("Make a new request")
    data = {
        title: req.body.title,
        description: req.body.description,
        offer: req.body.offer,
        deadline: req.body.deadline,
        category_id: req.body.category_id,
        user_id: req.payload.username,
        lancer_id: null,
    }

    Task.postTask(data,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
        }

        if (result) {
            res.send({
                exitcode: 0,
                message: "Create task successfully"
            })
        }
    })
}

function doneTask(req,res) {
    console.log("Done task")

    data = {
        task_id: req.body.task_id
    }

    Task.getStatus(data,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
            return
        }

        if (result[0]!=undefined) {
            if (result[0].status!=config.constant.STATUS.ACCEPTED) {
                res.send({
                    exitcode: 4,
                    message: "Task status not valid"
                })
                return;
            } else {
                Task.done(data,(err,result)=>{
                    if (err) {
                        res.send({
                            exitcode: 1,
                            message: err
                        })
                        return
                    }
                    
                    if (result) {
                        res.send({
                            exitcode: 0,
                            message: "Done task successfully"
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
    getTaskByCategory,
    getRequestByUsername,
    getTaskByUsername,
    postTask,
    doneTask
}