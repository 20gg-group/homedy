package gggroup.com.baron.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User (
        @SerializedName("id")
        @Expose
        var id: Int?,

        @SerializedName("username")
        @Expose
        var username: String?,

        @SerializedName("phone")
        @Expose
        var email: String?,

        @SerializedName("email")
        @Expose
        var phone:String?,

        @SerializedName("password")
        @Expose
        var password: String?,

        @SerializedName("role")
        @Expose
        var role: String?,

        @SerializedName("created_at")
        @Expose
        var createdAt : String?,

        @SerializedName("updated_at")
        @Expose
        var updatedAt : String?
)
