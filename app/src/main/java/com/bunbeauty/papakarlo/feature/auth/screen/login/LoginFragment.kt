package com.bunbeauty.papakarlo.feature.auth.screen.login

import android.os.Bundle
import android.view.View
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.LoadingButton
import com.bunbeauty.papakarlo.common.ui.element.textfield.FoodDeliveryTextField
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.main.IMessageHost
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragmentWithSharedViewModel(R.layout.layout_compose) {

    private val viewModel: LoginViewModel by viewModel()
    override val viewBinding by viewBinding(LayoutComposeBinding::bind)

    private val args: LoginFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.handleAction(LoginAction.Init)
        viewBinding.root.setContentWithTheme {
            val loginState by viewModel.loginState.collectAsStateWithLifecycle()
            LoginScreen(
                loginState = loginState,
                onAction = viewModel::handleAction
            )
            LaunchedEffect(loginState.eventList) {
                handleEventList(loginState.eventList)
            }
        }
    }

    private fun handleEventList(eventList: List<LoginEvent>) {
        eventList.forEach { event ->
            when (event) {
                is LoginEvent.NavigateToConfirmEvent -> {
                    findNavController().navigateSafe(
                        LoginFragmentDirections.toConfirmFragment(
                            event.phoneNumber,
                            args.successLoginDirection
                        )
                    )
                }

                LoginEvent.ShowTooManyRequestsErrorEvent -> {
                    (activity as? IMessageHost)?.showErrorMessage(
                        resources.getString(R.string.error_login_too_many_requests)
                    )
                }

                LoginEvent.ShowSomethingWentWrongErrorEvent -> {
                    (activity as? IMessageHost)?.showErrorMessage(
                        resources.getString(R.string.error_something_went_wrong)
                    )
                }

                LoginEvent.NavigateBack -> {
                    findNavController().popBackStack()
                }
            }
        }
        viewModel.handleAction(LoginAction.ConsumeEvents(eventList))
    }

    @Composable
    private fun LoginScreen(
        loginState: LoginState,
        onAction: (LoginAction) -> Unit,
    ) {
        FoodDeliveryScaffold(
            backActionClick = {
                onAction(LoginAction.BackClick)
            },
            backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
            actionButton = {
                LoadingButton(
                    modifier = Modifier
                        .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                    textStringId = R.string.action_login_continue,
                    isLoading = loginState.isLoading,
                    onClick = {
                        onAction(LoginAction.NextClick)
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
                    if (maxHeight > 200.dp) {
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

                val focusRequester = remember { FocusRequester() }
                FoodDeliveryTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                    focusRequester = focusRequester,
                    value = TextFieldValue(
                        text = loginState.phoneNumber,
                        selection = TextRange(loginState.phoneNumberCursorPosition)
                    ),
                    labelStringId = R.string.hint_login_phone,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done,
                    onValueChange = { value ->
                        onAction(LoginAction.ChangePhoneNumber(value.text, value.selection.start))
                    },
                    errorMessageId = if (loginState.hasPhoneError) {
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

    @Preview(showSystemUi = true)
    @Composable
    private fun LoginScreenPreview() {
        FoodDeliveryTheme {
            LoginScreen(
                loginState = LoginState(
                    phoneNumber = "+7 (900) 900-90-90",
                    phoneNumberCursorPosition = 18,
                    hasPhoneError = false,
                    isLoading = false,
                ),
                onAction = {}
            )
        }
    }
}
