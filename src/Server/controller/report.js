const config = require('../config/config')
const Report = require('../models/report')

function post(req,res) {
    console.log("Make report")
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
    console.log("Get report")
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
}

module.exports = {
    post,
    get
}