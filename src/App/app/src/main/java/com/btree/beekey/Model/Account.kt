package com.btree.beekey.Model


class Account (
    val username: String,
    val displayName: String,
    val phone: String,
    val email: String,
    val coin: Int = 0
) {
    override fun toString(): String {
        return "${username},${displayName},${phone},${email},${coin}"
    }
}