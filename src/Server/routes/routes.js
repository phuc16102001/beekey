const accountRoutes = require('./account')
const taskRoutes = require('./task')
const categoryRoutes = require('./category')
const counterOfferRoutes = require('./counterOffer')
const reportRoutes = require('./report')
const feedbackRoutes = require('./feedback')
const chatRoutes = require('./chat')

function assignRoutes(app){
    accountRoutes.assignRoutes(app)
    taskRoutes.assignRoutes(app)
    categoryRoutes.assignRoutes(app)
    counterOfferRoutes.assignRoutes(app)
    reportRoutes.assignRoutes(app)
    feedbackRoutes.assignRoutes(app)
    chatRoutes.assignRoutes(app)
}

module.exports = {
    assignRoutes
}