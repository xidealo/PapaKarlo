package com.bunbeauty.papakarlo.feature.auth

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseSingleStateComposeFragment
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.feature.auth.ui.SmsEditText
import com.bunbeauty.papakarlo.feature.main.IMessageHost
import com.bunbeauty.shared.presentation.confirm.Confirm
import com.bunbeauty.shared.presentation.confirm.ConfirmViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfirmFragment : BaseSingleStateComposeFragment<Confirm.ViewDataState, Confirm.Action, Confirm.Event>() {

    private val args: ConfirmFragmentArgs by navArgs()

    override val viewModel: ConfirmViewModel by viewModel()

    override val viewBinding by viewBinding(LayoutComposeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onAction(Confirm.Action.Init(args.phoneNumber, args.successLoginDirection))
    }

    @Composable
    override fun Screen(viewState: Confirm.ViewDataState, onAction: (Confirm.Action) -> Unit) {
        FoodDeliveryScaffold(
            backActionClick = {
                onAction(Confirm.Action.BackClick)
            },
            backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
            actionButton = {
                if (!viewState.isLoading) {
                    val buttonText = if (viewState.isResendEnable) {
                        stringResource(R.string.msg_request_code)
                    } else {
                        stringResource(R.string.msg_request_code_sec, viewState.resendSeconds)
                    }
                    MainButton(
                        modifier = Modifier
                            .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                        text = buttonText,
                        isEnabled = viewState.isResendEnable
                    ) {
                        onAction(Confirm.Action.ResendCode)
                    }
                }
            }
        ) {
            if (viewState.isLoading) {
                LoadingScreen()
            } else {
                ConfirmScreenSuccess(
                    state = viewState,
                    onAction = onAction
                )
            }
        }
    }

    override fun handleEvent(event: Confirm.Event) {
        when (event) {
            Confirm.Event.ShowTooManyRequestsError -> {
                (activity as? IMessageHost)?.showErrorMessage(
                    resources.getString(R.string.error_login_too_many_requests)
                )
            }

            Confirm.Event.ShowNoAttemptsError -> {
                (activity as? IMessageHost)?.showErrorMessage(
                    resources.getString(R.string.error_no_attempts)
                )
            }

            Confirm.Event.ShowInvalidCodeError -> {
                (activity as? IMessageHost)?.showErrorMessage(
                    resources.getString(R.string.error_invalid_code)
                )
            }

            Confirm.Event.ShowAuthSessionTimeoutError -> {
                (activity as? IMessageHost)?.showErrorMessage(
                    resources.getString(R.string.error_code_confirmation_timeout)
                )
            }

            Confirm.Event.ShowSomethingWentWrongError -> {
                (activity as? IMessageHost)?.showErrorMessage(
                    resources.getString(R.string.error_something_went_wrong)
                )
            }

            Confirm.Event.NavigateBackToProfile -> {
                findNavController().navigateSafe(ConfirmFragmentDirections.backToProfileFragment())
            }

            Confirm.Event.NavigateToCreateOrder -> {
                findNavController().navigateSafe(ConfirmFragmentDirections.toCreateOrderFragment())
            }

            Confirm.Event.NavigateBack -> {
                findNavController().popBackStack()
            }
        }
    }

    @Composable
    private fun ConfirmScreenSuccess(state: Confirm.ViewDataState, onAction: (Confirm.Action) -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(FoodDeliveryTheme.dimensions.mediumSpace),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.msg_confirm_enter_code, state.phoneNumber),
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
                textAlign = TextAlign.Center
            )
            SmsEditText(
                modifier = Modifier
                    .widthIn(max = FoodDeliveryTheme.dimensions.smsEditTextWidth)
                    .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
            ) { code ->
                onAction(Confirm.Action.CheckCode(code))
            }
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(FoodDeliveryTheme.dimensions.scrollScreenBottomSpace))
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConfirmScreenResendCodeDisableSuccessPreview() {
        FoodDeliveryTheme {
            Screen(
                viewState = Confirm.ViewDataState(
                    phoneNumber = "+7 (900) 900-90-90",
                    resendSeconds = 59,
                    isLoading = false
                ),
                onAction = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConfirmScreenResendCodeEnableSuccessPreview() {
        FoodDeliveryTheme {
            Screen(
                viewState = Confirm.ViewDataState(
                    phoneNumber = "+7 (900) 900-90-90",
                    resendSeconds = 0,
                    isLoading = false
                ),
                onAction = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConfirmScreenResendLoadingPreview() {
        FoodDeliveryTheme {
            Screen(
                viewState = Confirm.ViewDataState(
                    phoneNumber = "+7 (900) 900-90-90",
                    resendSeconds = 0,
                    isLoading = true
                ),
                onAction = {}
            )
        }
    }
}
