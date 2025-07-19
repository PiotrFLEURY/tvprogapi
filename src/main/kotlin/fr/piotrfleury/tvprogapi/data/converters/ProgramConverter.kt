package fr.piotrfleury.tvprogapi.data.converters

import fr.piotrfleury.tvprogapi.data.models.psql.ProgramTable
import fr.piotrfleury.tvprogapi.domain.entities.ProgramEntity
import fr.piotrfleury.tvprogapi.domain.entities.RatingEntity
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class ProgramConverter {

    fun entityToTable(program: ProgramEntity?): ProgramTable? {
        return program?.let {
            ProgramTable(
                channelId = program.channelId,
                startTime = Timestamp(program.startTime.time),
                endTime = Timestamp(program.endTime.time),
                title = program.title,
                subtitle = program.subtitle,
                description = program.description,
                categories = program.categories.joinToString(","),
                icon = program.iconUrl,
                episodeNum = program.episodeNum,
                ratingIcon = program.rating?.icon,
                ratingValue = program.rating?.value,
                ratingSystem = program.rating?.system,
            )
        }
    }

    fun tableToEntity(programTable: ProgramTable?): ProgramEntity? {
        return programTable?.let {
            ProgramEntity(
                id = programTable.id?.toInt(),
                startTime = programTable.startTime,
                endTime = programTable.endTime,
                channelId = programTable.channelId,
                title = programTable.title,
                subtitle = programTable.subtitle,
                description = programTable.description,
                categories = programTable.categories?.split(",") ?: emptyList(),
                iconUrl = programTable.icon,
                episodeNum = programTable.episodeNum,
                rating = if (programTable.ratingSystem != null &&
                    programTable.ratingValue != null &&
                    programTable.ratingIcon != null) {
                    RatingEntity(
                        system = programTable.ratingSystem,
                        value = programTable.ratingValue,
                        icon = programTable.ratingIcon
                    )
                } else null
            )
        }
    }
}