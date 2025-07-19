package fr.piotrfleury.tvprogapi.domain.repositories

import fr.piotrfleury.tvprogapi.domain.entities.ProgramEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProgramRepository {

    fun savePrograms(programs: List<ProgramEntity>)

    fun getChannelPrograms(channelId: String, pageable: Pageable): Page<ProgramEntity>
    fun getCurrentProgram(channelId: String): ProgramEntity?
    fun getTonightProgram(channelId: String): ProgramEntity?
}