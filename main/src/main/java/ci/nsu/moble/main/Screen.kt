package ci.nsu.moble.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ci.nsu.moble.main.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int
) {
    object Home : Screen("home", R.string.home)
    object Profile : Screen("profile", R.string.profile)
    object Settings : Screen("settings", R.string.settings)
}