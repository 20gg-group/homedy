package gggroup.com.baron.entities

data class DetailPost(
        var post : OverviewPost?,
        var user : User?,
        var address: AddressPost?,
        var images_url: MutableList<ImagesUrlDetailPost>?
)