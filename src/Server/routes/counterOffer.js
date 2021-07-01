const counterOffer = require('../controller/counterOffer')

function assignRoutes(app) {
   app.post("/counterOffer/post",counterOffer.postOffer)
}

module.exports = {
    assignRoutes
}