package com.inknotes.inknotes.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Note
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) {
        NavigationBarItem(
            selected = currentRoute == "notes",
            onClick = { onNavigate("notes") },
            icon = {
                Icon(
                    if (currentRoute == "notes") Icons.Filled.Note else Icons.Outlined.Note,
                    contentDescription = "Notes"
                )
            },
            label = { Text("Notes") },
            modifier = Modifier.weight(1f)
        )

        NavigationBarItem(
            selected = currentRoute == "folders",
            onClick = { onNavigate("folders") },
            icon = {
                Icon(
                    if (currentRoute == "folders") Icons.Filled.Folder else Icons.Outlined.Folder,
                    contentDescription = "Folders"
                )
            },
            label = { Text("Folders") },
            modifier = Modifier.weight(1f)
        )

        NavigationBarItem(
            selected = currentRoute == "settings",
            onClick = { onNavigate("settings") },
            icon = {
                Icon(
                    if (currentRoute == "settings") Icons.Filled.Settings else Icons.Outlined.Settings,
                    contentDescription = "Settings"
                )
            },
            label = { Text("Settings") },
            modifier = Modifier.weight(1f)
        )
    }
}