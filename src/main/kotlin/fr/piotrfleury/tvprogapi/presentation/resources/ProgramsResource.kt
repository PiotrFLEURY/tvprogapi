package fr.piotrfleury.tvprogapi.presentation.resources

import fr.piotrfleury.tvprogapi.domain.repositories.ProgramRepository
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Programs", description = "Operations related to TV programs")
@RestController
@RequestMapping("/programs")
class ProgramsResource(
    private val programRepository: ProgramRepository
) {

    @GetMapping
    fun listPrograms(
        @RequestParam("channelId") channelId: String,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int
    ) = programRepository.getChannelPrograms(channelId, PageRequest.of(page, size))

    @GetMapping("/current")
    fun getCurrentProgram(
        @RequestParam("channelId") channelId: String
    ) = programRepository.getCurrentProgram(channelId)

    @GetMapping("/tonight")
    fun getTonightProgram(
        @RequestParam("channelId") channelId: String
    ) = programRepository.getTonightProgram(channelId)
}