package com.example.todolist.ui.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.Data.RoomDB.TaskItem
import com.example.todolist.Viewmodel.TaskViewModel
import com.example.todolist.ui.theme.deepGrey
import com.example.todolist.ui.theme.grey

@Composable
fun TodoListScreen(ViewModel: TaskViewModel) {

    val tasks by ViewModel.allTasks.collectAsState()

    var taskToEdit by remember { mutableStateOf<TaskItem?>(null) }
    var showEditorDialog by remember { mutableStateOf(false) }

    Scaffold(
       floatingActionButton = {
           ExtendedFloatingActionButton(
               onClick = {
                   taskToEdit = null
                   showEditorDialog = true
               },
               shape = RoundedCornerShape(15.dp),
               containerColor = deepGrey,
               contentColor = Color.White,
               elevation = FloatingActionButtonDefaults.elevation(8.dp)
           ){

               Icon(
                   imageVector = Icons.Default.Add,
                   contentDescription = "Add"
               )

               Spacer(modifier = Modifier.width(10.dp))

               Text(
                   text = "New Task",
                   fontSize = 30.sp
               )
           }
       }
    ) { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
        ){
            Text(
                text = "My Tasks",
                modifier = Modifier.padding(bottom = 24.dp),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "${tasks.filter{!it.isDone}.size} Tasks remaining today",     // filters undone tasks from entire size of allTask list and shows their count.
                color = grey,
            )

            Spacer(modifier = Modifier.height(16.dp))

            if(tasks.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ){
                    Text(
                        text = "No Tasks to show",
                        color = grey,
                    )
                }
            }
            else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ){

                    items(
                        items = tasks,
                        key = {it.id}
                    ){ task ->

                        ToDoItem(
                            item = task,
                            onEditClick = { taskToEdit = task; showEditorDialog = true },
                            onDeleteClick = { ViewModel.deleteTask(task) },
                            onCheckChange = { checked -> ViewModel.updateTask(task.copy(isDone = checked)) }
                        )
                    }

                }
            }

        }
    }

    // calling Task Editor Dialog (Modal Bottom Sheet)
    if(showEditorDialog)
    {
        TaskEditorDialog(
            task = taskToEdit,
            onSave = { newName ->
                if(taskToEdit == null) {
                    ViewModel.addTask(TaskItem(taskName = newName, isDone = false))
                }
                else {
                    taskToEdit?.let { currentTask ->
                        ViewModel.updateTask(currentTask.copy(taskName = newName))
                    }
                }
                showEditorDialog = false
                taskToEdit = null
            },
            onCancel = {
                taskToEdit = null
                showEditorDialog = false
            }
        )
    }

}






