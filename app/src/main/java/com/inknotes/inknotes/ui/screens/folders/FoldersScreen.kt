package com.inknotes.inknotes.ui.screens.folders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.inknotes.inknotes.data.model.Folder
import com.inknotes.inknotes.ui.components.EmptyState
import com.inknotes.inknotes.viewmodel.FolderViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FoldersScreen(
    navController: NavController,
    isTwoPane: Boolean = false
) {
    val folderViewModel: FolderViewModel = hiltViewModel()
    val folders by folderViewModel.folders.collectAsState()
    
    var showCreateDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedFolder by remember { mutableStateOf<Folder?>(null) }
    var newFolderName by remember { mutableStateOf("") }
    var newFolderColor by remember { mutableStateOf(Color(0xFFB388FF)) }
    
    val snackbarHostState = remember { SnackbarHostState() }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Folders") },
                actions = {
                    IconButton(onClick = { showCreateDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "New Folder")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "New Folder")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            
            if (folders.isEmpty()) {
                EmptyState(
                    title = "No folders yet",
                    subtitle = "Create folders to organize your notes",
                    icon = Icons.Default.Folder
                )
            } else {
                FoldersGrid(
                    folders = folders,
                    onFolderClick = { folder ->
                        navController.navigate("notes")
                    },
                    onFolderEdit = { folder ->
                        selectedFolder = folder
                        newFolderName = folder.name
                        newFolderColor = Color(folder.color)
                        showCreateDialog = true
                    },
                    onFolderDelete = { folder ->
                        selectedFolder = folder
                        showDeleteDialog = true
                    }
                )
            }
        }
        
        if (showCreateDialog) {
            CreateFolderDialog(
                name = newFolderName,
                color = newFolderColor,
                onNameChange = { newFolderName = it },
                onColorChange = { newFolderColor = it },
                onDismiss = { showCreateDialog = false },
                onConfirm = {
                    if (selectedFolder == null) {
                        folderViewModel.createFolder(newFolderName, newFolderColor.value.toInt())
                    } else {
                        folderViewModel.updateFolder(
                            selectedFolder!!.copy(
                                name = newFolderName,
                                color = newFolderColor.value.toInt()
                            )
                        )
                    }
                    showCreateDialog = false
                    newFolderName = ""
                    selectedFolder = null
                }
            )
        }
        
        if (showDeleteDialog && selectedFolder != null) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Delete Folder") },
                text = { Text("Are you sure you want to delete this folder? Notes will be moved to Unfiled.") },
                confirmButton = {
                    TextButton(onClick = {
                        folderViewModel.deleteFolder(selectedFolder!!.id)
                        showDeleteDialog = false
                        selectedFolder = null
                    }) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun FoldersGrid(
    folders: List<Folder>,
    onFolderClick: (Folder) -> Unit,
    onFolderEdit: (Folder) -> Unit,
    onFolderDelete: (Folder) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(folders.size) { index ->
            val folder = folders[index]
            FolderCard(
                folder = folder,
                onClick = { onFolderClick(folder) },
                onEdit = { onFolderEdit(folder) },
                onDelete = { onFolderDelete(folder) }
            )
        }
    }
}

@Composable
fun FolderCard(
    folder: Folder,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(folder.color)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Folder,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            
            Text(
                text = folder.name,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { onEdit() },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier.size(16.dp)
                    )
                }
                IconButton(
                    onClick = { onDelete() },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun CreateFolderDialog(
    name: String,
    color: Color,
    onNameChange: (String) -> Unit,
    onColorChange: (Color) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create Folder") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    label = { Text("Folder Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Text("Folder Color")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val colors = listOf(
                        Color(0xFFB388FF),
                        Color(0xFF8AB4F8),
                        Color(0xFF81C9FA),
                        Color(0xFF8CD583),
                        Color(0xFFFFB470),
                        Color(0xFFFF9899),
                    )
                    colors.forEach { folderColor ->
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(folderColor)
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (color == folderColor) {
                                Icon(
                                    Icons.Default.Folder,
                                    contentDescription = "Selected",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                enabled = name.isNotBlank()
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}