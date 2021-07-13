const Feedback = require('../models/feedback')

function post(req,res) {
    console.log("Make feedback")

    data = {
        user_id: req.payload.username,
        task_id: req.body.task_id,
        title: req.body.title,
        description: req.body.description
    }

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

function get(req,res) {
    console.log("Get feedback")
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