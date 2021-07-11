package com.btree.beekey.Controller.Adapter

import com.google.gson.annotations.SerializedName

class Category(
    @SerializedName("category_id") var categoryId: Int,
    @SerializedName("category_name") var categoryName: String,
) {
    override fun toString(): String {
        return categoryName
    }
}

