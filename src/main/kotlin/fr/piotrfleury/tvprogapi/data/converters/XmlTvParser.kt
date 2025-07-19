package fr.piotrfleury.tvprogapi.data.converters

import fr.piotrfleury.tvprogapi.data.models.xmltvfr.Channel
import fr.piotrfleury.tvprogapi.data.models.xmltvfr.Program
import fr.piotrfleury.tvprogapi.data.models.xmltvfr.Rating
import fr.piotrfleury.tvprogapi.data.models.xmltvfr.Tv
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.xml.parsers.DocumentBuilderFactory

//<?xml version="1.0" encoding="UTF-8"?>
//<!DOCTYPE tv SYSTEM "xmltv.dtd">
//<!-- Generated with XML TV Fr v2.0.0 -->
//<tv source-info-url="https://github.com/racacax/XML-TV-Fr" source-info-name="XML TV Fr" generator-info-name="XML TV Fr" generator-info-url="https://github.com/racacax/XML-TV-Fr">
//<channel id="TF1.fr">
//<display-name>TF1</display-name>
//<icon src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4u3EMdVyFA2uqIpbnwKBT1GRW9U-mA2jyCz28L2ieGx4vB9bKhpInhHCIqmk&amp;amp;s"/>
//</channel>
//<!-- Orange -->
//<programme start="20211227234500 +0100" stop="20211228004500 +0100" channel="TF1.fr">
//<title lang="fr">Joséphine, ange gardien</title>
//<sub-title lang="fr">Haute couture</sub-title>
//<desc lang="fr">Une créatrice de mode, passionnée par son métier, refuse de se faire soigner. Joséphine va tout mettre en oeuvre pour venir en aide à la jeune femme.</desc>
//<category lang="fr">Série</category>
//<category lang="fr">Série Passion</category>
//<icon src="https://proxymedia.woopic.com/340/p/169_EMI_23486537.jpg"/>
//<episode-num system="xmltv_ns">19.4.</episode-num>
//<rating system="CSA">
//<value>Tout public</value>
//</rating>
//</programme>
//<programme start="20211228004500 +0100" stop="20211228013000 +0100" channel="TF1.fr">
//<title lang="fr">New York Unité Spéciale</title>
//<sub-title lang="fr">Une mort prématurée</sub-title>
//<desc lang="fr">Deux kayakistes découvrent le corps sans vie d'un nourrisson dans un réfrigérateur : l'enquête s'oriente très rapidement vers deux adolescentes.</desc>
//<category lang="fr">Série</category>
//<category lang="fr">Série Suspense</category>
//<icon src="https://proxymedia.woopic.com/340/p/169_EMI_10397668.jpg"/>
//<episode-num system="xmltv_ns">3.13.</episode-num>
//<rating system="CSA">
//<value>-10</value>
//<icon src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/bf/Moins10.svg/200px-Moins10.svg.png"/>
//</rating>
//</programme>
//</tv>
@Service
class XmlTvParser(
    val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyyMMddHHmmss Z")
) {

    val log: Logger = LoggerFactory.getLogger(XmlTvParser::class.java)


    fun from(xml: String): Tv {
        log.info("Parsing XML data to Tv object")
        return parseTv(xml)
    }

    private fun parseTv(xml: String): Tv {
        val documentBuilderFactory = DocumentBuilderFactory.newInstance()
        val documentBuilder = documentBuilderFactory.newDocumentBuilder()

        val xmlDocument = documentBuilder.parse(xml.byteInputStream())
        val channels = parseChannels(xmlDocument.getElementsByTagName("channel"))
        return Tv(
            channels = channels,
            programs = parsePrograms(xmlDocument.getElementsByTagName("programme"), channels),
            date = LocalDateTime.now()
        )
    }

    private fun parseChannels(channelNodes: NodeList): List<Channel> {
        val channels = mutableListOf<Channel>()
        for (i in 0 until channelNodes.length) {
            val channelNode = channelNodes.item(i)
            if (channelNode.nodeType == Node.ELEMENT_NODE) {
                val children = channelNode.childNodes
                val displayNode = children.elementByName("display-name")
                val iconNode = children.elementByName("icon")
                val channel = Channel(
                    channelId = channelNode.attributes.getNamedItem("id").nodeValue,
                    displayName = displayNode?.textContent ?: "Unknown display name",
                    icon = iconNode?.attributes?.getNamedItem("src")?.nodeValue
                )
                channels.add(channel)
            }
        }
        return channels
    }

    private fun parsePrograms(programNodes: NodeList, channels: List<Channel>): List<Program> {
        val programs = mutableListOf<Program>()
        for (i in 0 until programNodes.length) {
            val programNode = programNodes.item(i)
            if (programNode.nodeType == Node.ELEMENT_NODE) {
                val children = programNode.childNodes
                val channelId = programNode.attributes.getNamedItem("channel").nodeValue
                val channel = channels.firstOrNull { it.channelId == channelId }
                    ?: throw IllegalStateException("Channel with id $channelId not found")
                val titleNode = children.elementByName("title")
                val subTitleNode = children.elementByName("sub-title")
                val descNode = children.elementByName("desc")
                val iconNode = children.elementByName("icon")
                val episodeNumNode = children.elementByName("episode-num")
                val ratingNode = children.elementByName("rating")

                val startDate = simpleDateFormat.parse(
                    programNode.attributes.getNamedItem("start").nodeValue,

                    )
                val stopDate = simpleDateFormat.parse(
                    programNode.attributes.getNamedItem("stop").nodeValue,
                )

                programs.add(
                    Program(
                        start = startDate,
                        stop = stopDate,
                        channel = channel,
                        title = titleNode?.textContent ?: "Unknown Title",
                        subtitle = subTitleNode?.textContent,
                        description = descNode?.textContent,
                        icon = iconNode?.attributes?.getNamedItem("src")?.nodeValue,
                        episodeNum = episodeNumNode?.textContent,
                        rating = parseRating(ratingNode),
                    )
                )
            }
        }
        return programs
    }

    private fun parseRating(ratingNode: Node?): Rating? {
        ratingNode?.let {
            val valueNode = it.childNodes.elementByName("value")
            val iconNode = it.childNodes.elementByName("icon")
            return Rating(
                system = it.attributes.getNamedItem("system")?.nodeValue,
                value = valueNode?.textContent ?: "Unknown",
                icon = iconNode?.attributes?.getNamedItem("src")?.nodeValue
            )
        }
        return null
    }
}

fun NodeList.elementByName(tagName: String): Node? {
    for (i in 0 until this.length) {
        val node = this.item(i)
        if (node.nodeType == Node.ELEMENT_NODE && node.nodeName == tagName) {
            return node
        }
    }
    return null
}