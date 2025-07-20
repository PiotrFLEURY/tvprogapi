package fr.piotrfleury.tvprogapi.data.sources.postgre

import fr.piotrfleury.tvprogapi.TestcontainersConfiguration
import fr.piotrfleury.tvprogapi.data.models.psql.ChannelPackageTable
import fr.piotrfleury.tvprogapi.data.models.psql.ChannelTable
import fr.piotrfleury.tvprogapi.domain.entities.ChannelEntity
import fr.piotrfleury.tvprogapi.domain.entities.ChannelPackageEntity
import fr.piotrfleury.tvprogapi.domain.usecases.FetchXmlTv
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.bean.override.mockito.MockitoBean

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class ChannelCrudRepositoryTest {

    @Autowired
    lateinit var channelCrudRepository: ChannelCrudRepository

    @Autowired
    lateinit var channelPackageCrudRepository: ChannelPackageCrudRepository

    @MockitoBean
    lateinit var fetchXmlTv: FetchXmlTv

    @Test
    fun testFindChannelsForProgramType() {
        // Given
        val channel = ChannelEntity(
            channelId = "test_channel",
            name = "Test Channel",
            iconUrl = "http://example.com/icon.png"
        )
        val channelPackageEntity = ChannelPackageEntity.TNT
        channelCrudRepository.save(
            ChannelTable(
                channelId = channel.channelId,
                displayName = channel.name,
                icon = channel.iconUrl
            )
        )

        channelPackageCrudRepository.save(
            ChannelPackageTable(
                packageId = channelPackageEntity.name,
                channelId = channel.channelId
            )
        )

        // When
        val allChannels = channelCrudRepository.findAll().toList()
        val channels = channelCrudRepository.findByPackageLike(channelPackageEntity.name)

        // Then
        assert(allChannels.isNotEmpty()) { "Expected to find channels in the database" }
        assert(allChannels.size == 1) { "Expected exactly one channel in the database" }
        assert(channels.isNotEmpty()) { "Expected to find channels for program type '$channelPackageEntity'" }
        val foundChannel = channels.first()
        assert(foundChannel.channelId == channel.channelId) {
            "Expected to find channel with ID '${channel.channelId}', but found '${foundChannel?.channelId}'"
        }
    }
}