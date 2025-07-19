package fr.piotrfleury.tvprogapi.data.models.psql

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.sql.Timestamp

@Table(name = "programs")
data class ProgramTable(
    @Id
    var id: Integer? = null,
    var startTime: Timestamp,
    var endTime: Timestamp,
    var channelId: String,
    var title: String,
    var subtitle: String? = null,
    var description: String? = null,
    var categories: String? = null,
    var icon: String? = null,
    var episodeNum: String? = null,
    var ratingSystem: String? = null,
    var ratingValue: String? = null,
    var ratingIcon: String? = null
)
