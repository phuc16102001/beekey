var express = require('express')
var config = require('./config/config')
var db = require('mysql')
var app = express()

const conn = db.createConnection(config.database);
conn.connect(err=>{
    if (err) throw err;
    console.log("Connect successfully to database");
});

app.use(function(req,res,next){
    if (config.server.noTokenUrl.indexOf(req.url)==-1){
        
    }
})

app.get("/",function(req,res){
    res.json({
        username:"phuc16102001",
        password:"20"
    })
    res.end()
})

app.listen(config.server.port,function(){
    console.log("Begin listen on port %s...",config.server.port);
})
