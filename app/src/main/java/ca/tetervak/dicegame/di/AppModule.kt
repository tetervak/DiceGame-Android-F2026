package ca.tetervak.dicegame.di

import ca.tetervak.dicegame.data.LocalRollDataRepository
import ca.tetervak.dicegame.data.RollDataRepository
import ca.tetervak.dicegame.ui.roller.RollerViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    singleOf(::LocalRollDataRepository) bind RollDataRepository::class

    viewModelOf(::RollerViewModel)
}

