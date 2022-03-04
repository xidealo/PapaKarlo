package com.bunbeauty.papakarlo.feature.address.create_address

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.compose.element.EditText
import com.bunbeauty.papakarlo.compose.element.MainButton
import com.bunbeauty.papakarlo.compose.element.auto_complete_text_field.AutoCompleteEditText
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentCreateAddressBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.papakarlo.feature.edit_text.EditTextType
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateAddressFragment : BaseFragment(R.layout.fragment_create_address) {

    override val viewModel: CreateAddressViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentCreateAddressBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentCreateAddressCvMain.compose {
            var streetText by remember {
                mutableStateOf(TextFieldValue(text = ""))
            }
            var houseText by remember {
                mutableStateOf(TextFieldValue(text = ""))
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(FoodDeliveryTheme.dimensions.mediumSpace)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    val streetList by viewModel.streetList.collectAsState()
                    AutoCompleteEditText(
                        modifier = Modifier.fillMaxWidth(),
                        initTextFieldValue = streetText,
                        labelStringId = R.string.hint_create_address_street,
                        editTextType = EditTextType.TEXT,
                        focus = true,
                        list = streetList,
                    ) { value ->
                        streetText = value
                    }
                    EditText(
                        modifier = Modifier.fillMaxWidth(),
                        initTextFieldValue = houseText,
                        labelStringId = R.string.hint_create_address_house,
                        editTextType = EditTextType.TEXT
                    ) { value ->
                        houseText = value
                    }
                }
                MainButton(textStringId = R.string.action_create_address_save) {
                    viewModel.onCreateAddressClicked(
                        streetName = streetText.text,
                        house = houseText.text,
                        flat = "",
                        entrance = "",
                        comment = "",
                        floor = ""
                    )
                }
            }
        }

//        viewBinding.run {
//            textInputMap[STREET_ERROR_KEY] = fragmentCreateAddressTilStreet
//            textInputMap[HOUSE_ERROR_KEY] = fragmentCreateAddressTilHouse
//            textInputMap[FLAT_ERROR_KEY] = fragmentCreateAddressTilFlat
//            textInputMap[ENTRANCE_ERROR_KEY] = fragmentCreateAddressTilEntrance
//            textInputMap[FLOOR_ERROR_KEY] = fragmentCreateAddressTilFloor
//            textInputMap[COMMENT_ERROR_KEY] = fragmentCreateAddressTilComment
//
//            viewModel.streetNameList.startedLaunch { streetNameList ->
//                val arrayAdapter = ArrayAdapter(
//                    requireContext(),
//                    R.layout.support_simple_spinner_dropdown_item,
//                    streetNameList
//                )
//                fragmentCreateAddressEtStreet.setAdapter(arrayAdapter)
//            }
//
//            fragmentCreateAddressBtnCreateAddress.setOnClickListener {
//                viewModel.onCreateAddressClicked(
//                    streetName = fragmentCreateAddressEtStreet.text.toString(),
//                    house = fragmentCreateAddressEtHouse.text.toString().trim(),
//                    flat = fragmentCreateAddressEtFlat.text.toString().trim(),
//                    entrance = fragmentCreateAddressEtEntrance.text.toString().trim(),
//                    comment = fragmentCreateAddressEtComment.text.toString().trim(),
//                    floor = fragmentCreateAddressEtFloor.text.toString().trim()
//                )
//            }
//        }
    }
}