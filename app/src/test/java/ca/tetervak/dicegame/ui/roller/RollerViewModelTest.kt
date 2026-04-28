package ca.tetervak.dicegame.ui.roller

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import ca.tetervak.dicegame.data.RollDataSerializer
import ca.tetervak.dicegame.repository.PreferencesRepository
import ca.tetervak.dicegame.repository.RollDataRepository
import ca.tetervak.dicegame.rules.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class RollerViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val temporaryFolder: TemporaryFolder = TemporaryFolder()

    private lateinit var viewModel: RollerViewModel

    private fun createViewModel(testScope: TestScope) {
        val prefsDataStore = PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { File(temporaryFolder.newFolder(), "test_prefs.preferences_pb") }
        )
        val rollDataDataStore = DataStoreFactory.create(
            serializer = RollDataSerializer,
            scope = testScope,
            produceFile = { File(temporaryFolder.newFolder(), "test_roll_data.pb") }
        )
        viewModel = RollerViewModel(
            rollDataRepository = RollDataRepository(Random(10), rollDataDataStore),
            preferencesRepository = PreferencesRepository(prefsDataStore)
        )
    }

    @Test
    fun initialState_isNotRolledWithThreeDice() = runTest {
        createViewModel(this)
        advanceUntilIdle() // let init block complete
        val state = viewModel.uiState.value
        assertTrue("Initial state should be NotRolled, but was ${state::class.simpleName}",
            state is RollerUiState.NotRolled)
        assertEquals("Initial number of dice should be 3",
            3, state.numberOfDice)
    }

    @Test
    fun onRoll_updatesStateToRolled() = runTest {
        createViewModel(this)
        advanceUntilIdle()
        viewModel.onRoll()
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertTrue("State should be Rolled after onRoll(), but was ${state::class.simpleName}",
            state is RollerUiState.Rolled)
        state as RollerUiState.Rolled
        assertEquals("Number of dice should match",
            3, state.numberOfDice)
        assertEquals("Number of rolled values should be 3",
            3, state.rollData.values.size)
    }

    @Test
    fun onReset_resetsStateToNotRolledWithThreeDice() = runTest {
        createViewModel(this)
        advanceUntilIdle()
        // Change state first
        viewModel.onChangeNumberOfDice(5)
        advanceUntilIdle()
        viewModel.onRoll()
        advanceUntilIdle()
        
        // Reset
        viewModel.onReset()
        advanceUntilIdle()
        
        val state = viewModel.uiState.value
        assertTrue("State should be NotRolled after onReset(), but was ${state::class.simpleName}",
            state is RollerUiState.NotRolled)
        assertEquals("Number of dice should be reset to 3",
            3, state.numberOfDice)
    }

    @Test
    fun onChangeOfNumberDice_updatesNumberOfDice() = runTest {
        createViewModel(this)
        advanceUntilIdle()
        val newCount = 5
        viewModel.onChangeNumberOfDice(newCount)
        advanceUntilIdle()
        
        val state = viewModel.uiState.value
        assertTrue("State should be NotRolled after onChangeOfNumberOfDice(), but was ${state::class.simpleName}",
            state is RollerUiState.NotRolled)
        assertEquals("Number of dice should be updated to $newCount, but was ${state.numberOfDice}",
            newCount, state.numberOfDice)
    }

    @Test
    fun onChangeNumberOfDice_afterRolled_resetsToNotRolled() = runTest {
        createViewModel(this)
        advanceUntilIdle()
        viewModel.onRoll()
        advanceUntilIdle()
        assertTrue("State should be Rolled before change, but was ${viewModel.uiState.value::class.simpleName}",
            viewModel.uiState.value is RollerUiState.Rolled)
        
        val newCount = 4
        viewModel.onChangeNumberOfDice(newCount)
        advanceUntilIdle()
        
        val state = viewModel.uiState.value
        assertTrue("State should change to NotRolled when number of dice changes, but was ${state::class.simpleName}",
            state is RollerUiState.NotRolled)
        assertEquals("Number of dice should be updated to $newCount",
            newCount, state.numberOfDice)
    }

    @Test
    fun onChangeNumberOfDice_sameValue_doesNotResetToNotRolledIfAlreadyRolled() = runTest {
        createViewModel(this)
        advanceUntilIdle()
        viewModel.onRoll()
        advanceUntilIdle()
        val stateBefore = viewModel.uiState.value
        assertTrue("State should be Rolled, but was ${stateBefore::class.simpleName}",
            stateBefore is RollerUiState.Rolled)
        
        viewModel.onChangeNumberOfDice(3) // 3 is the current number of dice
        advanceUntilIdle()
        
        val stateAfter = viewModel.uiState.value
        assertTrue("State should remain Rolled if number of dice is the same, but was ${stateAfter::class.simpleName}",
            stateAfter is RollerUiState.Rolled)
        assertEquals("State should not have changed",
            stateBefore, stateAfter)
    }
}
