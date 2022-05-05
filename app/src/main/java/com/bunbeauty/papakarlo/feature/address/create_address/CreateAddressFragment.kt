package com.bunbeauty.papakarlo.feature.address.create_address

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.state.StateWithError
import com.bunbeauty.papakarlo.compose.element.EditText
import com.bunbeauty.papakarlo.compose.element.MainButton
import com.bunbeauty.papakarlo.compose.element.auto_complete_text_field.AutoCompleteEditText
import com.bunbeauty.papakarlo.compose.screen.ErrorScreen
import com.bunbeauty.papakarlo.compose.screen.LoadingScreen
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentCreateAddressBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.papakarlo.feature.edit_text.EditTextType
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateAddressFragment : BaseFragment(R.layout.fragment_create_address) {

    override val viewModel: CreateAddressViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentCreateAddressBinding::bind)

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getStreetList()
        viewBinding.fragmentCreateAddressCvMain.compose {
            val streetListState by viewModel.streetListState.collectAsState()
            CreateAddressScreen(streetListState)
        }
    }

    @Composable
    private fun CreateAddressScreen(streetListState: StateWithError<List<StreetItemModel>>) {
        when (streetListState) {
            is StateWithError.Success -> CreateAddressSuccessScreen(streetListState.data)
            is StateWithError.Loading -> LoadingScreen()
            is StateWithError.Error -> ErrorScreen(streetListState.message) {
                viewModel.getStreetList()
            }
            else -> Unit
        }
    }

    @Composable
    private fun CreateAddressSuccessScreen(streetList: List<StreetItemModel>) {
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
        var houseError: Int? by rememberSaveable {
            mutableStateOf(null)
        }
        var flatText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(""))
        }
        var flatError: Int? by rememberSaveable {
            mutableStateOf(null)
        }
        var entranceText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(""))
        }
        var entranceError: Int? by rememberSaveable {
            mutableStateOf(null)
        }
        var floorText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(""))
        }
        var floorError: Int? by rememberSaveable {
            mutableStateOf(null)
        }
        var commentText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(""))
        }
        var commentError: Int? by rememberSaveable {
            mutableStateOf(null)
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
                    errorMessageId = streetError,
                    list = streetList,
                ) { changedValue ->
                    streetText = changedValue
                }
                EditText(
                    modifier = Modifier.fillMaxWidth(),
                    textFieldValue = houseText,
                    labelStringId = R.string.hint_create_address_house,
                    editTextType = EditTextType.TEXT,
                    errorMessageId = houseError,
                ) { changedValue ->
                    houseText = changedValue
                }
                EditText(
                    modifier = Modifier.fillMaxWidth(),
                    textFieldValue = flatText,
                    labelStringId = R.string.hint_create_address_flat,
                    editTextType = EditTextType.TEXT,
                    errorMessageId = flatError,
                ) { changedValue ->
                    flatText = changedValue
                }
                EditText(
                    modifier = Modifier.fillMaxWidth(),
                    textFieldValue = entranceText,
                    labelStringId = R.string.hint_create_address_entrance,
                    editTextType = EditTextType.TEXT,
                    errorMessageId = entranceError,
                ) { changedValue ->
                    entranceText = changedValue
                }
                EditText(
                    modifier = Modifier.fillMaxWidth(),
                    textFieldValue = floorText,
                    labelStringId = R.string.hint_create_address_floor,
                    editTextType = EditTextType.TEXT,
                    errorMessageId = floorError,
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
                    errorMessageId = commentError,
                ) { changedValue ->
                    commentText = changedValue
                }
            }

            fun onSaveButtonClick() {
                streetError = null
                houseError = null
                flatError = null
                entranceError = null
                floorError = null
                commentError = null
                viewModel.checkStreetError(streetText.text)?.let { error ->
                    streetError = error
                    return
                }
                viewModel.checkHouseError(houseText.text)?.let { error ->
                    houseError = error
                    return
                }
                viewModel.checkHouseMaxLengthError(houseText.text)?.let { error ->
                    houseError = error
                    return
                }
                viewModel.checkFlatMaxLengthError(flatText.text)?.let { error ->
                    flatError = error
                    return
                }
                viewModel.checkEntranceMaxLengthError(entranceText.text)?.let { error ->
                    entranceError = error
                    return
                }
                viewModel.checkFloorMaxLengthError(floorText.text)?.let { error ->
                    floorError = error
                    return
                }
                viewModel.checkCommentMaxLengthError(commentText.text)?.let { error ->
                    commentError = error
                    return
                }
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
            MainButton(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                textStringId = R.string.action_create_address_save,
                onClick = ::onSaveButtonClick
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CreateAddressSuccessScreenPreview() {
        val streetItem = StreetItemModel(
            uuid = "",
            name = "улица Чапаева",
            cityUuid = "",
        )
        CreateAddressScreen(
            StateWithError.Success(
                listOf(
                    streetItem,
                    streetItem,
                    streetItem,
                    streetItem,
                )
            )
        )
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CreateAddressLoadingScreenPreview() {
        CreateAddressScreen(StateWithError.Loading())
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CreateAddressErrorScreenPreview() {
        CreateAddressScreen(StateWithError.Error("Не удалось загрузить список улиц"))
    }
}