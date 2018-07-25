package gggroup.com.baron.entities

data class Post(
        var user: User?,
        var title: String?,
        var address: String?,
        var price: Double?,
        var type: String?,
        var sex: String?,
        var area: String?,
        var phone: String?,
        var description: String?,
        var image_url: String?
)