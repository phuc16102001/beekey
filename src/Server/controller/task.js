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

function getTaskByUsername(req,res) {

}

function getTaskByLancer(req,res){

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
    getTaskByUsername,
    getTaskByLancer,
    postTask
}