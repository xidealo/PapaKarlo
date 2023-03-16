package com.bunbeauty.papakarlo.feature.auth.screen.confirm

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.ui.element.CircularProgressBar
import com.bunbeauty.papakarlo.common.ui.element.MainButton
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.toolbar.FoodDeliveryToolbarScreen
import com.bunbeauty.papakarlo.databinding.FragmentConfirmBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.auth.model.Confirmation
import com.bunbeauty.papakarlo.feature.auth.phone_verification.IPhoneVerificationUtil
import com.bunbeauty.papakarlo.feature.auth.ui.SmsEditText
import com.google.firebase.auth.PhoneAuthProvider
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class ConfirmFragment : BaseFragment(R.layout.fragment_confirm) {

    override val viewModel: ConfirmViewModel by stateViewModel(state = {
        arguments ?: bundleOf()
    })

    override val viewBinding by viewBinding(FragmentConfirmBinding::bind)

    private val phoneVerificationUtil: IPhoneVerificationUtil by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentConfirmCvMain.setContentWithTheme {
            val confirmState by viewModel.confirmState.collectAsState()
            ConfirmScreen(confirmState)
        }
        phoneVerificationUtil.codeSentEvent.startedLaunch { codeSentEvent ->
            viewModel.onCodeSent(codeSentEvent.verificationId, codeSentEvent.token)
        }
        phoneVerificationUtil.authErrorEvent.startedLaunch { authErrorEvent ->
            viewModel.onVerificationError(authErrorEvent.error)
        }
        phoneVerificationUtil.authSuccessEvent.startedLaunch {
            viewModel.onSuccessVerified()
        }
    }

    @Composable
    private fun ConfirmScreen(confirmState: Confirmation) {
        FoodDeliveryToolbarScreen(
            backActionClick = {
                findNavController().popBackStack()
            },
            actionButton = {
                if (!confirmState.isCodeChecking) {
                    val buttonText = if (confirmState.isResendEnable) {
                        stringResource(R.string.msg_request_code)
                    } else {
                        stringResource(R.string.msg_request_code_sec, confirmState.resendSeconds)
                    }
                    MainButton(
                        modifier = Modifier
                            .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                        text = buttonText,
                        isEnabled = confirmState.isResendEnable
                    ) {
                        viewModel.onResendCodeClicked()
                        phoneVerificationUtil.resendVerificationCode(
                            phone = confirmState.formattedPhoneNumber,
                            activity = requireActivity(),
                            token = confirmState.resendToken
                        )
                    }
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(FoodDeliveryTheme.colors.surface)
                    .padding(FoodDeliveryTheme.dimensions.mediumSpace)
            ) {
                if (confirmState.isCodeChecking) {
                    CircularProgressBar(modifier = Modifier.align(Alignment.Center))
                } else {
                    ConfirmScreenSuccess(confirmState)
                }
            }
        }
    }

    @Composable
    private fun ConfirmScreenSuccess(confirmation: Confirmation) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.msg_confirm_enter_code),
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                text = stringResource(R.string.msg_confirm_phone_info) + confirmation.phoneNumber,
                textAlign = TextAlign.Center
            )
            SmsEditText(
                modifier = Modifier
                    .widthIn(max = FoodDeliveryTheme.dimensions.smsEditTextWidth)
                    .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
            ) { code ->
                viewModel.onCodeEntered()
                phoneVerificationUtil.verifyCode(
                    code = code,
                    verificationId = confirmation.verificationId
                )
            }
            Spacer(
                modifier = Modifier
                    .height(FoodDeliveryTheme.dimensions.scrollScreenBottomSpace)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }

    @Preview
    @Composable
    private fun ConfirmScreenResendCodeDisableSuccessPreview() {
        ConfirmScreen(
            Confirmation(
                phoneNumber = "+7 (900) 900-90-90",
                resendToken = PhoneAuthProvider.ForceResendingToken.zza(),
                verificationId = "",
                resendSeconds = 59,
                isCodeChecking = false
            )
        )
    }

    @Preview
    @Composable
    private fun ConfirmScreenResendCodeEnableSuccessPreview() {
        ConfirmScreen(
            Confirmation(
                phoneNumber = "+7 (900) 900-90-90",
                resendToken = PhoneAuthProvider.ForceResendingToken.zza(),
                verificationId = "",
                resendSeconds = 0,
                isCodeChecking = false
            )
        )
    }

    @Preview
    @Composable
    private fun ConfirmScreenResendLoadingPreview() {
        ConfirmScreen(
            Confirmation(
                phoneNumber = "+7 (900) 900-90-90",
                resendToken = PhoneAuthProvider.ForceResendingToken.zza(),
                verificationId = "",
                resendSeconds = 0,
                isCodeChecking = true
            )
        )
    }
}
