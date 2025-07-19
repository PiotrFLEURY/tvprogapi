package fr.piotrfleury.tvprogapi.data.sources.postgre

import fr.piotrfleury.tvprogapi.data.models.psql.ChannelPackageTable
import org.springframework.data.repository.CrudRepository

interface ChannelPackageCrudRepository: CrudRepository<ChannelPackageTable, Int> {

    fun deleteByPackageId(packageId: String)
}