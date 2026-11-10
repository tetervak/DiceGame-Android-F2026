package ca.tetervak.dicegame.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import ca.tetervak.dicegame.repository.PreferencesRepository
import ca.tetervak.dicegame.repository.RollDataRepository
import ca.tetervak.dicegame.ui.roller.RollerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private const val USER_PREFERENCES = "user_preferences"

val appModule = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = { androidContext().preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

    single<RollDataRepository>{ RollDataRepository() }
    single<PreferencesRepository>{ PreferencesRepository(dataStore = get()) }

    viewModel<RollerViewModel> { RollerViewModel(rollDataRepository = get(), preferencesRepository = get()) }
}

