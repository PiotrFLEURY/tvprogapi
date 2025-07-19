package fr.piotrfleury.tvprogapi.data.models.xmltvfr

data class Channel(
    val channelId: String,
    val displayName: String,
    val icon: String? = null,
)