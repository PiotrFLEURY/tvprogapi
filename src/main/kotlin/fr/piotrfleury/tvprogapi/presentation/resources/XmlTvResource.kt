package fr.piotrfleury.tvprogapi.presentation.resources

import fr.piotrfleury.tvprogapi.domain.entities.ChannelPackageEntity
import fr.piotrfleury.tvprogapi.domain.usecases.FetchXmlTv
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/xmltv")
class XmlTvResource(
    val fetchXmlTv: FetchXmlTv
) {

    @GetMapping
    fun getXmlTv() = fetchXmlTv(ChannelPackageEntity.ALL)

    @GetMapping("/tnt")
    fun getTnt() = fetchXmlTv(ChannelPackageEntity.TNT)

    @GetMapping("/fr")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun getFr() = fetchXmlTv(ChannelPackageEntity.FR)

}