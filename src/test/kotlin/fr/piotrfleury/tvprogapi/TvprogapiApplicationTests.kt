package fr.piotrfleury.tvprogapi

import fr.piotrfleury.tvprogapi.domain.usecases.FetchXmlTv
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.bean.override.mockito.MockitoBean

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class TvprogapiApplicationTests {

	@MockitoBean
	lateinit var fetchXmlTv: FetchXmlTv

	@Test
	fun contextLoads() {
	}

}
