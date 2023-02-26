package com.bunbeauty.papakarlo.feature.auth.screen.login

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
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
import com.bunbeauty.papakarlo.common.ui.element.EditText
import com.bunbeauty.papakarlo.common.ui.element.MainButton
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentLoginBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.papakarlo.feature.auth.phone_verification.IPhoneVerificationUtil
import com.bunbeauty.papakarlo.feature.edit_text.model.EditTextType
import com.bunbeauty.shared.Constants.PHONE_CODE
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val phoneVerificationUtil: IPhoneVerificationUtil by inject()

    override val viewModel: LoginViewModel by stateViewModel(state = {
        arguments ?: bundleOf()
    })
    override val viewBinding by viewBinding(FragmentLoginBinding::bind)

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setSuccessState()
        viewBinding.fragmentLoginCvMain.compose {
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
        when (loginState.state) {
            is LoginState.State.Loading -> {
                LoadingScreen(background = FoodDeliveryTheme.colors.surface)
            }
            is LoginState.State.Success -> {
                LoginSuccessScreen()
            }
            is LoginState.State.Error -> {
                ErrorScreen(
                    mainTextId = R.string.common_error,
                    extraTextId = R.string.internet_error
                ) {
                    viewModel.setSuccessState()
                }
            }
        }
    }

    @Composable
    private fun LoginSuccessScreen() {
        var phoneText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(
                TextFieldValue(
                    text = PHONE_CODE,
                    selection = TextRange(PHONE_CODE.length)
                )
            )
        }
        var phoneError: Int? by rememberSaveable {
            mutableStateOf(null)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.surface)
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BoxWithConstraints {
                        if (maxHeight > 240.dp) {
                            Image(
                                painter = painterResource(R.drawable.logo_login_papa_k),
                                contentDescription = stringResource(R.string.description_login_logo)
                            )
                        }
                    }
                    Text(
                        modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                        text = stringResource(R.string.msg_login_info),
                        style = FoodDeliveryTheme.typography.body1,
                        color = FoodDeliveryTheme.colors.onSurface
                    )
                    EditText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                        labelStringId = R.string.hint_login_phone,
                        textFieldValue = phoneText,
                        editTextType = EditTextType.PHONE,
                        onTextChanged = { changedValue ->
                            phoneText = TextFieldValue(
                                text = viewModel.formatPhoneNumber(changedValue.text),
                                selection = TextRange(
                                    viewModel.getNewPosition(
                                        changedValue.text,
                                        changedValue.selection.start
                                    )
                                )
                            )
                        },
                        isLast = true,
                        focus = true,
                        errorMessageId = phoneError,
                    )
                }
            }
            MainButton(textStringId = R.string.action_login_continue) {
                phoneError = viewModel.checkPhoneNumberError(phoneText.text)
                if (phoneError == null) {
                    viewModel.onNextClick(phone = phoneText.text)
                }
            }
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
