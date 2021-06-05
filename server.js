var http = require('http')

function handler(req,res){
    switch (req.url){
        case ('/'): {
            res.writeHead(200,{ 'Content-Type': 'text/html' });
            res.write('Yeu Nhu Thanh nhieu')
            res.end();
            break;
        }
    }
}

var server = http.createServer(handler);
server.listen(8080);
console.log("Begin listen on 8080...");