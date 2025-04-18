package com.arbuzerxxl.vibeshot.features.auth.presentation


import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arbuzerxxl.vibeshot.core.design.theme.VibeShotTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun AuthRoute(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = koinViewModel(),
    onNavigateAfterAuth: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AuthScreen(
        modifier = modifier,
        uiState = uiState,
        onSignInClick = viewModel::onSignIn,
        onSignInAsGuestClick = viewModel::onSignInAsGuest,
        onNavigateAfterAuth = onNavigateAfterAuth,
    )
}

@Composable
internal fun AuthScreen(
    uiState: AuthUiState,
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,
    onSignInAsGuestClick: () -> Unit,
    onNavigateAfterAuth: () -> Unit,
) {

    val context = LocalContext.current

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Button(onClick = onSignInClick) {
                Text(text = "Sign In")
            }
            TextButton(onClick = onSignInAsGuestClick) {
                Text(text = "Sign In As Guest")
            }
            when (uiState) {
                is AuthUiState.Loading,
                    -> {
                }

                is AuthUiState.Error,
                    -> {
                }

                is AuthUiState.UserSuccess,
                    -> onNavigateAfterAuth()

                is AuthUiState.GuestSuccess,
                    -> onNavigateAfterAuth()

                is AuthUiState.Authorize,
                     -> openTab(context = context, url = uiState.authUrl)

            }
        }
    }
}

// TODO openTab works when chrome installed only
private fun openTab(context: Context, url: String) {

    val builder = CustomTabsIntent.Builder()
    builder.setShowTitle(true)
    builder.setInstantAppsEnabled(true)

    val customBuilder = builder.build()

    customBuilder.intent.setPackage("com.android.chrome")
    customBuilder.launchUrl(context, url.toUri())

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun AuthScreenPreview() {
    VibeShotTheme {
        AuthScreen(
            uiState = AuthUiState.Loading,
            onSignInClick = {},
            onNavigateAfterAuth = {},
            onSignInAsGuestClick = {}
        )
    }
}
