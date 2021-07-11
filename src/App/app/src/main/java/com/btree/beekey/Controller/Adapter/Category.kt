package com.btree.beekey.Controller.Adapter

class Category(
    var categoryId: Int,
    var categoryName: String,
) {
    override fun toString(): String {
        return categoryName
    }
}

