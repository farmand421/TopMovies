package com.ramand.topmovies.models.register

import com.google.gson.annotations.SerializedName

data class BodyRegister(
    @SerializedName("email")
    var email: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("password")
    var password: String = ""
)