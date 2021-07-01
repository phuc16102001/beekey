const Task = require('../models/task')

function getTaskByCategory(req,res) {
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

module.exports = {
    getTaskByCategory,
    getRequestByUsername,
    getTaskByUsername,
    postTask
}