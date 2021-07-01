const task = require('../controller/task')

function assignRoutes(app) {
   app.post("/task/getByCategory",task.getTaskByCategory)
   app.post("/task/post",task.postTask)
}

module.exports = {
    assignRoutes
}