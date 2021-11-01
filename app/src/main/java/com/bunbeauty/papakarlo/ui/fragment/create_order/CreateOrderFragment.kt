package com.bunbeauty.papakarlo.ui.fragment.create_order

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.bunbeauty.common.Constants.CAFE_ADDRESS_REQUEST_KEY
import com.bunbeauty.common.Constants.COMMENT_REQUEST_KEY
import com.bunbeauty.common.Constants.DEFERRED_TIME_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_CAFE_ADDRESS_KEY
import com.bunbeauty.common.Constants.RESULT_COMMENT_KEY
import com.bunbeauty.common.Constants.RESULT_USER_ADDRESS_KEY
import com.bunbeauty.common.Constants.SELECTED_DEFERRED_TIME_KEY
import com.bunbeauty.common.Constants.USER_ADDRESS_REQUEST_KEY
import com.bunbeauty.domain.util.validator.ITextValidator
import com.bunbeauty.papakarlo.databinding.FragmentCreateOrderBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.create_order.CreateOrderViewModel
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.custom.CustomSwitcher
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CreateOrderFragment : BaseFragment<FragmentCreateOrderBinding>() {

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    @Inject
    lateinit var textValidator: ITextValidator

    override val viewModel: CreateOrderViewModel by viewModels { viewModelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPickupMethod()
        setupAddress()
        setupComment()
        setupDeferredTime()
        setupBottomPrices()

        viewDataBinding.fragmentCreateOrderBtnCreateOrder.setOnClickListener {
            viewModel.onCreateOrderClicked()
        }
    }

    private fun setupPickupMethod() {
        viewDataBinding.run {
            fragmentCreateOrderCsDelivery.switchListener = object : CustomSwitcher.SwitchListener {
                override fun onSwitched(isLeft: Boolean) {
                    viewModel.onIsDeliveryChanged(isLeft)
                }
            }
            viewModel.isDelivery.onEach { isDelivery ->
                fragmentCreateOrderCsDelivery.isLeft = isDelivery
                fragmentCreateOrderTvDelivery.toggleVisibility(isDelivery)
                fragmentCreateOrderTvDeliveryValue.toggleVisibility(isDelivery)
            }.startedLaunch()
        }
    }

    private fun setupAddress() {
        viewDataBinding.run {
            viewModel.addressHint.onEach { addressHint ->
                fragmentCreateOrderNcAddAddress.cardText = addressHint
                fragmentCreateOrderTcAddress.hintText = addressHint
            }.startedLaunch()
            viewModel.address.onEach { address ->
                fragmentCreateOrderNcAddAddress.toggleVisibility(address == null)
                fragmentCreateOrderTcAddress.toggleVisibility(address != null)
                fragmentCreateOrderTcAddress.cardText = address ?: ""
            }.startedLaunch()
            fragmentCreateOrderNcAddAddress.setOnClickListener {
                viewModel.onAddAddressClicked()
            }
            fragmentCreateOrderTcAddress.setOnClickListener {
                viewModel.onAddressClicked()
            }
        }
        setFragmentResultListener(USER_ADDRESS_REQUEST_KEY) { _, bundle ->
            bundle.getString(RESULT_USER_ADDRESS_KEY)?.let { userAddressUuid ->
                viewModel.onUserAddressChanged(userAddressUuid)
            }
        }
        setFragmentResultListener(CAFE_ADDRESS_REQUEST_KEY) { _, bundle ->
            bundle.getString(RESULT_CAFE_ADDRESS_KEY)?.let { cafUuid ->
                viewModel.onCafeAddressChanged(cafUuid)
            }
        }
    }

    private fun setupComment() {
        viewDataBinding.run {
            viewModel.comment.onEach { comment ->
                fragmentCreateOrderNcAddComment.toggleVisibility(comment == null)
                fragmentCreateOrderTcComment.toggleVisibility(comment != null)
                fragmentCreateOrderTcComment.cardText = comment ?: ""
            }.startedLaunch()
            fragmentCreateOrderNcAddComment.setOnClickListener {
                viewModel.onAddCommentClicked()
            }
            fragmentCreateOrderTcComment.setOnClickListener {
                viewModel.onEditCommentClicked()
            }
        }

        setFragmentResultListener(COMMENT_REQUEST_KEY) { _, bundle ->
            bundle.getString(RESULT_COMMENT_KEY)?.let { comment ->
                viewModel.onCommentChanged(comment)
            }
        }
    }

    private fun setupDeferredTime() {
        viewDataBinding.run {
            viewModel.deferredTimeHint.onEach { deferredTimeHint ->
                fragmentCreateOrderTcDeferredTime.hintText = deferredTimeHint
            }.startedLaunch()
            viewModel.deferredTime.onEach { deferredTime ->
                fragmentCreateOrderTcDeferredTime.cardText = deferredTime
            }.startedLaunch()
            fragmentCreateOrderTcDeferredTime.setOnClickListener {
                viewModel.onDeferredTimeClicked()
            }
        }

        setFragmentResultListener(DEFERRED_TIME_REQUEST_KEY) { _, bundle ->
            val selectedDeferredTime = bundle.getLong(SELECTED_DEFERRED_TIME_KEY, -1)
            viewModel.onDeferredTimeSelected(selectedDeferredTime)
        }
    }

    private fun setupBottomPrices() {
        viewDataBinding.run {
            viewModel.totalCost.onEach { totalCost ->
                fragmentCreateOrderTvTotalValue.text = totalCost
            }.startedLaunch()
            viewModel.deliveryCost.onEach { deliveryCost ->
                fragmentCreateOrderTvDeliveryValue.text = deliveryCost
            }.startedLaunch()
            viewModel.amountToPay.onEach { newAmountToPay ->
                fragmentCreateOrderTvAmountToPayValue.text = newAmountToPay
            }.startedLaunch()
        }
    }
}