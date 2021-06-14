const usecase = require('../use-case/account')

function assignRoutes(app) {
    app.post('/account/login',usecase.login);
    app.post('/account/signup',usecase.signup);
    app.get('/account/getInformation',usecase.getInformation);
}

module.exports = assignRoutes