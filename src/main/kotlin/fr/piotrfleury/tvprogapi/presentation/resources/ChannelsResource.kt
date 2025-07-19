package fr.piotrfleury.tvprogapi.presentation.resources

import fr.piotrfleury.tvprogapi.domain.entities.ChannelEntity
import fr.piotrfleury.tvprogapi.domain.entities.ChannelPackageEntity
import fr.piotrfleury.tvprogapi.domain.repositories.ChannelRepository
import fr.piotrfleury.tvprogapi.presentation.dtos.ChannelPackageDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Channels")
@RestController
@RequestMapping("/channels")
class ChannelsResource(
    val channelRepository: ChannelRepository
) {

    @GetMapping("/{channelPackage}")
    fun listChannels(
        @PathVariable("channelPackage", required = true)
        channelPackage: ChannelPackageDto
    ): List<ChannelEntity> {
        return channelRepository.getAllChannels(ChannelPackageEntity.fromString(channelPackage.name))
    }
}