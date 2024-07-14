package com.bunbeauty.papakarlo.feature.address.screen.createaddress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseComposeFragment
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.LoadingButton
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.element.textfield.FoodDeliveryTextField
import com.bunbeauty.papakarlo.common.ui.element.textfield.FoodDeliveryTextFieldDefaults
import com.bunbeauty.papakarlo.common.ui.element.textfield.FoodDeliveryTextFieldWithMenu
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.main.IMessageHost
import com.bunbeauty.shared.presentation.create_address.CreateAddress
import com.bunbeauty.shared.presentation.create_address.CreateAddressViewModel
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateAddressFragment :
    BaseComposeFragment<CreateAddress.DataState, CreateAddressViewState, CreateAddress.Action, CreateAddress.Event>() {

    override val viewModel: CreateAddressViewModel by viewModel()

    @Composable
    override fun CreateAddress.DataState.mapState(): CreateAddressViewState {
        return mapCreateAddressState()
    }

    @Composable
    override fun Screen(viewState: CreateAddressViewState, onAction: (CreateAddress.Action) -> Unit) {
        FoodDeliveryScaffold(
            title = stringResource(R.string.title_create_address),
            backActionClick = {
                onAction(CreateAddress.Action.BackClick)
            },
            actionButton = {
                LoadingButton(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textStringId = R.string.action_create_address_save,
                    isLoading = viewState.isCreateLoading,
                    onClick = {
                        onAction(CreateAddress.Action.SaveClick)
                    }
                )
            }
        ) {
            CreateAddressSuccessScreen(
                viewState = viewState,
                onAction = onAction
            )
        }
    }

    override fun handleEvent(event: CreateAddress.Event) {
        when (event) {
            CreateAddress.Event.SuggestionLoadingFailed -> {
                (activity as? IMessageHost)?.showErrorMessage(
                    resources.getString(R.string.error_create_address_loading)
                )
            }

            CreateAddress.Event.AddressCreatedSuccess -> {
                (activity as? IMessageHost)?.showInfoMessage(
                    resources.getString(R.string.msg_create_address_created)
                )
                findNavController().popBackStack()
            }

            CreateAddress.Event.AddressCreatedFailed -> {
                (activity as? IMessageHost)?.showErrorMessage(
                    resources.getString(R.string.error_create_address_fail)
                )
            }

            CreateAddress.Event.Back -> {
                findNavController().popBackStack()
            }
        }
    }

    @Composable
    private fun CreateAddressSuccessScreen(
        viewState: CreateAddressViewState,
        onAction: (CreateAddress.Action) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            FoodDeliveryCard(
                modifier = Modifier.padding(top = 16.dp),
                clickable = false
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    val focusManager = LocalFocusManager.current
                    var expanded by remember(viewState.suggestionListNotEmpty) {
                        mutableStateOf(viewState.suggestionListNotEmpty)
                    }
                    FoodDeliveryTextFieldWithMenu(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState ->
                                onAction(CreateAddress.Action.StreetFocusChange(isFocused = focusState.isFocused))
                                expanded = focusState.isFocused && viewState.suggestionListNotEmpty
                            },
                        expanded = expanded,
                        onExpandedChange = { value ->
                            expanded = value
                        },
                        onSuggestionClick = { suggestion ->
                            focusManager.moveFocus(FocusDirection.Down)
                            onAction(CreateAddress.Action.SuggestionSelect(suggestion = suggestion))
                        },
                        value = viewState.street,
                        labelStringId = R.string.hint_create_address_street,
                        onValueChange = { street ->
                            onAction(CreateAddress.Action.StreetTextChange(street = street))
                        },
                        errorMessageStringId = viewState.streetErrorStringId,
                        suggestionsList = viewState.streetSuggestionList,
                        isLoading = viewState.isSuggestionLoading
                    )

                    FoodDeliveryTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = viewState.house,
                        labelStringId = R.string.hint_create_address_house,
                        onValueChange = { value ->
                            onAction(CreateAddress.Action.HouseTextChange(house = value))
                        },
                        maxSymbols = 5,
                        errorMessageStringId = viewState.houseErrorStringId
                    )

                    FoodDeliveryTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = viewState.flat,
                        labelStringId = R.string.hint_create_address_flat,
                        onValueChange = { value ->
                            onAction(CreateAddress.Action.FlatTextChange(flat = value))
                        },
                        maxSymbols = 5
                    )

                    FoodDeliveryTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = viewState.entrance,
                        labelStringId = R.string.hint_create_address_entrance,
                        onValueChange = { value ->
                            onAction(CreateAddress.Action.EntranceTextChange(entrance = value))
                        },
                        maxSymbols = 5
                    )

                    FoodDeliveryTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = viewState.floor,
                        labelStringId = R.string.hint_create_address_floor,
                        onValueChange = { value ->
                            onAction(CreateAddress.Action.FloorTextChange(floor = value))
                        },
                        maxSymbols = 5
                    )

                    FoodDeliveryTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = viewState.comment,
                        labelStringId = R.string.hint_create_address_comment,
                        keyboardOptions = FoodDeliveryTextFieldDefaults.keyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        maxLines = 5,
                        onValueChange = { value ->
                            onAction(CreateAddress.Action.CommentTextChange(comment = value))
                        },
                        maxSymbols = 100
                    )
                }
            }

            Spacer(modifier = Modifier.height(FoodDeliveryTheme.dimensions.scrollScreenBottomSpace))
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CreateAddressScreenPreview() {
        FoodDeliveryTheme {
            Screen(
                viewState = CreateAddressViewState(
                    street = "Street",
                    streetErrorStringId = null,
                    streetSuggestionList = persistentListOf(),
                    isSuggestionLoading = true,
                    house = "1",
                    houseErrorStringId = null,
                    flat = "",
                    entrance = "",
                    floor = "",
                    comment = "",
                    isCreateLoading = false
                ),
                onAction = {}
            )
        }
    }
}
