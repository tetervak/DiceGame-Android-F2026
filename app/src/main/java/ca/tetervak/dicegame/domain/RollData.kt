package ca.tetervak.dicegame.domain

class RollData(
    val values: List<Int>
) {

    val total: Int = values.sum()

    val numberOfDice: Int = values.size

    override fun toString(): String {
        return "RollData(values=$values, total=$total, numberOfDice=$numberOfDice)"
    }
}