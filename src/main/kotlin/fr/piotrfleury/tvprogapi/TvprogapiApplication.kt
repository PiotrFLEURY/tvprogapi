package fr.piotrfleury.tvprogapi

import fr.piotrfleury.tvprogapi.data.sources.api.XmlTvApi
import fr.piotrfleury.tvprogapi.domain.entities.TvProgApiConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
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

	@Bean
	fun tvProgApiConfig(environment: Environment): TvProgApiConfig {
		val initOnStartup = environment.getProperty("tvprogapi.init.on.startup", Boolean::class.java, false)
		return TvProgApiConfig(
			initOnStartup = initOnStartup
		)
	}

}

fun main(args: Array<String>) {
	runApplication<TvprogapiApplication>(*args)
}
