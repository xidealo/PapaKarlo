package core_common

object Constants {

    // UUID

    const val COMPANY_UUID = "7416dba5-2825-4fe3-abfb-1494a5e2bf99"

    // API PARAMETER

    const val COMPANY_UUID_PARAMETER = "companyUuid"
    const val CITY_UUID_PARAMETER = "cityUuid"
    const val UUID_PARAMETER = "uuid"

    // NAV PARAMETER

    const val ORDER_UUID = "orderUuid"

    // HEADER

    const val AUTHORIZATION_HEADER = "Authorization"
    const val BEARER = "Bearer "

    const val PRODUCT_CODE = "productCode"
    const val ORDERS = "ORDERS"
    const val L_ORDERS = "orders"
    const val USERS = "USERS"
    const val EMAIL = "email"
    const val ADDRESSES = "addresses"
    const val COMPANY = "COMPANY"
    const val CAFES = "cafes"
    const val MENU_PRODUCTS = "menu_products"
    const val DELIVERY = "delivery"

    // WORKER KEYS

    const val TOKEN_WORK_KEY = "token"
    const val SELECTED_CITY_UUID = "selected sity uuid"
    const val USER_UUID = "user uuid"
    const val USER_PHONE = "user phone"

    // LIMITS

    const val PHONE_LENGTH = 15

    // PAYMENT

    const val CARD_NUMBER_LABEL = "cart number"
    const val CARD_NUMBER = "4279 3800 2260 1191"
    const val PHONE_NUMBER_LABEL = "phone number"
    const val PHONE_NUMBER = "+7 (903) 801-55-52"


    // FRAGMENT RESULT KEY

    const val DEFERRED_TIME_REQUEST_KEY = "deferred time request key"
    const val SELECTED_DEFERRED_TIME_KEY = "selected deferred time key"

    const val COMMENT_REQUEST_KEY = "comment request key"
    const val RESULT_COMMENT_KEY = "result comment key"

    const val EMAIL_REQUEST_KEY = "email request key"
    const val RESULT_EMAIL_KEY = "result email key"

    const val USER_ADDRESS_REQUEST_KEY = "user address request key"
    const val RESULT_USER_ADDRESS_KEY = "result user address key"

    const val CAFE_ADDRESS_REQUEST_KEY = "cafe address request key"
    const val RESULT_CAFE_ADDRESS_KEY = "result cafe address key"

    // PATTERN

    const val DD_MMMM_HH_MM_PATTERN = "dd MMMM HH:mm"
    const val HH_MM_PATTERN = "HH:mm"

    // TIME

    const val MIN_DEFERRED_HOURS_ADDITION = 1
    const val MIN_DEFERRED_MINUTES_ADDITION = 0
    const val MINUTES_IN_HOUR = 60
    const val SECONDS_IN_MINUTE = 60
    const val SECONDS_IN_HOUR = 60 * 60

    // ERROR

    const val NOT_FOUND_WITH_UUID = 400
    const val SOMETHING_WENT_WRONG = "Something went wrong"
    const val WRONG_CODE = "Wrong code"
    const val TOO_MANY_REQUESTS = "Too many requests"

    // DIVIDER

    const val TIME_DIVIDER = ":"
    const val CODE_DIVIDER = "-"
    const val WORKING_HOURS_DIVIDER = " - "
    const val COORDINATES_DIVIDER = ","
    const val ADDRESS_DIVIDER = ", "

    // PHONE
    const val PHONE_CODE = "+7"

    // LINK

    const val VK_LINK = "https://vk.com/papakarlokimry"
    const val INSTAGRAM_LINK = "https://www.instagram.com/papakarlokimry"
    const val PLAY_MARKET_LINK =
        "https://play.google.com/store/apps/details?id=com.bunbeuaty.papakarlo"
    const val MAPS_LINK = "https://maps.google.com/maps?daddr="
    const val PHONE_LINK = "tel:"

    // ERROR_KEY

    const val STREET_ERROR_KEY = "street error key"
    const val HOUSE_ERROR_KEY = "house error key"
    const val FLAT_ERROR_KEY = "flat error key"
    const val ENTRANCE_ERROR_KEY = "entrance error key"
    const val FLOOR_ERROR_KEY = "floor error key"
    const val COMMENT_ERROR_KEY = "comment error key"

    // REMOTE CONFIG KEY

    const val FORCE_UPDATE_VERSION = "forceUpdateVersion"

}
