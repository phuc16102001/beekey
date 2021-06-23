const report = require('../controller/report')

function assignRoutes(app) {
   app.post("/report",report.post);
   app.get("/report",report.get);
}

module.exports = {
    assignRoutes
}