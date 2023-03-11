package com.bunbeauty.papakarlo.feature.address.screen.create_address

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.ui.element.LoadingButton
import com.bunbeauty.papakarlo.common.ui.element.text_field.FoodDeliveryTextField
import com.bunbeauty.papakarlo.common.ui.element.text_field.FoodDeliveryTextFieldWithMenu
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape
import com.bunbeauty.papakarlo.common.ui.toolbar.FoodDeliveryToolbarScreen
import com.bunbeauty.papakarlo.databinding.FragmentCreateAddressBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.extensions.showSnackbar
import com.bunbeauty.shared.domain.exeptions.EmptyStreetListException
import com.bunbeauty.shared.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoStreetByNameAndCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoUserUuidException
import com.bunbeauty.shared.presentation.Suggestion
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
        viewBinding.fragmentCreateAddressCvMain.setContentWithTheme {
            val streetListState by viewModel.streetListState.collectAsStateWithLifecycle()
            CreateAddressScreen(streetListState)
            LaunchedEffect(streetListState.eventList) {
                handleEventList(streetListState.eventList)
            }
        }
    }

    @Composable
    private fun CreateAddressScreen(createAddressState: CreateAddressState) {
        FoodDeliveryToolbarScreen(
            title = stringResource(R.string.title_create_address),
            backActionClick = {
                findNavController().popBackStack()
            },
            actionButton = {
                if (createAddressState.state is CreateAddressState.State.Success) {
                    LoadingButton(
                        modifier = Modifier.padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                        textStringId = R.string.action_create_address_save,
                        isLoading = createAddressState.isCreateLoading
                    ) {
                        viewModel.onCreateAddressClicked()
                    }
                }
            }
        ) {
            when (val state = createAddressState.state) {
                is CreateAddressState.State.Success -> CreateAddressSuccessScreen(createAddressState)
                is CreateAddressState.State.Loading -> LoadingScreen()
                is CreateAddressState.State.Error -> CreateAddressErrorScreen(state.throwable)
            }
        }
    }

    @Composable
    private fun CreateAddressSuccessScreen(state: CreateAddressState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(mediumRoundedCornerShape)
                    .background(FoodDeliveryTheme.colors.surface)
                    .padding(16.dp)
            ) {
                val focusManager = LocalFocusManager.current
                var streetText by remember {
                    mutableStateOf("")
                }
                var expanded by remember(state.suggestedStreetList) {
                    mutableStateOf(state.suggestedStreetList.isNotEmpty())
                }
                FoodDeliveryTextFieldWithMenu(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            expanded =
                                focusState.isFocused && state.suggestedStreetList.isNotEmpty()
                        },
                    expanded = expanded,
                    onExpandedChange = { value ->
                        expanded = value
                    },
                    value = streetText,
                    labelStringId = R.string.hint_create_address_street,
                    onValueChange = { value ->
                        streetText = value
                        viewModel.onStreetTextChanged(value)
                    },
                    errorMessageId = if (state.hasStreetError) {
                        R.string.error_create_address_street
                    } else {
                        null
                    },
                    suggestionsList = state.suggestedStreetList,
                    onSuggestionClick = { suggestion ->
                        streetText = suggestion.value
                        focusManager.moveFocus(FocusDirection.Down)
                        viewModel.onSuggestedStreetSelected(suggestion)
                    }
                )

                var houseText by remember {
                    mutableStateOf("")
                }
                FoodDeliveryTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = houseText,
                    labelStringId = R.string.hint_create_address_house,
                    errorMessageId = if (state.hasHouseError) {
                        R.string.error_create_address_house
                    } else {
                        null
                    },
                    onValueChange = { value ->
                        houseText = value
                        viewModel.onHouseTextChanged(value)
                    }
                )

                var flatText by remember {
                    mutableStateOf("")
                }
                FoodDeliveryTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = flatText,
                    labelStringId = R.string.hint_create_address_flat,
                    onValueChange = { value ->
                        flatText = value
                        viewModel.onFlatTextChanged(value)
                    }
                )

                var entranceText by remember {
                    mutableStateOf("")
                }
                FoodDeliveryTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = entranceText,
                    labelStringId = R.string.hint_create_address_entrance,
                    onValueChange = { value ->
                        entranceText = value
                        viewModel.onEntranceTextChanged(value)
                    }
                )

                var floorText by remember {
                    mutableStateOf("")
                }
                FoodDeliveryTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = floorText,
                    labelStringId = R.string.hint_create_address_floor,
                    onValueChange = { value ->
                        floorText = value
                        viewModel.onFloorTextChanged(value)
                    }
                )

                var commentText by remember {
                    mutableStateOf("")
                }
                FoodDeliveryTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = commentText,
                    labelStringId = R.string.hint_create_address_comment,
                    imeAction = ImeAction.Done,
                    maxLines = 5,
                    onValueChange = { value ->
                        commentText = value
                        viewModel.onCommentTextChanged(value)
                    }
                )
            }
            Spacer(modifier = Modifier.height(FoodDeliveryTheme.dimensions.scrollScreenBottomSpace))
        }
    }

    @Composable
    private fun CreateAddressErrorScreen(throwable: Throwable) {
        when (throwable) {
            is NoSelectedCityUuidException -> {
                ErrorScreen(
                    mainTextId = R.string.error_create_address_no_selected_city,
                    extraTextId = R.string.error_create_address_no_selected_city_help
                ) {
                    viewModel.getStreetList()
                }
            }
            is NoUserUuidException -> {
                ErrorScreen(
                    mainTextId = R.string.error_create_address_no_selected_city,
                    extraTextId = R.string.error_create_address_no_selected_city_help
                ) {
                    viewModel.getStreetList()
                }
            }
            is NoStreetByNameAndCityUuidException -> {
                ErrorScreen(
                    mainTextId = R.string.error_create_address_no_street_by_city_uuid_and_name,
                    extraTextId = R.string.error_create_address_no_street_by_city_uuid_and_name_help
                ) {
                    viewModel.getStreetList()
                }
            }
            is EmptyStreetListException -> {
                ErrorScreen(mainTextId = R.string.error_create_address_loading) {
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
        val suggestion = Suggestion(
            id = "",
            value = "улица Чапаева"
        )
        FoodDeliveryTheme {
            CreateAddressScreen(
                CreateAddressState(
                    suggestedStreetList = listOf(
                        suggestion,
                        suggestion,
                        suggestion,
                    ),
                    state = CreateAddressState.State.Success
                )
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CreateAddressLoadingScreenPreview() {
        FoodDeliveryTheme {
            CreateAddressScreen(CreateAddressState())
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CreateAddressErrorScreenPreview() {
        FoodDeliveryTheme {
            CreateAddressScreen(
                CreateAddressState(
                    state = CreateAddressState.State.Error(NoSelectedCityUuidException)
                )
            )
        }
    }
}
