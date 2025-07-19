package fr.piotrfleury.tvprogapi.domain.entities

import java.util.Date

data class ProgramEntity(
    var id: Int? = null,
    var startTime: Date,
    var endTime: Date,
    var channelId: String,
    var title: String,
    var subtitle: String? = null,
    var description: String? = null,
    var categories: List<String> = emptyList(),
    var iconUrl: String? = null,
    var episodeNum: String? = null,
    var rating: RatingEntity? = null
)
