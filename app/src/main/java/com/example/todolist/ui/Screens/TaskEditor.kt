package com.example.todolist.ui.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import com.example.todolist.ui.theme.deepGrey
import com.example.todolist.ui.theme.grey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditorDialog(
    task: TaskItem?,
    onSave: (String) -> Unit,
    onCancel: () -> Unit
) {

    var taskName by remember { mutableStateOf(task?.taskName ?: "") }

    ModalBottomSheet(
        onDismissRequest = onCancel,
        containerColor = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .navigationBarsPadding()
        ){
            Text(
                text = if (task == null) "Create New Task" else "Edit Task",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(21.dp))

            OutlinedTextField(
                value = taskName,
                onValueChange = { taskName = it },
                placeholder = { Text("Task Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = deepGrey,
                    unfocusedBorderColor = grey,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedPlaceholderColor = grey,
                    unfocusedPlaceholderColor = grey,
                ),
                shape = RoundedCornerShape(16.dp),
            )

            Spacer(modifier = Modifier.height(21.dp))

            Button(
                onClick = { onSave(taskName.trim()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = deepGrey),
                enabled = taskName.isNotBlank()
            ) {
                Text(
                    text = "Save Task",
                    fontSize = 17.sp,
                    color = Color.White
                )
            }
        }
    }

}