const report = require('../controller/report')

function assignRoutes(app) {
   app.post("/report/post",report.post);
}

module.exports = {
    assignRoutes
}