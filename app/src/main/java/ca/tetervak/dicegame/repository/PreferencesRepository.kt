package ca.tetervak.dicegame.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


class PreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        const val DEFAULT_NUMBER_OF_DICE = 3
        private val NUMBER_OF_DICE_KEY = intPreferencesKey("number_of_dice")
        private const val TAG = "PreferencesRepository"
    }

    val preferencesFlow: Flow<Preferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }

    val numberOfDiceFlow: Flow<Int> = preferencesFlow
        .map { preferences ->
            preferences[NUMBER_OF_DICE_KEY] ?: DEFAULT_NUMBER_OF_DICE
        }

    suspend fun saveNumberOfDice(numberOfDice: Int) {
        dataStore.edit { preferences ->
            preferences[NUMBER_OF_DICE_KEY] = numberOfDice
        }
    }
}