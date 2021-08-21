const config = require('../config/config')
const Chat = require('../models/chat')

function send(req,res) {
    dateTime = new Date()
    data = {
        send_id: req.payload.username,
        receive_id: req.body.receive_id,
        date_time: dateTime,
        content: req.body.content
    }

    Chat.send(data,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
        }

        if (result){
            res.send({
                exitcode: 0,
                message: "Send message successfully"
            })
        }
    })
}

function fetch(req,res) {
    data = {
        receive_id: req.body.receive_id,
        send_id: req.payload.username
    }

    Chat.fetch(data,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
        }

        if (result) {
            res.send({
                exitcode: 0,
                message: "Fetch all message successfully",
                Chats: result
            })
        }
    })
}

module.exports = {
    send,
    fetch
}