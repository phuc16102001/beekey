const task = require('../controller/task')

function assignRoutes(app) {
   app.post("/task/getByCategory",task.getTaskByCategory)
   app.post("/task/post",task.postTask)
   app.get("/task/getMyTask",task.getTaskByUsername)
   app.get("/task/getMyRequest",task.getRequestByUsername)
   app.post("/task/done",task.doneTask)
}

module.exports = {
    assignRoutes
}