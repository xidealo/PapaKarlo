package com.bunbeauty.papakarlo.ui.profile

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.State
import com.bunbeauty.domain.enums.ActiveLines
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentOrderDetailsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.extensions.visible
import com.bunbeauty.papakarlo.presentation.profile.OrderDetailsViewModel
import com.bunbeauty.papakarlo.ui.adapter.CartProductAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>() {

    override val viewModel: OrderDetailsViewModel by viewModels { modelFactory }

    @Inject
    lateinit var cartProductAdapter: CartProductAdapter

    @Inject
    lateinit var resourcesProvider: IResourcesProvider
    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getOrder(
            OrderDetailsFragmentArgs.fromBundle(
                requireArguments()
            ).orderUuid)
        viewModel.orderState.onEach { state ->
            when (state) {
                is State.Success -> {
                    if (state.data != null) {
                        with(viewDataBinding) {
                            fragmentOrderDetailsChipStatus.text =
                                state.data!!.orderStatus
                            fragmentOrderDetailsChipStatus.setChipBackgroundColorResource(
                                state.data!!.orderStatusBackground
                            )
                            fragmentOrderDetailsTvTimeValue.text = state.data!!.time

                            fragmentOrderDetailsTvPickupMethodValue.text =
                                state.data!!.pickupMethod
                            fragmentOrderDetailsTvDeferredTimeValue.text =
                                state.data!!.deferredTime

                            fragmentOrderDetailsTvAddressValue.text =
                                state.data!!.address

                            fragmentOrderDetailsTvCommentValue.text =
                                state.data!!.comment

                            fragmentOrderDetailsTvDeliveryCostValue.text =
                                state.data!!.deliveryCost

                            fragmentOrderDetailsTvOrderOldTotalCost.text =
                                state.data!!.oldTotalCost

                            fragmentOrderDetailsTvOrderOldTotalCost.paintFlags =
                                fragmentOrderDetailsTvOrderOldTotalCost.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                            fragmentOrderDetailsTvOrderNewTotalCost.text =
                                state.data!!.newTotalCost

                            cartProductAdapter.canBeChanged = false
                            fragmentOrderDetailsRvProductList.adapter =
                                cartProductAdapter
                            cartProductAdapter.submitList(state.data!!.cartProducts)

                            if (state.data!!.deferredTime.isEmpty()) {
                                fragmentOrderDetailsTvDeferredTimeValue.gone()
                                fragmentOrderDetailsTvDeferredTime.gone()
                            }
                            if (state.data!!.comment.isEmpty()) {
                                fragmentOrderDetailsTvCommentValue.gone()
                                fragmentOrderDetailsTvComment.gone()
                            }
                            if (!state.data!!.isDelivery) {
                                fragmentOrderDetailsTvDeliveryCostValue.gone()
                                fragmentOrderDetailsTvDeliveryCost.gone()
                            }
                            fragmentOrderDetailsClMain.visible()
                            fragmentOrderDetailsClBottomCost.visible()
                            fragmentOrderDetailsPbLoading.gone()
                            setActiveLines(state.data!!.orderStatusActiveLine)
                        }
                    }
                }
                is State.Loading -> {
                    viewDataBinding.fragmentOrderDetailsClMain.gone()
                    viewDataBinding.fragmentOrderDetailsClBottomCost.gone()
                    viewDataBinding.fragmentOrderDetailsPbLoading.visible()
                }
                else -> Unit
            }
        }.startedLaunch(viewLifecycleOwner)
    }


    fun setActiveLines(activeLines: ActiveLines) {
        with(viewDataBinding) {
            when (activeLines) {
                ActiveLines.ZERO_LINE -> {
                    fragmentOrderDetailsMcvPreparing.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.light_grey)
                    fragmentOrderDetailsMcvAccepted.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.light_grey)
                    fragmentOrderDetailsMcvReady.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.light_grey)
                    fragmentOrderDetailsMcvDone.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.light_grey)
                }
                ActiveLines.ONE_LINE -> {
                    fragmentOrderDetailsMcvAccepted.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.light_grey)
                    fragmentOrderDetailsMcvPreparing.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.colorPrimary)
                    fragmentOrderDetailsMcvReady.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.light_grey)
                    fragmentOrderDetailsMcvDone.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.light_grey)
                }
                ActiveLines.TWO_LINE -> {
                    fragmentOrderDetailsMcvAccepted.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.colorPrimary)
                    fragmentOrderDetailsMcvPreparing.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.colorPrimary)
                    fragmentOrderDetailsMcvReady.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.light_grey)
                    fragmentOrderDetailsMcvDone.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.light_grey)
                }
                ActiveLines.THREE_LINE -> {
                    fragmentOrderDetailsMcvAccepted.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.colorPrimary)
                    fragmentOrderDetailsMcvPreparing.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.colorPrimary)
                    fragmentOrderDetailsMcvReady.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.colorPrimary)
                    fragmentOrderDetailsMcvDone.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.light_grey)
                }
                ActiveLines.FOUR_LINE -> {
                    fragmentOrderDetailsMcvAccepted.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.colorPrimary)
                    fragmentOrderDetailsMcvPreparing.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.colorPrimary)
                    fragmentOrderDetailsMcvReady.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.colorPrimary)
                    fragmentOrderDetailsMcvDone.backgroundTintList =
                        resourcesProvider.getColorTint(R.color.colorPrimary)
                }
            }
        }
    }
    override fun onDestroyView() {
        viewDataBinding.fragmentOrderDetailsRvProductList.adapter = null

        super.onDestroyView()
    }
}