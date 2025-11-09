package com.bunbeauty.shared.ui.screen.auth

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import papakarlo.shared.generated.resources.Res
import com.bunbeauty.shared.ui.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.shared.ui.common.ui.element.button.MainButton
import com.bunbeauty.shared.ui.common.ui.screen.LoadingScreen
import com.bunbeauty.shared.ui.theme.FoodDeliveryTheme
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import org.koin.compose.viewmodel.koinViewModel
import com.bunbeauty.shared.presentation.confirm.Confirm
import com.bunbeauty.shared.presentation.confirm.ConfirmViewModel
import com.bunbeauty.shared.ui.screen.auth.ui.SmsEditText
import org.jetbrains.compose.resources.getString
import papakarlo.shared.generated.resources.error_code_confirmation_timeout
import papakarlo.shared.generated.resources.error_invalid_code
import papakarlo.shared.generated.resources.error_login_too_many_requests
import papakarlo.shared.generated.resources.error_no_attempts
import papakarlo.shared.generated.resources.error_something_went_wrong
import papakarlo.shared.generated.resources.msg_confirm_enter_code
import papakarlo.shared.generated.resources.msg_request_code
import papakarlo.shared.generated.resources.msg_request_code_sec

@Composable
fun ConfirmRoute(
    viewModel: ConfirmViewModel = koinViewModel(),
    phoneNumber: String,
    successLoginDirection: SuccessLoginDirection,
    back: () -> Unit,
    goBackToProfileFragment: () -> Unit,
    goToCreateOrderFragment: () -> Unit,
    showInfoMessage: (String) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.onAction(Confirm.Action.Init(phoneNumber, successLoginDirection))
    }

    val viewState by viewModel.dataState.collectAsStateWithLifecycle()

    val onAction =
        remember {
            { event: Confirm.Action ->
                viewModel.onAction(event)
            }
        }

    val effects by viewModel.events.collectAsStateWithLifecycle()
    val consumeEffects =
        remember {
            {
                viewModel.consumeEvents(effects)
            }
        }

    ConfirmEffect(
        effects = effects,
        back = back,
        goBackToProfileFragment = goBackToProfileFragment,
        goToCreateOrderFragment = goToCreateOrderFragment,
        consumeEffects = consumeEffects,
        showInfoMessage = showInfoMessage,
        showErrorMessage = showErrorMessage,
        
    )
    ConfirmScreen(viewState = viewState, onAction = onAction)
}

@Composable
fun ConfirmScreen(
    viewState: Confirm.ViewDataState,
    onAction: (Confirm.Action) -> Unit,
) {
    FoodDeliveryScaffold(
        backActionClick = {
            onAction(Confirm.Action.BackClick)
        },
        backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
        actionButton = {
            if (!viewState.isLoading) {
                val buttonText =
                    if (viewState.isResendEnable) {
                        stringResource(Res.string.msg_request_code)
                    } else {
                        stringResource(Res.string.msg_request_code_sec, viewState.resendSeconds)
                    }
                MainButton(
                    modifier =
                        Modifier
                            .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                    text = buttonText,
                    enabled = viewState.isResendEnable,
                ) {
                    onAction(Confirm.Action.ResendCode)
                }
            }
        },
    ) {
        if (viewState.isLoading) {
            LoadingScreen()
        } else {
            ConfirmScreenSuccess(
                state = viewState,
                onAction = onAction,
            )
        }
    }
}

@Composable
fun ConfirmEffect(
    effects: List<Confirm.Event>,
    back: () -> Unit,
    goBackToProfileFragment: () -> Unit,
    goToCreateOrderFragment: () -> Unit,
    consumeEffects: () -> Unit,
    showInfoMessage: (String) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    LaunchedEffect(effects) {
        effects.forEach { effect ->
            when (effect) {
                Confirm.Event.ShowTooManyRequestsError -> {
                    showErrorMessage(
                        getString(Res.string.error_login_too_many_requests)
                    )
                }

                Confirm.Event.ShowNoAttemptsError -> {
                    showErrorMessage(
                        getString(Res.string.error_no_attempts)
                    )
                }

                Confirm.Event.ShowInvalidCodeError -> {
                    showErrorMessage(
                        getString(Res.string.error_invalid_code)
                    )
                }

                Confirm.Event.ShowAuthSessionTimeoutError -> {
                    showErrorMessage(
                        getString(Res.string.error_code_confirmation_timeout)
                    )
                }

                Confirm.Event.ShowSomethingWentWrongError -> {
                    showErrorMessage(
                        getString(Res.string.error_something_went_wrong)
                    )
                }

                Confirm.Event.NavigateBackToProfile -> goBackToProfileFragment()

                Confirm.Event.NavigateToCreateOrder -> goToCreateOrderFragment()

                Confirm.Event.NavigateBack -> back()
            }
        }
        consumeEffects()
    }
}

@Composable
private fun ConfirmScreenSuccess(
    state: Confirm.ViewDataState,
    onAction: (Confirm.Action) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(FoodDeliveryTheme.dimensions.mediumSpace),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(Res.string.msg_confirm_enter_code, state.phoneNumber),
            style = FoodDeliveryTheme.typography.bodyLarge,
            color = FoodDeliveryTheme.colors.mainColors.onSurface,
            textAlign = TextAlign.Center,
        )
        SmsEditText(
            modifier =
                Modifier
                    .widthIn(max = FoodDeliveryTheme.dimensions.smsEditTextWidth)
                    .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
        ) { code ->
            onAction(Confirm.Action.CheckCode(code))
        }
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(FoodDeliveryTheme.dimensions.scrollScreenBottomSpace))
    }
}

@Preview(showBackground = true)
@Composable
private fun ConfirmScreenResendCodeDisableSuccessPreview() {
    FoodDeliveryTheme {
        ConfirmScreen(
            viewState =
                Confirm.ViewDataState(
                    phoneNumber = "+7 (900) 900-90-90",
                    resendSeconds = 59,
                    isLoading = false,
                ),
            onAction = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ConfirmScreenResendCodeEnableSuccessPreview() {
    FoodDeliveryTheme {
        ConfirmScreen(
            viewState =
                Confirm.ViewDataState(
                    phoneNumber = "+7 (900) 900-90-90",
                    resendSeconds = 0,
                    isLoading = false,
                ),
            onAction = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ConfirmScreenResendLoadingPreview() {
    FoodDeliveryTheme {
        ConfirmScreen(
            viewState =
                Confirm.ViewDataState(
                    phoneNumber = "+7 (900) 900-90-90",
                    resendSeconds = 0,
                    isLoading = true,
                ),
            onAction = {},
        )
    }
}
