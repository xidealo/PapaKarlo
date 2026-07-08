package com.bunbeauty.menu.presentation

internal const val MENU_GRID_INDEX_LAST_ORDER = 2
internal const val MENU_GRID_INDEX_FAVORITES = 3

internal fun menuContentStartGridIndex(hasFavoritesSection: Boolean): Int =
    if (hasFavoritesSection) {
        MENU_GRID_INDEX_FAVORITES + 1
    } else {
        MENU_GRID_INDEX_FAVORITES
    }
