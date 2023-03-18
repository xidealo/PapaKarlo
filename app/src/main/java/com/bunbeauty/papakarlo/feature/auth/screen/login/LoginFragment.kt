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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.core.os.bundleOf
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.text_field.FoodDeliveryTextField
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.toolbar.FoodDeliveryToolbarScreen
import com.bunbeauty.papakarlo.databinding.FragmentComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.auth.phone_verification.IPhoneVerificationUtil
import com.bunbeauty.shared.Constants.PHONE_CODE
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class LoginFragment : BaseFragment(R.layout.fragment_compose) {

    private val phoneVerificationUtil: IPhoneVerificationUtil by inject()

    override val viewModel: LoginViewModel by stateViewModel(state = {
        arguments ?: bundleOf()
    })
    override val viewBinding by viewBinding(FragmentComposeBinding::bind)

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setSuccessState()
        viewBinding.root.setContentWithTheme {
            val loginState by viewModel.loginState.collectAsStateWithLifecycle()
            LoginScreen(loginState)
            LaunchedEffect(loginState.eventList) {
                handleEventList(loginState.eventList)
            }
        }
        phoneVerificationUtil.codeSentEvent.startedLaunch { codeSentEvent ->
            viewModel.onCodeSent(
                codeSentEvent.phone,
                codeSentEvent.verificationId,
                codeSentEvent.token
            )
        }
        phoneVerificationUtil.authErrorEvent.startedLaunch { authErrorEvent ->
            viewModel.onVerificationError(authErrorEvent.error)
        }
        phoneVerificationUtil.authSuccessEvent.startedLaunch {
            viewModel.onSuccessVerified()
        }
    }

    private fun handleEventList(eventList: List<LoginState.Event>) {
        eventList.forEach { event ->
            when (event) {
                is LoginState.Event.NavigateToCreateOrderFragment -> {
                    findNavController().navigate(LoginFragmentDirections.toCreateOrderFragment())
                }
                is LoginState.Event.NavigateBackToProfileFragment -> {
                    findNavController().navigate(LoginFragmentDirections.backToProfileFragment())
                }
                is LoginState.Event.NavigateToConfirmFragment -> {
                    findNavController().navigate(
                        LoginFragmentDirections.toConfirmFragment(
                            event.phone,
                            event.verificationId,
                            event.resendToken,
                            event.successLoginDirection
                        )
                    )
                }
                is LoginState.Event.SendCode -> {
                    phoneVerificationUtil.sendVerificationCode(
                        phone = event.phone,
                        activity = requireActivity()
                    )
                }
            }
        }
        viewModel.consumeEventList(eventList)
    }

    @Composable
    private fun LoginScreen(loginState: LoginState) {
        FoodDeliveryToolbarScreen(
            backActionClick = {
                findNavController().popBackStack()
            },
            backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
            actionButton = {
                if (loginState.state == LoginState.State.Success) {
                    MainButton(
                        modifier = Modifier
                            .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                        textStringId = R.string.action_login_continue
                    ) {
                        viewModel.onNextClick()
                    }
                }
            }
        ) {
            when (loginState.state) {
                is LoginState.State.Loading -> {
                    LoadingScreen()
                }
                is LoginState.State.Success -> {
                    LoginSuccessScreen(loginState)
                }
                is LoginState.State.Error -> {
                    ErrorScreen(
                        mainTextId = R.string.common_error,
                        extraTextId = R.string.internet_error,
                        onClick = viewModel::setSuccessState,
                    )
                }
            }
        }
    }

    @Composable
    private fun LoginSuccessScreen(loginState: LoginState) {
        var phoneText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(
                TextFieldValue(
                    text = PHONE_CODE,
                    selection = TextRange(PHONE_CODE.length)
                )
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BoxWithConstraints {
                if (maxHeight > 240.dp) {
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
                value = phoneText,
                labelStringId = R.string.hint_login_phone,
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done,
                onValueChange = { value ->
                    phoneText = TextFieldValue(
                        viewModel.formatPhoneNumber(value.text),
                        selection = TextRange(
                            viewModel.getNewPosition(
                                value.text,
                                value.selection.end
                            )
                        )
                    )
                    viewModel.onPhoneTextChanged(phoneText.text)
                },
                errorMessageId = if (loginState.hasPhoneError) {
                    R.string.error_login_phone
                } else {
                    null
                }
            )
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
            Spacer(
                modifier = Modifier
                    .height(FoodDeliveryTheme.dimensions.scrollScreenBottomSpace)
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun LoginScreenPreview() {
        LoginScreen(
            LoginState(
                state = LoginState.State.Success
            )
        )
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun LoginScreenLoadingPreview() {
        LoginScreen(
            LoginState(
                state = LoginState.State.Loading
            )
        )
    }
}
