package com.example.android_ex2_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TodoScreen() {
    val state = remember { TodoListState() }
    val red = Color(0xFFD32F2F)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F9FC))
            .safeDrawingPadding()
            .padding(16.dp)
    ) {
        Text(
            text = "课程学习任务",
            color = red,
            fontSize = 26.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = state.inputText,
                onValueChange = { state.updateInput(it) },
                modifier = Modifier.weight(1f),
                placeholder = { Text("请输入学习任务") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { state.addTask() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = red
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("添加")
            }
        }

        Text(
            text = "已完成: ${state.completedCount} / ${state.tasks.size}",
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            color = Color(0xFF777777),
            fontSize = 14.sp
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = state.tasks,
                key = { task -> task.id }
            ) { task ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 64.dp)
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = task.isCompleted,
                            onCheckedChange = { checked ->
                                state.setCompleted(task.id, checked)
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = red
                            )
                        )

                        Text(
                            text = task.title,
                            modifier = Modifier.weight(1f),
                            color = if (task.isCompleted) {
                                Color(0xFF888888)
                            } else {
                                Color(0xFF333333)
                            },
                            textDecoration = if (task.isCompleted) {
                                TextDecoration.LineThrough
                            } else {
                                TextDecoration.None
                            },
                            fontSize = 14.sp
                        )

                        TextButton(
                            onClick = { state.deleteTask(task.id) }
                        ) {
                            Text(
                                text = "删除",
                                color = red
                            )
                        }
                    }
                }
            }
        }
    }
}