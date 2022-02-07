package com.bunbeauty.papakarlo.feature.create_order

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.common.Constants.CAFE_ADDRESS_REQUEST_KEY
import com.bunbeauty.common.Constants.COMMENT_REQUEST_KEY
import com.bunbeauty.common.Constants.DEFERRED_TIME_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_CAFE_ADDRESS_KEY
import com.bunbeauty.common.Constants.RESULT_COMMENT_KEY
import com.bunbeauty.common.Constants.RESULT_USER_ADDRESS_KEY
import com.bunbeauty.common.Constants.SELECTED_DEFERRED_TIME_KEY
import com.bunbeauty.common.Constants.USER_ADDRESS_REQUEST_KEY
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.databinding.FragmentCreateOrderBinding
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateOrderFragment : BaseFragment(R.layout.fragment_create_order) {

    override val viewModel: CreateOrderViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentCreateOrderBinding::bind)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPickupMethod()
        setupAddress()
        setupComment()
        setupDeferredTime()
        setupBottomPrices()

        viewBinding.run {
            fragmentCreateOrderBtnCreateOrder.setOnClickListener {
                viewModel.onCreateOrderClicked()
            }
            viewModel.isLoading.startedLaunch { isLoading ->
                fragmentCreateOrderBtnCreateOrder.toggleLoading(isLoading)
                fragmentCreateOrderBtnCreateOrder.toggleEnabling(!isLoading)
                fragmentCreateOrderCsDelivery.isSwitcherEnabled = !isLoading
                fragmentCreateOrderTcAddress.isClickable = !isLoading
                fragmentCreateOrderNcAddComment.isClickable = !isLoading
                fragmentCreateOrderTcComment.isClickable = !isLoading
                fragmentCreateOrderTcDeferredTime.isClickable = !isLoading
            }
        }
    }

    private fun setupPickupMethod() {
        viewBinding.run {
            fragmentCreateOrderCsDelivery.switchListener = object : CustomSwitcher.SwitchListener {
                override fun onSwitched(isLeft: Boolean) {
                    viewModel.onIsDeliveryChanged(isLeft)
                }
            }
            viewModel.isDelivery.startedLaunch { isDelivery ->
                fragmentCreateOrderCsDelivery.isLeft = isDelivery
                fragmentCreateOrderTvDelivery.toggleVisibility(isDelivery)
                fragmentCreateOrderTvDeliveryValue.toggleVisibility(isDelivery)
            }
        }
    }

    private fun setupAddress() {
        viewBinding.run {
            viewModel.addressHint.startedLaunch { addressHint ->
                fragmentCreateOrderNcAddAddress.cardText = addressHint
                fragmentCreateOrderTcAddress.hintText = addressHint
            }
            viewModel.address.startedLaunch { address ->
                fragmentCreateOrderNcAddAddress.toggleVisibility(address == null)
                fragmentCreateOrderTcAddress.toggleVisibility(address != null)
                fragmentCreateOrderTcAddress.cardText = address ?: ""
            }
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
        viewBinding.run {
            viewModel.comment.startedLaunch { comment ->
                fragmentCreateOrderNcAddComment.toggleVisibility(comment == null)
                fragmentCreateOrderTcComment.toggleVisibility(comment != null)
                fragmentCreateOrderTcComment.cardText = comment ?: ""
            }
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
        viewBinding.run {
            viewModel.deferredTimeHint.startedLaunch { deferredTimeHint ->
                fragmentCreateOrderTcDeferredTime.hintText = deferredTimeHint
            }
            viewModel.deferredTime.startedLaunch { deferredTime ->
                fragmentCreateOrderTcDeferredTime.cardText = deferredTime
            }
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
        viewBinding.run {
            viewModel.totalCost.startedLaunch { totalCost ->
                fragmentCreateOrderTvTotalValue.text = totalCost
            }
            viewModel.deliveryCost.startedLaunch { deliveryCost ->
                fragmentCreateOrderTvDeliveryValue.text = deliveryCost
            }
            viewModel.amountToPay.startedLaunch { newAmountToPay ->
                fragmentCreateOrderTvAmountToPayValue.text = newAmountToPay
            }
        }
    }
}