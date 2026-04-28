package ca.tetervak.dicegame.ui.roller

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

// It is an example of an AI-generated test
class RollerViewModelTest {

    private lateinit var viewModel: RollerViewModel

    @Before
    fun setUp() {
        viewModel = RollerViewModel()
    }

    @Test
    fun initialState_isNotRolledWithThreeDice() {
        val state = viewModel.uiState.value
        assertTrue("Initial state should be NotRolled",
            state is RollerUiState.NotRolled)
        assertEquals("Initial number of dice should be 3",
            3, state.numberOfDice)
    }

    @Test
    fun onRoll_updatesStateToRolled() {
        viewModel.onRoll()
        val state = viewModel.uiState.value
        assertTrue("State should be Rolled after onRoll()",
            state is RollerUiState.Rolled)
        state as RollerUiState.Rolled
        assertEquals("Number of dice should match",
            3, state.numberOfDice)
        assertEquals("Number of rolled values should be 3",
            3, state.rollData.values.size)
    }

    @Test
    fun onReset_resetsStateToNotRolledWithThreeDice() {
        // Change state first
        viewModel.onChangeOfNumberOfDice(5)
        viewModel.onRoll()
        
        // Reset
        viewModel.onReset()
        
        val state = viewModel.uiState.value
        assertTrue("State should be NotRolled after onReset()",
            state is RollerUiState.NotRolled)
        assertEquals("Number of dice should be reset to 3",
            3, state.numberOfDice)
    }

    @Test
    fun onChangeOfNumberOfDice_updatesNumberOfDice() {
        val newCount = 5
        viewModel.onChangeOfNumberOfDice(newCount)
        val state = viewModel.uiState.value
        assertTrue("State should be NotRolled after onChangeOfNumberOfDice()",
            state is RollerUiState.NotRolled)
        assertEquals("Number of dice should be updated to $newCount",
            newCount, state.numberOfDice)
    }

    @Test
    fun onChangeOfNumberOfDice_afterRolled_resetsToNotRolled() {
        viewModel.onRoll()
        assertTrue("State should be Rolled before change",
            viewModel.uiState.value is RollerUiState.Rolled)
        
        val newCount = 4
        viewModel.onChangeOfNumberOfDice(newCount)
        val state = viewModel.uiState.value
        assertTrue("State should change to NotRolled when number of dice changes",
            state is RollerUiState.NotRolled)
        assertEquals("Number of dice should be updated to $newCount",
            newCount, state.numberOfDice)
    }

    @Test
    fun onChangeOfNumberOfDice_sameValue_doesNotResetToNotRolledIfAlreadyRolled() {
        // Note: The current implementation of onChangeOfNumberOfDice only changes if new != current
        // fun onChangeOfNumberOfDice(newNumberOfDice: Int) {
        //    if(newNumberOfDice != _uiState.value.numberOfDice){
        //        _uiState.value = RollerUiState.NotRolled(newNumberOfDice)
        //    }
        // }
        
        viewModel.onRoll()
        val stateBefore = viewModel.uiState.value
        assertTrue(stateBefore is RollerUiState.Rolled)
        
        viewModel.onChangeOfNumberOfDice(3) // 3 is the current number of dice
        
        val stateAfter = viewModel.uiState.value
        assertTrue("State should remain Rolled if number of dice is the same",
            stateAfter is RollerUiState.Rolled)
        assertEquals("State should not have changed",
            stateBefore, stateAfter)
    }
}
