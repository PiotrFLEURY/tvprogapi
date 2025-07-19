package fr.piotrfleury.tvprogapi

import fr.piotrfleury.tvprogapi.data.sources.api.XmlTvApi
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

@SpringBootApplication
class TvprogapiApplication {

	@Bean
	fun retrofit(): Retrofit {
		return Retrofit.Builder()
			.baseUrl("https://xmltvfr.fr/")
			.addConverterFactory(ScalarsConverterFactory.create())
			.build()
	}

	@Bean
	fun xmlTvApi(retrofit: Retrofit): XmlTvApi {
		return retrofit.create(XmlTvApi::class.java)
	}

}

fun main(args: Array<String>) {
	runApplication<TvprogapiApplication>(*args)
}
