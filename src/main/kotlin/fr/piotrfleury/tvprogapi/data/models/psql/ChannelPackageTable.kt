package fr.piotrfleury.tvprogapi.data.models.psql

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "channel_packages")
data class ChannelPackageTable(
    @Id
    var id: Int? = null,
    var packageId: String,
    var channelId: String
)
