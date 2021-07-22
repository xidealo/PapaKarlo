package com.bunbeauty.papakarlo.ui.fragment.create_order

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.bunbeauty.common.Constants.COMMENT_REQUEST_KEY
import com.bunbeauty.common.Constants.DEFERRED_TIME_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_COMMENT_KEY
import com.bunbeauty.common.Constants.SELECTED_DEFERRED_TIME_KEY
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
import com.google.android.material.button.MaterialButton
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
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
                        viewModel.onIsDeliveryChanged(isLeft)
                    }
                }
            viewModel.isDelivery.onEach { isDelivery ->
                fragmentCreateOrderCsDelivery.isLeft = isDelivery
                fragmentCreateOrderTvDelivery.toggleVisibility(isDelivery)
                fragmentCreateOrderTvDeliveryValue.toggleVisibility(isDelivery)
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

            viewModel.comment.onEach { comment ->
                if (comment == null) {
                    fragmentCreateOrderNcAddComment.visible()
                    fragmentCreateOrderTcComment.gone()
                } else {
                    fragmentCreateOrderNcAddComment.gone()
                    fragmentCreateOrderTcComment.visible()
                    fragmentCreateOrderTcComment.cardText = comment
                }
            }.startedLaunch(viewLifecycleOwner)
            fragmentCreateOrderNcAddComment.setOnClickListener {
                viewModel.onAddCommentClicked()
            }
            fragmentCreateOrderTcComment.setOnClickListener {
                viewModel.onEditCommentClicked()
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
                viewModel.onDeferredTimeClicked()
            }
            fragmentCreateOrderTcDeferredTime.setOnClickListener {
                viewModel.onDeferredTimeClicked()
            }

            viewModel.totalCost.onEach { totalCost ->
                fragmentCreateOrderTvTotalValue.text = totalCost
            }.startedLaunch(viewLifecycleOwner)
            viewModel.deliveryCost.onEach { deliveryCost ->
                fragmentCreateOrderTvDeliveryValue.text = deliveryCost
            }.startedLaunch(viewLifecycleOwner)
            viewModel.newAmountToPay.onEach { newAmountToPay ->
                fragmentCreateOrderTvAmountToPayValue.text = newAmountToPay
            }.startedLaunch(viewLifecycleOwner)

            fragmentCreateOrderBtnCreateOrder.setOnClickListener {
                viewModel.onCreateOrderClicked()
            }
        }
        setFragmentResultListener(DEFERRED_TIME_REQUEST_KEY) { _, bundle ->
            bundle.getString(SELECTED_DEFERRED_TIME_KEY)?.let { selectedDeferredTime ->
                viewModel.onDeferredTimeSelected(selectedDeferredTime)
            }
        }
        setFragmentResultListener(COMMENT_REQUEST_KEY) { _, bundle ->
            bundle.getString(RESULT_COMMENT_KEY)?.let { comment ->
                viewModel.onCommentChanged(comment)
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