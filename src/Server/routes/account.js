const account = require('../controller/account')

function assignRoutes(app) {
    app.post('/account/login',account.login);
    app.post('/account/signup',account.signup);
    app.get('/account/getInformation',account.getInformation);
    app.post('/account/changePassword',account.changePassword)
}

module.exports = {
    assignRoutes
}