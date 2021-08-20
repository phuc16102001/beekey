const counterOffer = require('../controller/counterOffer')

function assignRoutes(app) {
   app.post("/counterOffer/post",counterOffer.postOffer)
   app.post("/counterOffer/getByRequest",counterOffer.getByRequest)
   app.post("/counterOffer/accept",counterOffer.accept)
}

module.exports = {
    assignRoutes
}