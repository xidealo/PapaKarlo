package com.bunbeauty.papakarlo.feature.address.screen.create_address

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.ui.element.EditText
import com.bunbeauty.papakarlo.common.ui.element.LoadingButton
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentCreateAddressBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.papakarlo.extensions.showSnackbar
import com.bunbeauty.papakarlo.feature.address.ui.auto_complete_text_field.AutoCompleteEditText
import com.bunbeauty.papakarlo.feature.edit_text.model.EditTextType
import com.bunbeauty.shared.domain.interactor.address.CreateAddressUseCase
import com.bunbeauty.shared.domain.interactor.street.GetStreetsUseCase
import com.bunbeauty.shared.presentation.create_address.CreateAddressState
import com.bunbeauty.shared.presentation.create_address.CreateAddressViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateAddressFragment : BaseFragmentWithSharedViewModel(R.layout.fragment_create_address) {

    val viewModel: CreateAddressViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentCreateAddressBinding::bind)

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getStreetList()
        viewBinding.fragmentCreateAddressCvMain.compose {
            val streetListState by viewModel.streetListState.collectAsStateWithLifecycle()
            CreateAddressScreen(streetListState)
            LaunchedEffect(streetListState.eventList) {
                handleEventList(streetListState.eventList)
            }
        }
    }

    @Composable
    private fun CreateAddressScreen(createAddressState: CreateAddressState) {
        when (val state = createAddressState.state) {
            is CreateAddressState.State.Success -> CreateAddressSuccessScreen(createAddressState)
            is CreateAddressState.State.Loading -> LoadingScreen()
            is CreateAddressState.State.Error -> {
                when (state.throwable) {
                    is GetStreetsUseCase.NoSelectedCityUuidThrow, CreateAddressUseCase.NoSelectedCityUuidThrow -> {
                        ErrorScreen(
                            mainTextId = R.string.error_create_address_no_selected_city,
                            extraTextId = R.string.error_create_address_no_selected_city_help
                        ) {
                            viewModel.getStreetList()
                        }
                    }
                    is GetStreetsUseCase.NoUserUuidThrow, CreateAddressUseCase.NoUserUuidThrow -> {
                        ErrorScreen(
                            mainTextId = R.string.error_create_address_no_selected_city,
                            extraTextId = R.string.error_create_address_no_selected_city_help
                        ) {
                            viewModel.getStreetList()
                        }
                    }
                    is CreateAddressUseCase.NoStreetByNameAndCityUuidThrow -> {
                        ErrorScreen(
                            mainTextId = R.string.error_create_address_no_street_by_city_uuid_and_name,
                            extraTextId = R.string.error_create_address_no_street_by_city_uuid_and_name_help
                        ) {
                            viewModel.getStreetList()
                        }
                    }
                    else -> {
                        ErrorScreen(
                            mainTextId = R.string.common_error,
                            extraTextId = R.string.internet_error
                        ) {
                            viewModel.getStreetList()
                        }
                    }
                }
            }
            is CreateAddressState.State.Empty -> {
                ErrorScreen(mainTextId = R.string.error_create_address_loading) {
                    viewModel.getStreetList()
                }
            }
        }
    }

    @Composable
    private fun CreateAddressSuccessScreen(state: CreateAddressState) {
        val focusManager = LocalFocusManager.current
        var streetText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(""))
        }
        var streetError: Int? by rememberSaveable {
            mutableStateOf(null)
        }
        var houseText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(""))
        }
        var flatText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(""))
        }
        var entranceText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(""))
        }
        var floorText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(""))
        }
        var commentText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(""))
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
            ) {
                AutoCompleteEditText(
                    modifier = Modifier.fillMaxWidth(),
                    textFieldValue = streetText,
                    labelStringId = R.string.hint_create_address_street,
                    editTextType = EditTextType.TEXT,
                    focus = true,
                    errorMessageId = if (state.hasStreetError) {
                        R.string.error_create_address_street
                    } else {
                        null
                    },
                    list = state.streetItemList,
                ) { changedValue ->
                    streetText = changedValue
                    viewModel.onStreetTextChanged(streetText.text)
                }
                EditText(
                    modifier = Modifier.fillMaxWidth(),
                    textFieldValue = houseText,
                    labelStringId = R.string.hint_create_address_house,
                    editTextType = EditTextType.TEXT,
                    errorMessageId = when (state.hasHouseError) {
                        CreateAddressState.FieldError.INCORRECT -> R.string.error_create_address_house
                        CreateAddressState.FieldError.LENGTH -> R.string.error_create_address_house_max_length
                        else -> null
                    },
                ) { changedValue ->
                    houseText = changedValue
                }
                EditText(
                    modifier = Modifier.fillMaxWidth(),
                    textFieldValue = flatText,
                    labelStringId = R.string.hint_create_address_flat,
                    editTextType = EditTextType.TEXT,
                    errorMessageId = if (state.hasFlatError) {
                        R.string.error_create_address_flat_max_length
                    } else {
                        null
                    },
                ) { changedValue ->
                    flatText = changedValue
                }
                EditText(
                    modifier = Modifier.fillMaxWidth(),
                    textFieldValue = entranceText,
                    labelStringId = R.string.hint_create_address_entrance,
                    editTextType = EditTextType.TEXT,
                    errorMessageId = if (state.hasEntranceError) {
                        R.string.error_create_address_entrance_max_length
                    } else {
                        null
                    },
                ) { changedValue ->
                    entranceText = changedValue
                }
                EditText(
                    modifier = Modifier.fillMaxWidth(),
                    textFieldValue = floorText,
                    labelStringId = R.string.hint_create_address_floor,
                    editTextType = EditTextType.TEXT,
                    errorMessageId = if (state.hasFloorError) {
                        R.string.error_create_address_floor_max_length
                    } else {
                        null
                    },
                ) { changedValue ->
                    floorText = changedValue
                }
                EditText(
                    modifier = Modifier.fillMaxWidth(),
                    textFieldValue = commentText,
                    labelStringId = R.string.hint_create_address_comment,
                    editTextType = EditTextType.TEXT,
                    isLast = true,
                    maxLines = 5,
                    errorMessageId = if (state.hasCommentError) {
                        R.string.error_create_address_comment_max_length
                    } else {
                        null
                    },
                ) { changedValue ->
                    commentText = changedValue
                }
            }

            fun onSaveButtonClick() {
                focusManager.clearFocus()
                viewModel.onCreateAddressClicked(
                    streetName = streetText.text,
                    house = houseText.text,
                    flat = flatText.text,
                    entrance = entranceText.text,
                    floor = floorText.text,
                    comment = commentText.text,
                )
            }

            LoadingButton(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                textStringId = R.string.action_create_address_save,
                onClick = ::onSaveButtonClick,
                isLoading = state.isCreateLoading
            )
        }
    }

    private fun handleEventList(eventList: List<CreateAddressState.Event>) {
        eventList.forEach { event ->
            when (event) {
                is CreateAddressState.Event.AddressCreatedSuccess -> {
                    viewBinding.root.showSnackbar(
                        message = resources.getString(R.string.msg_create_address_created),
                        textColor = resourcesProvider.getColorByAttr(R.attr.colorOnPrimary),
                        backgroundColor = resourcesProvider.getColorByAttr(R.attr.colorPrimary),
                        isTop = false
                    )
                    findNavController().popBackStack()
                }
                is CreateAddressState.Event.AddressCreatedFailed -> {
                    viewBinding.root.showSnackbar(
                        message = resources.getString(R.string.error_create_address_fail),
                        textColor = resourcesProvider.getColorByAttr(R.attr.colorOnError),
                        backgroundColor = resourcesProvider.getColorByAttr(R.attr.colorError),
                        isTop = false
                    )
                }
            }
        }
        viewModel.consumeEventList(eventList)
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CreateAddressSuccessScreenPreview() {
        val streetItem = CreateAddressState.StreetItem(
            uuid = "",
            name = "улица Чапаева"
        )
        CreateAddressScreen(
            CreateAddressState(
                streetItemList = listOf(
                    streetItem,
                    streetItem,
                    streetItem,
                    streetItem,
                ),
                state = CreateAddressState.State.Success
            )
        )
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CreateAddressLoadingScreenPreview() {
        CreateAddressScreen(CreateAddressState())
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CreateAddressErrorScreenPreview() {
        CreateAddressScreen(
            CreateAddressState(
                state = CreateAddressState.State.Error(
                    GetStreetsUseCase.NoSelectedCityUuidThrow
                )
            )
        )
    }
}
