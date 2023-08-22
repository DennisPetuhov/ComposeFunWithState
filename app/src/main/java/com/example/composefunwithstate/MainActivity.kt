package com.example.composefunwithstate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.composefunwithstate.ui.theme.ComposeFunWithStateTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeFunWithStateTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            modifier = Modifier.background(color = Color.Blue),
                            title = {
                                Text(text = stringResource(id = R.string.app_name))
                            }
                        )
                    },
                ) { padding ->
                    Column {
                        LazyColumn(contentPadding = padding) {
                            itemsIndexed(viewModel.peopleState) { index, person ->
                                PersonItem(
                                    index = index,
                                    person = person,
                                    onRemove = {
                                        viewModel.removePerson(it)
                                    },
                                    onChangeCity = { cityName ->
                                        viewModel.changeCity(index, cityName)
                                    },
                                    onTagged = {
                                        viewModel.tagRow(index)
                                    }
                                )
                            }
                        }
                        Button(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterHorizontally),
                            onClick = {
                                viewModel.openAddPersonDialog = true
                            }
                        ) {
                            Text(text = "Add")
                        }
                    }
                    if (viewModel.openAddPersonDialog) {
                        NewPersonDialog(viewModel)
                    }
                }
            }
            }
        }
    }




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPersonDialog(viewModel: MainViewModel) {
    Dialog(
        onDismissRequest = {
            viewModel.openAddPersonDialog = false
        },
        properties = DialogProperties(),
        content = {
            Card(
                modifier = Modifier
                    .wrapContentSize(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val name = remember {
                        mutableStateOf("")
                    }
                    val city = remember {
                        mutableStateOf("")
                    }
                    TextField(
                        value = name.value,
                        label = {
                            Text(text = "Name")
                        },
                        onValueChange = {
                            name.value = it
                        })
                    TextField(
                        value = city.value,
                        label = {
                            Text(text = "City")
                        },
                        onValueChange = {
                            city.value = it
                        })
                    Button(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            viewModel.addPerson(name = name.value, city = city.value)
                            viewModel.openAddPersonDialog = false
                        }
                    ) {
                        Text(text = "Add")
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeCityDialog(travellingFrom: String, onChangeCity: (String) -> Unit) {
    Dialog(
        onDismissRequest = {
            // Do nothing
        },
        properties = DialogProperties(),
        content = {
            Card(
                modifier = Modifier
                    .wrapContentSize(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val cityName = remember {
                        mutableStateOf(travellingFrom)
                    }
                    TextField(
                        value = cityName.value,
                        label = {
                            Text(text = "Name")
                        },
                        onValueChange = {
                            cityName.value = it
                        })
                    Button(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            onChangeCity.invoke(cityName.value)
                        }
                    ) {
                        Text(text = "Add")
                    }
                }
            }
        }
    )
}

@Composable
fun PersonItem(
    index: Int,
    person: Person,
    onRemove: (Int) -> Unit,
    onChangeCity: (String) -> Unit,
    onTagged: () -> Unit
) {
    val showDialog = remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                color = if (person.tagged) {
                    Color.Gray
                } else {
                    Color.White
                }
            )
            .clickable {
                onTagged.invoke()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f),
            text = "${index + 1}. ${person.name}"
        )
        TextButton(
            modifier = Modifier
                .weight(1f),
            onClick = {
                showDialog.value = true
            }
        ) {
            Text(text = person.travellingFrom)
        }
        Button(
            modifier = Modifier
                .padding(3.dp)
                .weight(1f),
            onClick = {
                onRemove.invoke(index)
            }) {
            Text(text = "Remove")
        }
    }
    if (showDialog.value) {
        ChangeCityDialog(person.travellingFrom) { city ->
            onChangeCity.invoke(city)
            showDialog.value = false
        }
    }
}





@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeFunWithStateTheme {
        Greeting("Android")
    }
}