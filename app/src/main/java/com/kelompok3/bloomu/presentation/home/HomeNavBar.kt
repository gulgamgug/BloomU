package com.kelompok3.bloomu.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewDay
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kelompok3.bloomu.navigation.AnalyticRoute
import com.kelompok3.bloomu.navigation.CareRoute
import com.kelompok3.bloomu.navigation.HomeRoute
import com.kelompok3.bloomu.navigation.ProfileRoute
import com.kelompok3.bloomu.presentation.calendar.CalendarPage
import com.kelompok3.bloomu.presentation.profile.ProfilePage
import androidx.compose.runtime.saveable.rememberSaveable


data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: Any
)


@Composable
fun HomeNavBar( //navbar
    initialTab: Int = 0,
    onCheckInClick: () -> Unit,
    onLogOutSuccess: () -> Unit,
    onEditAccountClick: () -> Unit = {}
) {
    var selectedTab by rememberSaveable { mutableStateOf(initialTab) }

    val navItems = listOf( //isi navbar
        NavItem("Home", Icons.Default.Home, HomeRoute),
        NavItem("Kalender", Icons.Default.CalendarViewDay, AnalyticRoute),
        NavItem("Care", Icons.Default.ListAlt, CareRoute),
        NavItem("Profil", Icons.Default.Person, ProfileRoute)
    )

    Scaffold(
        bottomBar = {
            androidx.compose.material3.Surface(
                tonalElevation = 0.dp,
                color = Color(0xFF6E6299)
            ) {
                NavigationBar(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF6E6299),
                    modifier = androidx.compose.ui.Modifier
                        .height(64.dp)
                        .padding(horizontal = 30.dp)
                        .fillMaxWidth(), // Atur tinggi navbar lebih pendek
                    tonalElevation = 0.dp
                ) {
                    navItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            alwaysShowLabel = false, // Pastikan label tidak memakan space
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label,
                                    modifier = androidx.compose.ui.Modifier.size(26.dp) // Ukuran icon sedikit lebih besar
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                indicatorColor = Color(0xFF413B5E),
                                unselectedIconColor = Color(0xFFFFFFFF).copy(alpha = 0.4f)
                            )
                        )
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
                onLogOutSuccess = onLogOutSuccess
            )
            1 -> CalendarPage(modifier = contentModifier)
            2 -> PlaceholderScreen("Care", contentModifier)
            3 -> ProfilePage(
                modifier = contentModifier,
                onEditAccountClick = onEditAccountClick
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
