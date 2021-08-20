const Feedback = require('../models/feedback')
const Task = require('../models/task')
const config = require('../config/config')

function post(req,res) {
    data = {
        user_id: req.payload.username,
        task_id: req.body.task_id,
        title: req.body.title,
        description: req.body.description
    }

    console.log(data.task_id)

    Task.getStatus(data,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
            return
        }

        if (result[0]!=undefined) {
            console.log(result)
            if (int(result.status)!=config.constant.STATUS.DONE) {
                res.send({
                    exitcode: 4,
                    message: "Task status not valid"
                })
            } else {
                Feedback.post(data,(err,result)=>{
                    if (err) {
                        res.send({
                            exitcode: 1,
                            message: err
                        })
                    }
            
                    if (result){
                        res.send({
                            exitcode: 0,
                            message: "Create feedback successfully"
                        })
                    } else {
                        res.send({
                            exitcode: 1,
                            message: "Cannot create feedback"
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

function get(req,res) {
    data = {
        username: req.payload.username
    }
    Feedback.get((err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
        }

        if (result) {
            res.send({
                exitcode: 0,
                message: "Get all feedback successfully",
                feedbacks: result
            })
        }
    })

}

module.exports = {
    post,
    get
}