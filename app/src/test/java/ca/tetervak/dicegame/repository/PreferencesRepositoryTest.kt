package ca.tetervak.dicegame.repository

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

// It is an example of an AI-generated test
@OptIn(ExperimentalCoroutinesApi::class)
class PreferencesRepositoryTest {

    @get:Rule
    val temporaryFolder: TemporaryFolder = TemporaryFolder()

    private fun createDataStore() = PreferenceDataStoreFactory.create(
        produceFile = { File(temporaryFolder.newFolder(), "test_preferences.preferences_pb") }
    )

    @Test
    fun numberOfDiceFlow_emitsDefaultValueInitially() = runTest {
        val dataStore = createDataStore()
        val repository = PreferencesRepository(dataStore)

        val numberOfDice = repository.numberOfDiceFlow.first()
        assertEquals(PreferencesRepository.DEFAULT_NUMBER_OF_DICE, numberOfDice)
    }

    @Test
    fun saveNumberOfDice_updatesValue() = runTest {
        val dataStore = createDataStore()
        val repository = PreferencesRepository(dataStore)
        val expected = 5

        repository.saveNumberOfDice(expected)

        val actual = repository.numberOfDiceFlow.first()
        assertEquals(expected, actual)
    }

    @Test
    fun numberOfDiceFlow_emitsNewValuesOnUpdate() = runTest {
        val dataStore = createDataStore()
        val repository = PreferencesRepository(dataStore)
        
        // Initial check
        assertEquals(PreferencesRepository.DEFAULT_NUMBER_OF_DICE, repository.numberOfDiceFlow.first())

        // Update 1
        repository.saveNumberOfDice(4)
        assertEquals(4, repository.numberOfDiceFlow.first())

        // Update 2
        repository.saveNumberOfDice(6)
        assertEquals(6, repository.numberOfDiceFlow.first())
    }
}
