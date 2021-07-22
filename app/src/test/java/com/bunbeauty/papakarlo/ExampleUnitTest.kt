package com.bunbeauty.papakarlo

import com.bunbeauty.domain.model.ui.address.CafeAddress
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun addition_isCorrectAddressWithFlat() {
        val address = CafeAddress(4, "Держиснкого", "5a", "54")
        assertEquals("Улица:Держиснкого, Дом:5a, Квартира:54", address.addressString())
    }

    @Test
    fun addition_isCorrectAddressWithEntrance() {
        val address = CafeAddress(4, "Держиснкого", "5a", "54", "4")
        assertEquals("Улица:Держиснкого, Дом:5a, Квартира:54, Подъезд:4", address.addressString())
    }

    @Test
    fun addition_isCorrectAddressWithIntercom() {
        val address = CafeAddress(4, "Держиснкого", "5a", "54", "4", "23")
        assertEquals("Улица:Держиснкого, Дом:5a, Квартира:54, Подъезд:4, Домофон:23", address.addressString())
    }

    @Test
    fun addition_isCorrectAddressWithFloor() {
        val address = CafeAddress(4, "Держиснкого", "5a", "54", "4", "23", "3")
        assertEquals("Улица:Держиснкого, Дом:5a, Квартира:54, Подъезд:4, Домофон:23, Этаж:3", address.addressString())
    }
}