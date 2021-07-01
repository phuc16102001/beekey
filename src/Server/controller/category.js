const Category = require('../models/category')

function getCategory(req,res) {
    Category.get(null,(err,result)=>{
        if (err) {
            res.send({
                exitcode: 1,
                message: err
            })
        }

        if (result) {
            res.send({
                exitcode: 0,
                message: "Get categories successfully",
                categories: result
            })
        }
    })
}

module.exports = {
    getCategory
}