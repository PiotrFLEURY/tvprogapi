package fr.piotrfleury.tvprogapi.data.sources.postgre

import fr.piotrfleury.tvprogapi.TestcontainersConfiguration
import fr.piotrfleury.tvprogapi.data.models.psql.ChannelTable
import fr.piotrfleury.tvprogapi.data.models.psql.ProgramTable
import fr.piotrfleury.tvprogapi.domain.usecases.FetchXmlTv
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.bean.override.mockito.MockitoBean
import java.sql.Timestamp
import java.util.Calendar

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class ProgramCrudRepositoryTest {

    @Autowired
    lateinit var channelCrudRepository: ChannelCrudRepository

    @Autowired
    lateinit var programCrudRepository: ProgramCrudRepository

    @MockitoBean
    lateinit var fetchXmlTv: FetchXmlTv

    @Test
    fun tonightProgram() {
        // Given
        val channelId = "test_channel_program"
        val now = Calendar.getInstance()
        now.set(Calendar.HOUR_OF_DAY, 20)
        now.set(Calendar.MINUTE, 30)
        val targetTime = Timestamp(now.timeInMillis)

        val channel = ChannelTable(
            channelId = channelId,
            displayName = "Test Channel",
            icon = "http://example.com/icon.png"
        )

        val program20h = ProgramTable(
            channelId = channelId,
            startTime = Timestamp(targetTime.time - 3600000), // 20:00
            endTime = Timestamp(targetTime.time + 3600000), // 21:00
            title = "Program at 20:00",
            description = "Description for program at 20:00"
        )

        val program21h = ProgramTable(
            channelId = channelId,
            startTime = Timestamp(targetTime.time + 2400000), // 21:20
            endTime = Timestamp(targetTime.time + 3600000), // 22:00
            title = "Program at 21:00",
            description = "Description for program at 21:00"
        )

        val program22h = ProgramTable(
            channelId = channelId,
            startTime = Timestamp(targetTime.time + 3600000), // 22:00
            endTime = Timestamp(targetTime.time + 7200000), // 23:00
            title = "Program at 22:00",
            description = "Description for program at 22:00"
        )

        channelCrudRepository.save(channel)

        programCrudRepository.saveAll(
            listOf(program20h, program21h, program22h)
        )

        // When
        val tonightProgram = programCrudRepository.findTonightByChannelId(channelId, targetTime)

        // Then
        println(tonightProgram?.title)
        assert(tonightProgram != null) { "Expected to find a tonight program for channel '$channelId'" }
        assert(tonightProgram!!.channelId == channelId) { "Expected the program to belong to channel '$channelId'" }
    }
}