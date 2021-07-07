const counterOffer = require('../models/counterOffer')

function postOffer(req,res) {
    console.log("Make counter-offer")
    data = {
        username: req.payload.username,
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

module.exports = {
    postOffer
}