
const accountRoutes = require('./account')

function assignRoutes(app){
    accountRoutes.assignRoutes(app)
}

module.exports = {
    assignRoutes
}