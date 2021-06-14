const controller = require('../controller/account')

function assignRoutes(app) {
    app.post('/account/login',controller.login);
    app.post('/account/signup',controller.signup);
    app.get('/account/getInformation',controller.getInformation);
}

module.exports = {
    assignRoutes
}