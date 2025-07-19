package fr.piotrfleury.tvprogapi.domain.repositories

import fr.piotrfleury.tvprogapi.data.models.xmltvfr.Tv

interface XmlTvRepository {

    fun fetchXmlTv(): Tv

    fun fetchXmlTvFr(): Tv

    fun fetchXmlTvTnt(): Tv
}