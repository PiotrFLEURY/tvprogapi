package fr.piotrfleury.tvprogapi.data.repositories

import fr.piotrfleury.tvprogapi.data.converters.ProgramConverter
import fr.piotrfleury.tvprogapi.data.sources.postgre.ProgramCrudRepository
import fr.piotrfleury.tvprogapi.domain.entities.ProgramEntity
import fr.piotrfleury.tvprogapi.domain.repositories.ProgramRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.sql.Date
import java.sql.Timestamp
import java.util.*

@Service
class ProgramDatabaseRepository(
    private val programCrudRepository: ProgramCrudRepository,
    private val programConverter: ProgramConverter
) : ProgramRepository {

    private val logger = LoggerFactory.getLogger(ProgramDatabaseRepository::class.java)

    override fun savePrograms(programs: List<ProgramEntity>) {
        programCrudRepository.deleteByChannelIdIn(
            programs.map { it.channelId }.distinct()
        )
        programCrudRepository.saveAll(programs.map(programConverter::entityToTable))
    }

    override fun getChannelPrograms(channelId: String, pageable: Pageable): Page<ProgramEntity> {
        return programCrudRepository
            .findByChannelIdAndStartTimeGreaterThan(
                channelId,
                Date(System.currentTimeMillis()),
                pageable
            )
            .map(programConverter::tableToEntity)
    }

    override fun getCurrentProgram(channelId: String): ProgramEntity? {
        val currentProgram = programCrudRepository.findCurrentByChannelId(channelId)
        return programConverter.tableToEntity(currentProgram)
    }

    override fun getTonightProgram(channelId: String): ProgramEntity? {
        val now = Calendar.getInstance()
        now.set(Calendar.HOUR_OF_DAY, 20)
        now.set(Calendar.MINUTE, 30)
        val tonight = Timestamp(now.timeInMillis)
        val tonightProgram = programCrudRepository.findTonightByChannelId(channelId, tonight)
        return programConverter.tableToEntity(tonightProgram)
    }
}