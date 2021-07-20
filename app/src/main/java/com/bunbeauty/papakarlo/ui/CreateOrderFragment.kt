package com.bunbeauty.papakarlo.ui

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.State
import com.bunbeauty.domain.util.field_helper.IFieldHelper
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentCreateOrderBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.extensions.visible
import com.bunbeauty.papakarlo.presentation.create_order.CreateOrderViewModel
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.view.CustomSwitcher
import com.bunbeauty.papakarlo.ui.view.PhoneTextWatcher
import com.google.android.material.button.MaterialButton
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CreateOrderFragment : BaseFragment<FragmentCreateOrderBinding>() {

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    @Inject
    lateinit var fieldHelper: IFieldHelper

    override val viewModel: CreateOrderViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    private var reviewInfo: ReviewInfo? = null
    private var reviewManager: ReviewManager? = null

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.run {
            fragmentCreateOrderCsDelivery.switchListener =
                object : CustomSwitcher.SwitchListener {
                    override fun onSwitched(isLeft: Boolean) {
                        viewModel.setIsDelivery(isLeft)
                    }
                }
            viewModel.isDelivery.onEach { isDelivery ->
                fragmentCreateOrderCsDelivery.isLeft = isDelivery
            }.startedLaunch(viewLifecycleOwner)

            viewModel.phone.onEach { phone ->
                if (phone == null) {
                    fragmentCreateOrderNcPhone.cardText =
                        resourcesProvider.getString(R.string.action_create_order_phone)
                    fragmentCreateOrderNcPhone.icon =
                        resourcesProvider.getDrawable(R.drawable.ic_right_arrow)
                } else {
                    fragmentCreateOrderNcPhone.cardText = phone
                    fragmentCreateOrderNcPhone.icon =
                        resourcesProvider.getDrawable(R.drawable.ic_logout)
                }
            }.startedLaunch(viewLifecycleOwner)
            fragmentCreateOrderNcPhone.setOnClickListener {
                viewModel.onPhoneClicked()
            }

            viewModel.address.onEach { address ->
                if (address == null) {
                    fragmentCreateOrderNcAddAddress.visible()
                    fragmentCreateOrderTcAddress.gone()
                } else {
                    fragmentCreateOrderNcAddAddress.gone()
                    fragmentCreateOrderTcAddress.cardText = address
                    fragmentCreateOrderTcAddress.visible()
                }
            }.startedLaunch(viewLifecycleOwner)
            fragmentCreateOrderNcAddAddress.setOnClickListener {
                viewModel.onAddAddressClicked()
            }
            fragmentCreateOrderTcAddress.setOnClickListener {
                viewModel.onAddressClicked()
            }

            viewModel.actionDeferredTime.onEach { actionDeferredTime ->
                fragmentCreateOrderNcAddDeferredTime.cardText = actionDeferredTime
            }.startedLaunch(viewLifecycleOwner)
            viewModel.hintDeferredTime.onEach { hintDeferredTime ->
                fragmentCreateOrderTcDeferredTime.hintText = hintDeferredTime
            }.startedLaunch(viewLifecycleOwner)
            viewModel.deferredTime.onEach { deferredTime ->
                if (deferredTime == null) {
                    fragmentCreateOrderNcAddDeferredTime.visible()
                    fragmentCreateOrderTcDeferredTime.gone()
                } else {
                    fragmentCreateOrderNcAddDeferredTime.gone()
                    fragmentCreateOrderTcDeferredTime.visible()
                    fragmentCreateOrderTcDeferredTime.cardText = deferredTime
                }
            }.startedLaunch(viewLifecycleOwner)
            fragmentCreateOrderNcAddDeferredTime.setOnClickListener {
                viewModel.onAddDeferredTimeClicked()
            }
        }
        
        
        
        
        
        
        
        
        

//        reviewManager = ReviewManagerFactory.create(requireContext())
//        val request = reviewManager?.requestReviewFlow()
//        request?.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                // We got the ReviewInfo object
//                reviewInfo = task.result
//            } else {
//                // There was some problem, log or handle the error code.
//                //@ReviewErrorCode val reviewErrorCode = (task.exception as TaskException).errorCode
//            }
//        }
//
//
    }

//    private fun showTimePicker() {
//        val picker = MaterialTimePicker.Builder()
//            .setTimeFormat(CLOCK_24H)
//            .setHour(viewModel.deferredHoursStateFlow.value ?: 0)
//            .setMinute(viewModel.deferredMinutesStateFlow.value ?: 0)
//            .setTitleText(requireContext().getString(R.string.title_create_order_deferred_time))
//            .build()
//        picker.show(parentFragmentManager, "TimePicker")
//
//        picker.addOnPositiveButtonClickListener {
//            if (viewModel.isDeferredTimeCorrect(picker.hour, picker.minute)) {
//                fragmentCreateOrderGroupAddDeferred.gone()
//                fragmentCreateOrderGroupPickedDeferred.visible()
//                viewModel.deferredHoursStateFlow.value = picker.hour
//                viewModel.deferredMinutesStateFlow.value = picker.minute
//            } else {
//                //showError(resourcesProvider.getString(R.string.error_creation_order_deferred))
//            }
//        }
//        picker.addOnNegativeButtonClickListener {
//            fragmentCreateOrderGroupAddDeferred.visible()
//            fragmentCreateOrderGroupPickedDeferred.gone()
//            viewModel.deferredHoursStateFlow.value = null
//            viewModel.deferredMinutesStateFlow.value = null
//        }
//    }

    private fun activateButton(button: MaterialButton) {
        button.backgroundTintList =
            ColorStateList.valueOf(resourcesProvider.getColor(R.color.colorPrimary))
        button.setTextColor(resourcesProvider.getColor(R.color.white))
    }

    private fun inactivateButton(button: MaterialButton) {
        button.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        button.setTextColor(resourcesProvider.getColor(R.color.grey))
    }
}