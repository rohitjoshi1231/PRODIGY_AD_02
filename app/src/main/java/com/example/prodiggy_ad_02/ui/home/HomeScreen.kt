package com.example.prodiggy_ad_02.ui.home


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ButtonDefaults.elevatedShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prodiggy_ad_02.R
import com.example.prodiggy_ad_02.data.SharedUtility
import com.example.prodiggy_ad_02.data.database.TaskDatabase
import com.example.prodiggy_ad_02.data.models.Task
import com.example.prodiggy_ad_02.data.repositores.TaskRepository
import com.example.prodiggy_ad_02.data.viewmodels.TaskViewModelFactory
import com.example.prodiggy_ad_02.data.viewmodels.TaskViewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

fun userID(context: Context) {
    val existingUserId = SharedUtility.getUserId(context)
    if (existingUserId == -1) { // Assuming -1 is the default value indicating no user ID is set
        val randomUserId = Random.nextInt(1, 1001) // Generates a random integer between 1 and 1000
        SharedUtility.saveUserId(context, randomUserId)
    }
}


@Composable
fun TodoListUi(taskViewModels: TaskViewModels) {
    val tasks by taskViewModels.allTasks.collectAsState(initial = emptyList())
    val showForm = remember { mutableStateOf(false) }
    val taskToEdit = remember { mutableStateOf<Task?>(null) }
    val context = LocalContext.current

    // Generate user ID if needed
    userID(context)

    Box(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.app_bg))
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            Title()

            SharedUtility.clearUserId(context)
            if (tasks.isEmpty()) {
                AddTaskEmpty()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 100.dp)
                ) {
                    items(tasks) { task ->
                        SwipeToDismiss(item = task, onEdit = {
                            taskToEdit.value = task
                            showForm.value = true
                        }, onRemove = {
                            CoroutineScope(Dispatchers.IO).launch {
                                taskViewModels.delete(task)
                            }
                        })
                    }
                }
            }
        }

        if (showForm.value) {
            EmbeddedAndroidViewDemo(
                txt = if (taskToEdit.value != null) "Update Task" else "Add Task",
                taskViewModels = taskViewModels,
                onCancel = {
                    showForm.value = false
                    taskToEdit.value = null
                },
                task = taskToEdit.value
            )
        }

        Add {
            showForm.value = true
            taskToEdit.value = null
        }

        Bg2()
    }
}

@Composable
fun Bg2() {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.todo_bg_2),
            contentDescription = null,
            alignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Composable
fun AddTaskEmpty() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.add_task),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(128.dp),
                alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "To add task click +",
                color = Color.DarkGray,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            )
        }
    }
}


@Composable
fun Add(onClick: () -> Unit) {
    FloatingActionButton(
        elevation = FloatingActionButtonDefaults.elevation(10.dp),
        containerColor = colorResource(id = R.color.primary),
        contentColor = Color.White,
        shape = FloatingActionButtonDefaults.smallShape,
        onClick = { onClick() },
        modifier = Modifier
            .padding(30.dp, 25.dp, 30.dp, 60.dp)
            .size(40.dp)
    ) {
        Icon(Icons.Filled.Add, "Floating action button.", modifier = Modifier.size(20.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismiss(
    item: Task, onRemove: () -> Unit, modifier: Modifier = Modifier, onEdit: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(confirmValueChange = { state ->
        when (state) {
            SwipeToDismissBoxValue.StartToEnd -> {
                coroutineScope.launch {
                    delay(1.seconds)
                    onRemove()
                    Toast.makeText(context, "Item deleted successfully", Toast.LENGTH_SHORT).show()
                }
            }

            SwipeToDismissBoxValue.EndToStart -> {
                coroutineScope.launch {
                    delay(1.seconds)
                    onEdit()
                }
            }

            else -> return@rememberSwipeToDismissBoxState false
        }
        false
    }, positionalThreshold = { it * 0.25f })

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        backgroundContent = {
            DismissBackground(dismissBoxState = swipeToDismissBoxState)
        },
        content = {
            TaskItem(item, onRemove)
        },
        modifier = modifier,
    )
}


@Composable
fun TaskItem(i: Task, onRemove: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        shape = CardDefaults.outlinedShape,
        border = BorderStroke(.5.dp, Color.Black),
        modifier = Modifier
            .wrapContentSize(Alignment.CenterStart)
            .padding(15.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.secondary),
            contentColor = colorResource(id = R.color.text),
            disabledContentColor = Color.Red,
            disabledContainerColor = Color.DarkGray
        )
    ) {
        Row {
            Spacer(modifier = Modifier.width(10.dp))
            TextView(
                text = i.title.toString(),
                color = Color.Black,
                fontSize = 18,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 10.dp)
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            val context = LocalContext.current
            Icon(
                Icons.Filled.CheckCircle,
                contentDescription = "",
                tint = colorResource(id = R.color.tertiary),
                modifier = Modifier
                    .clickable(true, onClick = {
                        onRemove()
                        Toast
                            .makeText(context, "Task Completed", Toast.LENGTH_SHORT)
                            .show()
                    })
                    .padding(end = 5.dp)
                    .size(50.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissBoxState: SwipeToDismissBoxState) {
    val color = when (dismissBoxState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Color(0Xffff1744)
        SwipeToDismissBoxValue.EndToStart -> Color(0Xff1de9b6)
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentSize()
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp)
    ) {
        Icon(Icons.Outlined.Delete, contentDescription = null)
        Icon(Icons.Outlined.Create, contentDescription = null)
    }
}

@Composable
private fun Title() {
    TextView(
        text = "To-Do",
        fontSize = 30,
        textAlign = TextAlign.Center,
        Modifier
            .fillMaxWidth()
            .padding(0.dp, 30.dp, 0.dp, 60.dp)
    )

}

@Composable
private fun TextView(
    text: String,
    fontSize: Int,
    textAlign: TextAlign,
    modifier: Modifier = Modifier,
    color: Color = colorResource(id = R.color.primary)
) {
    Text(
        modifier = modifier, text = text, style = TextStyle(
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Medium,
            textAlign = textAlign,
            fontFamily = FontFamily.Serif,
            color = color
        )
    )
}

@Composable
fun TodoListApp() {
    val context = LocalContext.current
    val database = TaskDatabase.getDatabase(context)
    val repository = TaskRepository(database.taskDao())
    val taskViewModel: TaskViewModels = viewModel(factory = TaskViewModelFactory(repository))
    TodoListUi(taskViewModel)
}

@Composable
@Preview(showBackground = true)
fun PreviewApp() {
    MaterialTheme {
        TodoListApp()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmbeddedAndroidViewDemo(
    txt: String,
    taskViewModels: TaskViewModels,
    onCancel: () -> Unit,
    task: Task? = null // Default to null for new task
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val state = remember {
                mutableStateOf(
                    task?.title ?: ""
                )
            } // Initialize with task title if editing

            OutlinedTextField(value = state.value,
                onValueChange = { state.value = it },
                maxLines = 6,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.DarkGray,
                    unfocusedLabelColor = colorResource(id = R.color.text)
                ),
                label = {
                    Text(
                        text = "Enter $txt",
                        color = colorResource(id = R.color.text),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Serif
                    )
                })

            Column {
                OutlinedButton(
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            if (task != null) {
                                // Update existing task
                                val updatedTask = task.copy(title = state.value)
                                taskViewModels.update(updatedTask)
                                onCancel()
                            } else {
                                // Insert new task
                                val newTask = Task(title = state.value)
                                taskViewModels.insert(newTask)
                                onCancel()
                            }
                        }
                    },
                    Modifier
                        .fillMaxWidth()
                        .padding(40.dp, 20.dp, 40.dp, 0.dp),
                    shape = elevatedShape
                ) {
                    Text(
                        if (task != null) "Update" else "Add",
                        color = Color.DarkGray,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Serif
                    )
                }
                OutlinedButton(
                    onClick = {
                        onCancel()
                    },
                    Modifier
                        .fillMaxWidth()
                        .padding(40.dp, 20.dp, 40.dp, 0.dp),
                ) {
                    Text(
                        text = "Cancel",
                        color = Color.DarkGray,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Serif
                    )
                }


            }
        }
    }
}
