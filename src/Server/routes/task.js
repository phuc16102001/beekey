const task = require('../controller/task')

function assignRoutes(app) {
   app.post("/task/getByCategory",task.getTaskByCategory)
}

module.exports = {
    assignRoutes
}