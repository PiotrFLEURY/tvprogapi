package fr.piotrfleury.tvprogapi.data.sources.postgre

import fr.piotrfleury.tvprogapi.data.models.psql.ProgramTable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import java.sql.Date
import java.sql.Timestamp

interface ProgramCrudRepository: CrudRepository<ProgramTable, Integer> {
    fun findByChannelIdAndStartTimeGreaterThanOrderByStartTimeAsc(channelId: String, startTime: Date, pageable: Pageable): Page<ProgramTable>

    @Query("""
        SELECT * FROM programs
        WHERE channel_id = :channelId
        AND start_time <= NOW()
        AND end_time >= NOW()
        LIMIT 1
    """)
    fun findCurrentByChannelId(channelId: String): ProgramTable?

    @Query("""
        SELECT * FROM programs
        WHERE channel_id = :channelId
        AND start_time >= :targetTime
        -- duration is at least 1 hours
        AND (end_time - start_time) >= INTERVAL '1 hour'
        ORDER BY start_time ASC
        LIMIT 1
    """)
    fun findTonightByChannelId(channelId: String, targetTime: Timestamp): ProgramTable?

    fun deleteByChannelIdIn(channels: List<String>)
}