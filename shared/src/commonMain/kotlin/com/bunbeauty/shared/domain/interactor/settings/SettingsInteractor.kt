package com.bunbeauty.shared.domain.interactor.settings

import com.bunbeauty.shared.domain.interactor.city.ICityInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.mapFlow
import com.bunbeauty.shared.domain.model.profile.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class SettingsInteractor(
    private val cityInteractor: ICityInteractor,
    private val userInteractor: IUserInteractor,
) : ISettingsInteractor {

    override fun observeSettings(): Flow<Settings?> {
        return cityInteractor.observeSelectedCity().flatMapLatest { city ->
            userInteractor.observeUser().mapFlow { user ->
                if (city != null) {
                    Settings(
                        user = user,
                        cityName = city.name
                    )
                } else {
                    null
                }
            }
        }
    }
}