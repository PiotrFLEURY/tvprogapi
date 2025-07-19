package fr.piotrfleury.tvprogapi.data.repositories

import fr.piotrfleury.tvprogapi.data.models.psql.ChannelPackageTable
import fr.piotrfleury.tvprogapi.data.models.psql.ChannelTable
import fr.piotrfleury.tvprogapi.data.sources.postgre.ChannelCrudRepository
import fr.piotrfleury.tvprogapi.data.sources.postgre.ChannelPackageCrudRepository
import fr.piotrfleury.tvprogapi.domain.entities.ChannelEntity
import fr.piotrfleury.tvprogapi.domain.entities.ChannelPackageEntity
import fr.piotrfleury.tvprogapi.domain.repositories.ChannelRepository
import org.springframework.stereotype.Service

@Service
class ChannelDatabaseRepository(
    val channelCrudRepository: ChannelCrudRepository,
    val channelPackageCrudRepository: ChannelPackageCrudRepository
) : ChannelRepository {

    override fun saveChannels(channelPackage: ChannelPackageEntity ,channels: List<ChannelEntity>) {

        val existingChannels = channelCrudRepository.findAll()
        val existingChannelIds = existingChannels.map { it.channelId }.toSet()

        channelCrudRepository.saveAll(
            channels
                .filter { it.channelId !in existingChannelIds }
                .map { channel ->
                ChannelTable(
                    channelId = channel.channelId,
                    displayName = channel.name,
                    icon = channel.iconUrl
                )
            }
        )

        channelPackageCrudRepository.deleteByPackageId(channelPackage.name)
        channelPackageCrudRepository.saveAll(
            channels.map { channel ->
                ChannelPackageTable(
                    packageId = channelPackage.name,
                    channelId = channel.channelId
                )
            }
        )
    }

    override fun getAllChannels(channelPackageEntity: ChannelPackageEntity): List<ChannelEntity> {
        return when (channelPackageEntity) {
            ChannelPackageEntity.ALL -> channelCrudRepository.findAll()
            else -> channelCrudRepository.findByPackageLike(channelPackageEntity.name)
        }.map { channelTable ->
            ChannelEntity(
                id = channelTable.id!!,
                channelId = channelTable.channelId,
                name = channelTable.displayName,
                iconUrl = channelTable.icon
            )
        }
    }
}