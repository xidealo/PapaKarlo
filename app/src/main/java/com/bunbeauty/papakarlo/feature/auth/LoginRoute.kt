package com.bunbeauty.papakarlo.feature.auth

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.LoadingButton
import com.bunbeauty.papakarlo.common.ui.element.textfield.FoodDeliveryTextField
import com.bunbeauty.papakarlo.common.ui.element.textfield.FoodDeliveryTextFieldDefaults
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.main.IMessageHost
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import com.bunbeauty.shared.presentation.login.Login
import com.bunbeauty.shared.presentation.login.LoginViewModel
import org.koin.androidx.compose.koinViewModel

private val logoBoxHeightLimit: Dp = 200.dp

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = koinViewModel(),
    successLoginDirection: SuccessLoginDirection,
    back: () -> Unit,
    goToConfirm: (phoneNumber: String, successLoginDirection: SuccessLoginDirection) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.onAction(Login.Action.Init)
    }
    val viewState by viewModel.dataState.collectAsStateWithLifecycle()

    val onAction = remember {
        { event: Login.Action ->
            viewModel.onAction(event)
        }
    }


    val effects by viewModel.events.collectAsStateWithLifecycle()
    val consumeEffects = remember {
        {
            viewModel.consumeEvents(effects)
        }
    }

    LoginEffect(
        effects = effects,
        back = back,
        successLoginDirection = successLoginDirection,
        goToConfirm = goToConfirm,
        consumeEffects = consumeEffects
    )
    LoginScreen(viewState = viewState, onAction = onAction)
}

@Composable
private fun LoginScreen(viewState: Login.ViewDataState, onAction: (Login.Action) -> Unit) {
    FoodDeliveryScaffold(
        backActionClick = {
            onAction(Login.Action.BackClick)
        },
        backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
        actionButton = {
            LoadingButton(
                modifier = Modifier
                    .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                textStringId = R.string.action_login_continue,
                isLoading = viewState.isLoading,
                onClick = {
                    onAction(Login.Action.NextClick)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(FoodDeliveryTheme.dimensions.mediumSpace),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BoxWithConstraints {
                val constraints = this
                if (constraints.maxHeight > logoBoxHeightLimit) {
                    Image(
                        modifier = Modifier.height(156.dp),
                        painter = painterResource(R.drawable.logo_medium),
                        contentDescription = stringResource(R.string.description_login_logo)
                    )
                }
            }
            Text(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                text = stringResource(R.string.msg_login_info),
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.mainColors.onSurface
            )

            val focusRequester = remember {
                FocusRequester()
            }
            FoodDeliveryTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                focusRequester = focusRequester,
                value = TextFieldValue(
                    text = viewState.phoneNumber,
                    selection = TextRange(viewState.phoneNumberCursorPosition)
                ),
                labelStringId = R.string.hint_login_phone,
                keyboardOptions = FoodDeliveryTextFieldDefaults.keyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                onValueChange = { value ->
                    onAction(Login.Action.ChangePhoneNumber(value.text, value.selection.start))
                },
                errorMessageId = if (viewState.hasPhoneError) {
                    R.string.error_login_phone
                } else {
                    null
                }
            )
            Spacer(
                modifier = Modifier.height(FoodDeliveryTheme.dimensions.scrollScreenBottomSpace)
            )

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        }
    }
}

@Composable
private fun LoginEffect(
    effects: List<Login.Event>,
    successLoginDirection: SuccessLoginDirection,
    back: () -> Unit,
    goToConfirm: (phoneNumber: String, successLoginDirection: SuccessLoginDirection) -> Unit,
    consumeEffects: () -> Unit,
) {

    val activity = LocalActivity.current
    LaunchedEffect(effects) {
        effects.forEach { effect ->
            when (effect) {
                is Login.Event.NavigateToConfirm -> {
                    goToConfirm(
                        effect.phoneNumber,
                        successLoginDirection
                    )
                }

                Login.Event.ShowTooManyRequestsError -> {
                    (activity as? IMessageHost)?.showErrorMessage(
                        activity.resources.getString(R.string.error_login_too_many_requests)
                    )
                }

                Login.Event.ShowSomethingWentWrongError -> {
                    (activity as? IMessageHost)?.showErrorMessage(
                        activity.resources.getString(R.string.error_something_went_wrong)
                    )
                }

                Login.Event.NavigateBack -> back()
            }
        }
        consumeEffects()
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    FoodDeliveryTheme {
        LoginScreen(
            viewState = Login.ViewDataState(
                phoneNumber = "+7 (900) 900-90-90",
                phoneNumberCursorPosition = 18,
                hasPhoneError = false,
                isLoading = false
            ),
            onAction = {}
        )
    }
}
