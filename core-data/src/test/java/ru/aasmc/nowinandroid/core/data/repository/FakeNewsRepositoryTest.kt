package ru.aasmc.nowinandroid.core.data.repository

import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.serialization.json.Json
import org.junit.Before
import ru.aasmc.nowinandroid.core.data.repository.fake.FakeNewsRepository

class FakeNewsRepositoryTest {
    private lateinit var subject: FakeNewsRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        subject = FakeNewsRepository(
            ioDispatcher = testDispatcher,
            networkJson = Json { ignoreUnknownKeys = true }
        )
    }
}