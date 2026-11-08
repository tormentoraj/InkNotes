package com.inknotes.inknotes.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Note
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun NavigationRail(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            
            NavigationRailItem(
                selected = currentRoute == "notes",
                onClick = { onNavigate("notes") },
                icon = {
                    Icon(
                        if (currentRoute == "notes") Icons.Filled.Note else Icons.Outlined.Note,
                        contentDescription = "Notes"
                    )
                },
                label = { Text("Notes") },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            NavigationRailItem(
                selected = currentRoute == "folders",
                onClick = { onNavigate("folders") },
                icon = {
                    Icon(
                        if (currentRoute == "folders") Icons.Filled.Folder else Icons.Outlined.Folder,
                        contentDescription = "Folders"
                    )
                },
                label = { Text("Folders") },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            NavigationRailItem(
                selected = currentRoute == "settings",
                onClick = { onNavigate("settings") },
                icon = {
                    Icon(
                        if (currentRoute == "settings") Icons.Filled.Settings else Icons.Outlined.Settings,
                        contentDescription = "Settings"
                    )
                },
                label = { Text("Settings") },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}