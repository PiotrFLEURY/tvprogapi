package fr.piotrfleury.tvprogapi.data.sources.postgre

import fr.piotrfleury.tvprogapi.data.models.psql.ChannelTable
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface ChannelCrudRepository: CrudRepository<ChannelTable, Integer> {

    @Query("""
        SELECT * 
        FROM channels
        JOIN channel_packages ON channels.channel_id = channel_packages.channel_id
        WHERE channel_packages.package_id = :packageId
    """)
    fun findByPackageLike(packageId: String): List<ChannelTable>

}