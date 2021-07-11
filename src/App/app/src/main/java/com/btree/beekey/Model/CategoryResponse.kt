package com.btree.beekey.Model

import com.btree.beekey.Controller.Adapter.Category
import com.google.gson.annotations.SerializedName

class CategoryResponse (
    @SerializedName("exitcode") val exitcode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("categories") val categories: List<Category>
)
