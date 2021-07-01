const category = require('../controller/category')

function assignRoutes(app) {
   app.get("/category",category.getCategory)
}

module.exports = {
    assignRoutes
}