package com.bunbeauty.papakarlo.feature.auth.screen.confirm

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.feature.auth.ui.SmsEditText
import com.bunbeauty.papakarlo.common.ui.element.CircularProgressBar
import com.bunbeauty.papakarlo.common.ui.element.MainButton
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentConfirmBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.papakarlo.feature.auth.model.Confirmation
import com.bunbeauty.papakarlo.feature.auth.phone_verification.IPhoneVerificationUtil
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

        viewBinding.fragmentConfirmCvMain.compose {
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

    @Composable
    private fun ConfirmScreenSuccess(confirmation: Confirmation) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.msg_confirm_enter_code))
                Text(
                    modifier = Modifier
                        .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    text = stringResource(R.string.msg_confirm_phone_info) + confirmation.phoneNumber
                )
                SmsEditText(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.8f)
                        .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
                ) { code ->
                    viewModel.onCodeEntered()
                    phoneVerificationUtil.verifyCode(
                        code = code,
                        verificationId = confirmation.verificationId
                    )
                }
            }
            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!confirmation.isResendEnable) {
                    Text(
                        text = stringResource(R.string.msg_confirm_code_info)
                                + confirmation.resendSeconds
                                + stringResource(R.string.msg_confirm_seconds)
                    )
                }
                MainButton(
                    modifier = Modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace),
                    textStringId = R.string.action_confirm_resend_code,
                    isEnabled = confirmation.isResendEnable
                ) {
                    viewModel.onResendCodeClicked()
                    phoneVerificationUtil.resendVerificationCode(
                        phone = confirmation.formattedPhoneNumber,
                        activity = requireActivity(),
                        token = confirmation.resendToken
                    )
                }
            }
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