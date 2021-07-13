const counterOffer = require('../controller/counterOffer')

function assignRoutes(app) {
   app.post("/counterOffer/post",counterOffer.postOffer)
   app.post("/counterOffer/getByRequest",counterOffer.getByRequest)
}

module.exports = {
    assignRoutes
}