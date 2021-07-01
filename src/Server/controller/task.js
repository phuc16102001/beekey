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
    
}

module.exports = {
    getTaskByCategory,
    getTaskByUsername,
    getTaskByLancer,
    postTask
}