package com.bunbeauty.papakarlo.common.viewmodel

import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent

@Deprecated("Use SharedViewModel")
abstract class BaseViewModel : ViewModel(), KoinComponent
