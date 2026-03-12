package com.kelompok3.bloomu.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.navigation.AnalyticRoute
import com.kelompok3.bloomu.navigation.HomeRoute
import com.kelompok3.bloomu.navigation.MissionRoute
import com.kelompok3.bloomu.navigation.NotificationRoute
import com.kelompok3.bloomu.navigation.ProfileRoute
import com.kelompok3.bloomu.presentation.calendar.CalendarPage
import com.kelompok3.bloomu.presentation.mission.MissionPage
import com.kelompok3.bloomu.presentation.profile.ProfilePage


data class NavItem(
    val label: String,
    val activeIcon: Int,
    val inactiveIcon: Int,
    val route: Any
)


@Composable
fun HomeNavBar( //navbar
    initialTab: Int = 0,
    onCheckInClick: () -> Unit,
    onNotificationClick: () -> Unit = {},
    onLogOutSuccess: () -> Unit,
    onEditAccountClick: () -> Unit = {}
) {
    var selectedTab by rememberSaveable { mutableStateOf(initialTab) }

    val navItems = listOf( //isi navbar
        NavItem("Home", R.drawable.home_active, R.drawable.home_inactive, HomeRoute),
        NavItem("Kalender", R.drawable.calendar_active, R.drawable.calendar_inactive, AnalyticRoute),
        NavItem("Misi", R.drawable.mission_active, R.drawable.mission_inactive, MissionRoute),
        NavItem("Profil", R.drawable.profile_active, R.drawable.profile_inactive, ProfileRoute)
    )

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(67.dp)
                    .background(Color(0xFF6E6299)),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                navItems.forEachIndexed { index, item ->
                    val isSelected = selectedTab == index
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable { selectedTab = index },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        // White bar indicator at the top
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(5.dp)
                                    .background(Color.White)
                            )
                        } else {
                            // Spacer to maintain icon alignment
                            Box(modifier = Modifier.height(5.dp))
                        }

                        // Icon container - using weight to take available space
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = if (isSelected) item.activeIcon else item.inactiveIcon),
                                contentDescription = item.label,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(21.dp)
                            )
                        }

                        // Spacer at the bottom to push the icon upward
                        androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    ) { innerPadding ->
        // Konten utama berdasarkan tab yang dipilih
        // Gunakan padding bottom saja agar background bisa tembus ke status bar (seamless)
        val contentModifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())

        when (selectedTab) {
            0 -> HomeScreen(
                modifier = contentModifier,
                onCheckInClick = onCheckInClick,
                onNotificationClick = onNotificationClick,
                onLogOutSuccess = onLogOutSuccess
            )
            1 -> CalendarPage(modifier = contentModifier)
            2 -> MissionPage(modifier = contentModifier)
            3 -> ProfilePage(
                modifier = contentModifier,
                onEditAccountClick = onEditAccountClick,
                onLogOutSuccess = onLogOutSuccess
            )
        }
    }

}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeNavBarPreview() {
    com.kelompok3.bloomu.ui.theme.BloomUTheme {
        HomeNavBar(
            onCheckInClick = {},
            onLogOutSuccess = {}
        )
    }
}

@Composable
//placeholder untuk layar yang belum jadi
fun PlaceholderScreen(name: String, modifier: Modifier = Modifier) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(text = "Layar $name (Segera Datang)")
    }
}
