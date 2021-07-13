const feedback = require('../controller/feedback')

function assignRoutes(app) {
   app.post("/feedback",feedback.post);
   app.get("/feedback",feedback.get);
}

module.exports = {
    assignRoutes
}