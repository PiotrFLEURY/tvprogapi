package fr.piotrfleury.tvprogapi.data.models.xmltvfr

import java.util.Date

data class Program(
    val start: Date,
    val stop: Date,
    val channel: Channel,
    val title: String,
    val subtitle: String? = null,
    val description: String? = null,
    val categories: List<String> = emptyList(),
    val icon: String? = null,
    val episodeNum: String? = null,
    val rating: Rating?,
)
