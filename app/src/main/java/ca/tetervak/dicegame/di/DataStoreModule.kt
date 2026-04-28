package ca.tetervak.dicegame.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import ca.tetervak.dicegame.data.RollDataProto
import ca.tetervak.dicegame.data.RollDataSerializer
import ca.tetervak.dicegame.repository.PreferencesRepository
import ca.tetervak.dicegame.repository.RollDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlin.random.Random


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideRandom(): Random = Random.Default

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("dice_game_preferences") }
        )
    }

    @Provides
    @Singleton
    fun providePreferencesRepository(dataStore: DataStore<Preferences>): PreferencesRepository {
        return PreferencesRepository(dataStore)
    }

    @Provides
    @Singleton
    fun provideRollDataDataStore(@ApplicationContext context: Context): DataStore<RollDataProto> {
        return DataStoreFactory.create(
            serializer = RollDataSerializer,
            produceFile = { context.dataStoreFile("roll_data.pb") }
        )
    }

    @Provides
    @Singleton
    fun provideRollDataRepository(
        random: Random,
        dataStore: DataStore<RollDataProto>
    ): RollDataRepository {
        return RollDataRepository(random, dataStore)
    }
}
