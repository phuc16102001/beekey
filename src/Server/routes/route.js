
const accountRoute = require('./account')

function assignRoutes(app){
    accountRoute.assignRoutes(app)
}

module.exports = {
    assignRoutes
}