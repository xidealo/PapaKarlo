package com.bunbeauty.papakarlo.feature.auth.screen.confirm

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.auth.model.ConfirmState
import com.bunbeauty.papakarlo.feature.auth.phone_verification.IPhoneVerificationUtil
import com.bunbeauty.papakarlo.feature.auth.ui.SmsEditText
import com.bunbeauty.papakarlo.feature.main.IMessageHost
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.core.parameter.parametersOf

class ConfirmFragment : BaseFragment(R.layout.layout_compose) {

    private val args: ConfirmFragmentArgs by navArgs()

    override val viewModel: ConfirmViewModel by stateViewModel(
        parameters = {
            parametersOf(
                args.successLoginDirection,
                args.phone,
            )
        }
    )

    override val viewBinding by viewBinding(LayoutComposeBinding::bind)

    private val phoneVerificationUtil: IPhoneVerificationUtil by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.root.setContentWithTheme {
            val confirmState by viewModel.confirmState.collectAsState()

            LaunchedEffect(confirmState.eventList) {
                handleEventList(confirmState.eventList)
            }
            ConfirmScreen(confirmState)
        }
        phoneVerificationUtil.authErrorEvent.startedLaunch { authErrorEvent ->
            viewModel.onVerificationError(authErrorEvent.error)
        }
        phoneVerificationUtil.authSuccessEvent.startedLaunch {
            viewModel.onSuccessVerified()
        }
    }

    @Composable
    private fun ConfirmScreen(confirmState: ConfirmState) {
        FoodDeliveryScaffold(
            backActionClick = {
                findNavController().popBackStack()
            },
            backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
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
                        )
                    }
                }
            }
        ) {
            if (confirmState.isCodeChecking) {
                LoadingScreen()
            } else {
                ConfirmScreenSuccess(confirmState)
            }
        }
    }

    @Composable
    private fun ConfirmScreenSuccess(confirmState: ConfirmState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
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
                text = stringResource(R.string.msg_confirm_phone_info) + confirmState.phoneNumber,
                textAlign = TextAlign.Center
            )
            SmsEditText(
                modifier = Modifier
                    .widthIn(max = FoodDeliveryTheme.dimensions.smsEditTextWidth)
                    .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
            ) { code ->
                viewModel.onCodeEntered()
                phoneVerificationUtil.verifyCode(code)
            }
            Spacer(modifier = Modifier.weight(1f))
            Spacer(
                modifier = Modifier
                    .height(FoodDeliveryTheme.dimensions.scrollScreenBottomSpace)
            )
        }
    }

    private fun handleEventList(eventList: List<ConfirmState.Event>) {
        eventList.forEach { event ->
            when (event) {
                is ConfirmState.Event.ShowErrorMessageEvent -> {
                    val messageId = when (event.error) {
                        ConfirmState.ConfirmError.SOMETHING_WENT_WRONG_ERROR -> R.string.error_something_went_wrong
                        ConfirmState.ConfirmError.WRONG_CODE_ERROR -> R.string.error_confirm_wrong_code
                    }
                    (activity as? IMessageHost)?.showErrorMessage(resources.getString(messageId))
                }
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConfirmScreenResendCodeDisableSuccessPreview() {
        FoodDeliveryTheme {
            ConfirmScreen(
                ConfirmState(
                    phoneNumber = "+7 (900) 900-90-90",
                    resendSeconds = 59,
                    isCodeChecking = false
                )
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConfirmScreenResendCodeEnableSuccessPreview() {
        FoodDeliveryTheme {
            ConfirmScreen(
                ConfirmState(
                    phoneNumber = "+7 (900) 900-90-90",
                    resendSeconds = 0,
                    isCodeChecking = false
                )
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConfirmScreenResendLoadingPreview() {
        FoodDeliveryTheme {
            ConfirmScreen(
                ConfirmState(
                    phoneNumber = "+7 (900) 900-90-90",
                    resendSeconds = 0,
                    isCodeChecking = true
                )
            )
        }
    }
}
