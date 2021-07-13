const counterOffer = require('../models/counterOffer')

function postOffer(req,res) {
    console.log("Make counter-offer")
    data = {
        lancer_id: req.payload.username,
        reason: req.body.reason,
        offer: req.body.offer
    }

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
                message: "Get counter-offer by request successfully"
            })
        }
    })

}

module.exports = {
    postOffer,
    getByRequest
}