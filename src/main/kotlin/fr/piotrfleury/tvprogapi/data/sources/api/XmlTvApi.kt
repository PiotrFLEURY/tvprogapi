package fr.piotrfleury.tvprogapi.data.sources.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface XmlTvApi {

    @GET("xmltv/xmltv.zip")
    fun getXmlTv(): Call<ResponseBody>

    @GET("xmltv/xmltv_tnt.zip")
    fun getXmlTvTnt(): Call<ResponseBody>

    @GET("xmltv/xmltv_fr.zip")
    fun getXmlTvFr(): Call<ResponseBody>
}