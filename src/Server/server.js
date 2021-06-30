const   express = require('express')
        app = express()
        route = require('./routes/routes')
        jwt = require('jsonwebtoken')
        config = require('./config/config')
        cors = require('cors')

//==================== Library =======================

//#region middleware

app.use(cors())
app.use(express.json())
app.use(express.urlencoded({
    extended:true
}));

app.use(function(req,res,next){
    if (config.server.noTokenUrl.indexOf(req.url)==-1){
        //In token url
        const token = req.headers['x-access-token']

        jwt.verify(token,config.server.secret,(err,decoded)=>{
            if (err) {
                res.status(403);
                res.send({
                    exitcode: 2,
                    message: err
                })
                return
            }
            
            req.payload = {
                username: decoded.username
            }
            next()
        })
    } 
    else {
        //Non-token url
        next()
    }
})

//#endregion middleware

//Bind route
route.assignRoutes(app)

//Start listen
app.listen(config.server.port,function(){
    console.log("Begin listen on port %s...",config.server.port);
})
