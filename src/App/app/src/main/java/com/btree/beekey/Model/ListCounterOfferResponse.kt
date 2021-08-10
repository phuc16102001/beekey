package com.btree.beekey.Model

import com.btree.beekey.Controller.Adapter.CounterOffer
import com.google.gson.annotations.SerializedName

class ListCounterOfferResponse (
    @SerializedName("exitcode") val exitcode :Int,
    @SerializedName("message") val message:String,
    @SerializedName("offers") val offers:List<CounterOffer>
)

