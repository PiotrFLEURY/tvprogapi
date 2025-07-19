package fr.piotrfleury.tvprogapi.domain.entities

data class ChannelEntity(
    val id: Integer? = null,
    val channelId: String,
    val name: String,
    val iconUrl: String? = null
)
