package com.bunbeauty.papakarlo.ui

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
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

        viewDataBinding.fragmentCreateOrderCsDelivery.switchListener =
            object : CustomSwitcher.SwitchListener {
                override fun onSwitched(isLeft: Boolean) {
                    viewModel.setIsDelivery(isLeft)
                }
            }
        viewModel.isDelivery.onEach { isDelivery ->
            viewDataBinding.fragmentCreateOrderCsDelivery.isLeft = isDelivery
        }.startedLaunch(viewLifecycleOwner)

        viewModel.phone.onEach { phone ->

        }.startedLaunch(viewLifecycleOwner)





        viewModel.hasAddressState.onEach { state ->
            when (state) {
                is State.Success -> {
                    viewDataBinding.fragmentCreationOrderGroupHasAddress.toggleVisibility(state.data)
                    viewDataBinding.fragmentCreationOrderGroupNoAddress.toggleVisibility(!state.data)
                }
                else -> {
                }
            }
        }.startedLaunch(viewLifecycleOwner)

        viewModel.selectedAddressTextState.onEach { state ->
            when (state) {
                is State.Success -> {
                    viewDataBinding.fragmentCreationOrderBtnAddressPick.text = state.data
                }
                else -> {
                }
            }
        }.startedLaunch(viewLifecycleOwner)
        viewModel.getAddress()

        viewModel.userEntityState.onEach { state ->
            when (state) {
                is State.Success -> {
                    if (state.data != null) {
                        viewDataBinding.fragmentCreationOrderEtPhone.setText(
                            state.data?.phone ?: ""
                        )
                        viewDataBinding.fragmentCreationOrderEtEmail.setText(
                            state.data?.email ?: ""
                        )
                        viewDataBinding.fragmentCreationOrderTvBonusesValue.text =
                            state.data?.bonusList?.sum().toString()
                    } else {
                        viewDataBinding.fragmentCreationOrderEtPhone.setText(
                            viewModel.getCachedPhone()
                        )
                        viewDataBinding.fragmentCreationOrderEtEmail.setText(
                            viewModel.getCachedEmail()
                        )
                        viewDataBinding.fragmentCreationOrderMcvBonuses.gone()
                    }
                    viewDataBinding.fragmentCreationOrderSvMain.visible()
                    viewDataBinding.fragmentCreationOrderPbLoading.gone()
                }
                else -> {
                }
            }
        }.startedLaunch(viewLifecycleOwner)
        viewModel.getUser()

        viewModel.deferredTextStateFlow.onEach { deferredText ->
            viewDataBinding.fragmentCreationOrderBtnSelectedDeferred.text = deferredText
        }.startedLaunch(viewLifecycleOwner)
        viewModel.subscribeOnDeferredText()

        viewModel.orderStringStateFlow.onEach { orderString ->
            viewDataBinding.fragmentCreationOrderBtnCreateOrder.text = orderString
        }.startedLaunch(viewLifecycleOwner)
        viewModel.subscribeOnOrderString()

        //viewModel.isDeliveryState.onEach { isDelivery ->
//            if (isDelivery) {
//                activateButton(viewDataBinding.fragmentCreationOrderBtnDelivery)
//                inactivateButton(viewDataBinding.fragmentCreationOrderBtnPickup)
//            } else {
//                inactivateButton(viewDataBinding.fragmentCreationOrderBtnDelivery)
//                activateButton(viewDataBinding.fragmentCreationOrderBtnPickup)
//            }
        //    viewDataBinding.fragmentCreationOrderTvDelivery.toggleVisibility(isDelivery)
        //}.startedLaunch(viewLifecycleOwner)

        val phoneTextWatcher = PhoneTextWatcher(viewDataBinding.fragmentCreationOrderEtPhone)
        viewDataBinding.fragmentCreationOrderEtPhone.addTextChangedListener(phoneTextWatcher)

        setOnClickListeners()

        reviewManager = ReviewManagerFactory.create(requireContext())
        val request = reviewManager?.requestReviewFlow()
        request?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // We got the ReviewInfo object
                reviewInfo = task.result
            } else {
                // There was some problem, log or handle the error code.
                //@ReviewErrorCode val reviewErrorCode = (task.exception as TaskException).errorCode
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setOnClickListeners() {
        viewDataBinding.fragmentCreationOrderBtnAddressPick.setOnClickListener {
            viewModel.onAddressClicked()
        }
        viewDataBinding.fragmentCreationOrderBtnCreateAddress.setOnClickListener {
            viewModel.onCreateAddressClicked()
        }
        viewDataBinding.fragmentCreationOrderBtnCreateOrder.setOnClickListener {
            createOrder()
        }
//        viewDataBinding.fragmentCreationOrderBtnDelivery.setOnClickListener {
//            viewModel.isDeliveryState.value = true
//        }
//        viewDataBinding.fragmentCreationOrderBtnPickup.setOnClickListener {
//            viewModel.isDeliveryState.value = false
//        }
        viewDataBinding.fragmentCreationOrderBtnSelectedDeferred.setOnClickListener {
            showTimePicker()
        }
        viewDataBinding.fragmentCreationOrderBtnDeferred.setOnClickListener {
            showTimePicker()
        }
    }

    private fun showTimePicker() {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(CLOCK_24H)
            .setHour(viewModel.deferredHoursStateFlow.value ?: 0)
            .setMinute(viewModel.deferredMinutesStateFlow.value ?: 0)
            .setTitleText(requireContext().getString(R.string.title_creation_order_deferred_time))
            .build()
        picker.show(parentFragmentManager, "TimePicker")

        picker.addOnPositiveButtonClickListener {
            if (viewModel.isDeferredTimeCorrect(picker.hour, picker.minute)) {
                viewDataBinding.fragmentCreationOrderGroupAddDeferred.gone()
                viewDataBinding.fragmentCreationOrderGroupPickedDeferred.visible()
                viewModel.deferredHoursStateFlow.value = picker.hour
                viewModel.deferredMinutesStateFlow.value = picker.minute
            } else {
                //showError(resourcesProvider.getString(R.string.error_creation_order_deferred))
            }
        }
        picker.addOnNegativeButtonClickListener {
            viewDataBinding.fragmentCreationOrderGroupAddDeferred.visible()
            viewDataBinding.fragmentCreationOrderGroupPickedDeferred.gone()
            viewModel.deferredHoursStateFlow.value = null
            viewModel.deferredMinutesStateFlow.value = null
        }
    }

    private fun createOrder() {
        if (!fieldHelper.isCorrectFieldContent(
                viewDataBinding.fragmentCreationOrderEtComment.text.toString(),
                false,
                100
            )
        ) {
            viewDataBinding.fragmentCreationOrderEtComment.error =
                resources.getString(R.string.error_creation_order_comment)
            viewDataBinding.fragmentCreationOrderEtComment.requestFocus()
            return
        }
        if (!fieldHelper.isCorrectFieldContent(
                viewDataBinding.fragmentCreationOrderEtPhone.text.toString(),
                true,
                18,
                18
            )
        ) {
            viewDataBinding.fragmentCreationOrderEtPhone.error =
                resources.getString(R.string.error_creation_order_phone)
            viewDataBinding.fragmentCreationOrderEtPhone.requestFocus()
            return
        }

        viewModel.createOrder(
            viewDataBinding.fragmentCreationOrderEtComment.text.toString().trim(),
            viewDataBinding.fragmentCreationOrderEtPhone.text.toString(),
            viewDataBinding.fragmentCreationOrderEtEmail.text.toString().trim(),
            viewModel.deferredHoursStateFlow.value,
            viewModel.deferredMinutesStateFlow.value,
            viewDataBinding.fragmentCreationOrderEtBonuses.text.toString()
        )

        if (reviewInfo != null)
            reviewManager?.launchReviewFlow(requireActivity(), reviewInfo!!)
                ?.addOnCompleteListener { _ ->
                    val t = 0
                    //show toast
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                }
    }

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