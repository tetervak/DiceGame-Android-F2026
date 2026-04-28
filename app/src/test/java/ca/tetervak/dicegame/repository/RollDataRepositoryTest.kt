package ca.tetervak.dicegame.repository

import androidx.datastore.core.DataStoreFactory
import ca.tetervak.dicegame.data.RollDataSerializer
import ca.tetervak.dicegame.domain.RollData
import ca.tetervak.dicegame.rules.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.lang.IllegalArgumentException
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class RollDataRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val temporaryFolder: TemporaryFolder = TemporaryFolder()

    // specifying the seed makes the "random" sequence always the same
    private val random: Random = Random(seed = 10)
    private lateinit var rollDataRepository: RollDataRepository

    @Before
    fun setUp() {
        println("--- testing case ---")
        val testScope = TestScope(mainDispatcherRule.testDispatcher)
        val dataStore = DataStoreFactory.create(
            serializer = RollDataSerializer,
            scope = testScope,
            produceFile = { File(temporaryFolder.newFolder(), "test_roll_data.pb") }
        )
        rollDataRepository = RollDataRepository(random, dataStore)
    }

    @After
    fun tearDown() {
        println("--- end of case ---")
    }

    @Test
    fun getRandomRollData() {
        println("test getRandomRollData()")
        for(numberOfDice: Int in 1..4){
            for(repetition: Int in 1..5){
                println("test getNumberOfDice($numberOfDice) repetition $repetition")
                val rollData = rollDataRepository.getRandomRollData(numberOfDice)
                assertNotNull(rollData)
                assertTrue(rollData.values.isNotEmpty())
                println("rollData = $rollData")
                assertEquals(numberOfDice, rollData.numberOfDice)
                val values = rollData.values
                for(value in values){
                    assertTrue(value > 0)
                    assertTrue(value <= 6)
                }
            }
        }
    }

    @Test
    fun getRollData_ZeroDice(){
        println("test throwing exception for getRollData(numberOfDice = 0)")
        assertThrows(IllegalArgumentException::class.java) {
            val rollData = rollDataRepository.getRandomRollData(numberOfDice = 0)
            println("rollData = $rollData")
        }
    }

    @Test
    fun saveAndLoadRollData() = runTest {
        println("test saveAndLoadRollData()")
        val rollData = RollData(listOf(1, 2, 3))
        val timestamp = 123456789L
        rollDataRepository.saveRollData(rollData, timestamp)
        
        val savedRoll = rollDataRepository.lastRollFlow.first()
        assertNotNull(savedRoll)
        assertEquals(rollData.values, savedRoll?.rollData?.values)
        assertEquals(timestamp, savedRoll?.timestamp)
    }

    @Test
    fun clearRollData() = runTest {
        println("test clearRollData()")
        val rollData = RollData(listOf(1, 2, 3))
        rollDataRepository.saveRollData(rollData, System.currentTimeMillis())
        
        rollDataRepository.clearRollData()
        val savedRoll = rollDataRepository.lastRollFlow.first()
        assertNull(savedRoll)
    }
}
