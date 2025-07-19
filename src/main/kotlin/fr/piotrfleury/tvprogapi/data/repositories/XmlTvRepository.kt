package fr.piotrfleury.tvprogapi.data.repositories

import fr.piotrfleury.tvprogapi.data.converters.ByteArrayUnzipper
import fr.piotrfleury.tvprogapi.data.converters.XmlTvParser
import fr.piotrfleury.tvprogapi.data.models.xmltvfr.Tv
import fr.piotrfleury.tvprogapi.data.sources.api.XmlTvApi
import fr.piotrfleury.tvprogapi.domain.repositories.XmlTvRepository
import okhttp3.ResponseBody
import org.springframework.stereotype.Service


@Service
class XmlTvRepository(
    private val api: XmlTvApi,
    private val parser: XmlTvParser,
    private val byteArrayUnzipper: ByteArrayUnzipper
): XmlTvRepository {

    fun ResponseBody.xmlTv(): Tv {
        val byteArray = byteStream().readBytes()
        val unzippedBytes = byteArrayUnzipper.toUnzippedByteArray(byteArray)
        val xml = unzippedBytes.decodeToString()
        return parser.from(xml)
    }

    override fun fetchXmlTv(): Tv {
        val call = api.getXmlTv()
        val httpResponse = call.execute()
        if (httpResponse.isSuccessful) {
            return httpResponse.body()?.xmlTv() ?: throw IllegalStateException("Response body is null")
        } else {
            throw IllegalStateException("Failed to fetch XML TV data: ${httpResponse.errorBody()?.string()}")
        }
    }

    override fun fetchXmlTvTnt(): Tv {
        val call = api.getXmlTvTnt()
        val httpResponse = call.execute()
        if (httpResponse.isSuccessful) {
            return httpResponse.body()?.xmlTv() ?: throw IllegalStateException("Response body is null")
        } else {
            throw IllegalStateException("Failed to fetch TNT data: ${httpResponse.errorBody()?.string()}")
        }
    }

    override fun fetchXmlTvFr(): Tv {
        val call = api.getXmlTvFr()
        val httpResponse = call.execute()
        if (httpResponse.isSuccessful) {
            return httpResponse.body()?.xmlTv() ?: throw IllegalStateException("Response body is null")
        } else {
            throw IllegalStateException("Failed to fetch French XML TV data: ${httpResponse.errorBody()?.string()}")
        }
    }

}