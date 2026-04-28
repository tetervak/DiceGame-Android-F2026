package ca.tetervak.dicegame.domain

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RollDataTest {
    @Before
    fun setUp() {
        println("--- testing case ---")
    }

    @After
    fun tearDown() {
        println("--- ------- ---- ---")
    }

    @Test
    fun getTotal() {
        println("test getTotal()")
        val values = listOf(4,3,5)
        println("values = $values")
        val total = values.sum()
        println("total = $total")
        val rollData = RollData(values)
        println("rollData = $rollData")
        assertEquals(total, rollData.total)
    }


    @Test
    fun getNumberOfDice() {
        println("test numberOfDice()")
        val values = listOf(4,3,5,6)
        val numberOfDice = values.size
        println("numberOfDice = $numberOfDice")
        val rollData = RollData(values)
        println("rollData = $rollData")
        assertEquals(numberOfDice, rollData.numberOfDice)
    }

    @Test
    fun testToString() {
        println("test toString()")
        val values = listOf(4,3,5,6)
        val rollData = RollData(values)
        println("rollData = $rollData")
        assertEquals("RollData(values=[4, 3, 5, 6], total=18, numberOfDice=4)", rollData.toString())
    }

    @Test
    fun getValues() {
        println("test getValues()")
        val values = listOf(1, 2, 3)
        val rollData = RollData(values)
        println("rollData = $rollData")
        assertEquals(values, rollData.values)
    }

}