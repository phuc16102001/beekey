exports.server = {
    port: 8080,
    noTokenUrl: ['/account/login','/account/signup'],
    expTime: 60*60*24,
    secret: 'BTree'
}

exports.database = {
    host: 'localhost',
    username: 'postgres',
    password: 'Phuc16102001',
    name: 'BeeKey'
}