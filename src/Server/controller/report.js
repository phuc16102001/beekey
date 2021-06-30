const config = require('../config/config')
const Report = require('../models/report')

function post(req,res) {
    dateTime = new Date()
    data = {
        username: req.payload.username,
        content: req.body.content,
        date_time: dateTime
    }

    Report.post(data,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
        }

        if (result){
            res.send({
                exitcode: 0,
                message: "Create report successfully"
            })
        } else {
            res.send({
                exitcode: 3,
                message: "Username not found"
            })
        }
    })
}

function get(req,res) {
    if (req.payload.type==config.constant.ADMIN_ID){
        Report.get((err,result)=>{
            if (err) {
                res.send({
                    exitcode: 1,
                    message: err
                })
            }

            if (result) {
                res.send({
                    exitcode: 0,
                    message: "Get all report successfully",
                    reports: result
                })
            }
        })
    } else {
        res.send({
            exitcode: 2,
            message: "Please login with admin account"
        })
    }
}

module.exports = {
    post,
    get
}