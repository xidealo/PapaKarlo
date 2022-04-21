package com.bunbeauty.papakarlo.feature.auth.login

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import by.kirich1409.viewbindingdelegate.viewBinding
import core_common.Constants.PHONE_CODE
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.compose.element.CircularProgressBar
import com.bunbeauty.papakarlo.compose.element.EditText
import com.bunbeauty.papakarlo.compose.element.ErrorSnackbar
import com.bunbeauty.papakarlo.compose.element.MainButton
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentLoginBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.papakarlo.feature.auth.phone_verification.IPhoneVerificationUtil
import com.bunbeauty.papakarlo.feature.edit_text.EditTextType
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val phoneVerificationUtil: IPhoneVerificationUtil by inject()

    override val viewModel: LoginViewModel by stateViewModel(state = {
        arguments ?: bundleOf()
    })
    override val viewBinding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.setNotLoading()
        viewBinding.fragmentLoginCvMain.compose {
            val isLoading by viewModel.isLoading.collectAsState()
            LoginScreen(isLoading)
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

    @Composable
    private fun LoginScreen(isLoading: Boolean) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.surface)
                .padding(FoodDeliveryTheme.dimensions.mediumSpace),
        ) {
            val rememberScaffoldState = rememberScaffoldState()
            ErrorSnackbar(
                modifier = Modifier.align(Alignment.TopCenter),
                snackbarHostState = rememberScaffoldState.snackbarHostState
            )
            if (isLoading) {
                LoginLoadingScreen()
            } else {
                LoginSuccessScreen()
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
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_login_papa_karlo),
                    contentDescription = stringResource(R.string.description_login_logo)
                )
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
            MainButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                textStringId = R.string.action_login_continue
            ) {
                phoneError = viewModel.checkPhoneNumberError(phoneText.text)
                if (phoneError == null) {
                    phoneVerificationUtil.sendVerificationCode(
                        phone = phoneText.text,
                        activity = requireActivity()
                    )
                    viewModel.onNextClick()
                }
            }
        }
    }

    @Composable
    fun LoginLoadingScreen() {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressBar(modifier = Modifier.align(Alignment.Center))
        }
    }

    @Preview
    @Composable
    private fun LoginScreenPreview() {
        LoginScreen(false)
    }

    @Preview
    @Composable
    private fun LoginScreenLoadingPreview() {
        LoginScreen(true)
    }

}