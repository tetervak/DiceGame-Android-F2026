package ca.tetervak.dicegame.repository

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException
import kotlin.random.Random

class RollDataRepositoryTest {

    // specifying the seed makes the "random" sequence always the same
    private val random: Random = Random(seed = 10)
    private val rollDataRepository: RollDataRepository = RollDataRepository(random)

    @Before
    fun setUp() {
        println("--- testing case ---")
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

}