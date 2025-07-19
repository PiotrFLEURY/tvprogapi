package fr.piotrfleury.tvprogapi.data.models.xmltvfr

import java.time.LocalDateTime

data class Tv(
    val channels: List<Channel>,
    val programs: List<Program>,
    val date: LocalDateTime
)