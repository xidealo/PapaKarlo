package com.bunbeauty.papakarlo.common.view_model

import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent

@Deprecated("Use SharedViewModel")
abstract class BaseViewModel : ViewModel(), KoinComponent
