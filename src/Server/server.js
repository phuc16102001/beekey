const   express = require('express')
        app = express()
        route = require('./route/route')
        jwt = require('jsonwebtoken')
        config = require('./config/config')

//==================== Library =======================

//#region middleware

app.use(express.json())
app.use(express.urlencoded({
    extended:true
}));

app.use((req,res,next)=>{
    console.log("%s %s",req.method,req.url);
    next()
});

app.use(function(req,res,next){
    if (config.server.noTokenUrl.indexOf(req.url)==-1){
        //In token url
        const token = req.body.token || req.headers['x-access-token']
        if (token){ 
            //Token found
            const decoded = jwt.decode(token);
            if (decoded.exitcode==-1) {
                //Parse failed
                res.json({
                    exitcode: 1,
                    message: 'Fail to parse token'
                })
                res.end()
            } 
            else {
                //Parse successful
                req.payload = decoded.data
                next()
            }
        } 
        else {
            //No token found
            res.status(403);
            res.send({
                exitcode: 0,
                message: "No token"
            })
            res.end()
        }
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
