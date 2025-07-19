package fr.piotrfleury.tvprogapi.data.models.psql

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name= "channels")
data class ChannelTable(
    @Id
    var id: Integer? = null,
    var channelId: String,
    var displayName: String,
    var icon: String? = null
)