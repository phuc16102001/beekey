const task = require('../controller/task')

function assignRoutes(app) {
   app.assignRoutes("/task/getByCategory",task.getTaskByCategory)
}

module.exports = {
    assignRoutes
}