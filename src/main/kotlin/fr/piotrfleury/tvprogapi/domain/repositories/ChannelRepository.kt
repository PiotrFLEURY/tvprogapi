package fr.piotrfleury.tvprogapi.domain.repositories

import fr.piotrfleury.tvprogapi.domain.entities.ChannelEntity
import fr.piotrfleury.tvprogapi.domain.entities.ChannelPackageEntity

interface ChannelRepository {

    fun getAllChannels(channelPackageEntity: ChannelPackageEntity): List<ChannelEntity>
    fun saveChannels(channelPackage: ChannelPackageEntity, channels: List<ChannelEntity>)
}