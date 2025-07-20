package fr.piotrfleury.tvprogapi.domain.usecases

import fr.piotrfleury.tvprogapi.data.models.xmltvfr.Tv
import fr.piotrfleury.tvprogapi.domain.entities.ChannelEntity
import fr.piotrfleury.tvprogapi.domain.entities.ChannelPackageEntity
import fr.piotrfleury.tvprogapi.domain.entities.ProgramEntity
import fr.piotrfleury.tvprogapi.domain.entities.RatingEntity
import fr.piotrfleury.tvprogapi.domain.entities.TvProgApiConfig
import fr.piotrfleury.tvprogapi.domain.repositories.ChannelRepository
import fr.piotrfleury.tvprogapi.domain.repositories.ProgramRepository
import fr.piotrfleury.tvprogapi.domain.repositories.XmlTvRepository
import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service


@Service
class FetchXmlTv(
    val xmlTvFrRepository: XmlTvRepository,
    val channelRepository: ChannelRepository,
    val programRepository: ProgramRepository
) {

    val logger: Logger = LoggerFactory.getLogger(FetchXmlTv::class.java)

    @PostConstruct
    private fun init() {
        // Pre-fetch all channels and programs for the default package
        invoke(ChannelPackageEntity.FR)
        invoke(ChannelPackageEntity.TNT)
    }

    private fun fetchPrograms(channelPackageEntity: ChannelPackageEntity): Tv {
        return when (channelPackageEntity) {
            ChannelPackageEntity.TNT -> xmlTvFrRepository.fetchXmlTvTnt()
            ChannelPackageEntity.FR -> xmlTvFrRepository.fetchXmlTvFr()
            ChannelPackageEntity.ALL -> xmlTvFrRepository.fetchXmlTv()
        }
    }

    operator fun invoke(channelPackageEntity: ChannelPackageEntity) {
        logger.info("Fetching XML TV data for package: ${channelPackageEntity.name}")
        val xmlTv = fetchPrograms(channelPackageEntity)
        channelRepository.saveChannels(
            channelPackageEntity, xmlTv.channels
                .map { channel ->
                    ChannelEntity(
                        channelId = channel.channelId,
                        name = channel.displayName,
                        iconUrl = channel.icon ?: ""
                    )
                })
        programRepository.savePrograms(xmlTv.programs.map { program ->
            ProgramEntity(
                startTime = program.start,
                endTime = program.stop,
                channelId = program.channel.channelId,
                title = program.title,
                subtitle = program.subtitle ?: "",
                description = program.description ?: "",
                categories = program.categories,
                iconUrl = program.icon ?: "",
                episodeNum = program.episodeNum ?: "",
                rating = program.rating?.let {
                    RatingEntity(
                        system = it.system,
                        value = it.value,
                        icon = it.icon
                    )
                }
            )
        })
    }
}