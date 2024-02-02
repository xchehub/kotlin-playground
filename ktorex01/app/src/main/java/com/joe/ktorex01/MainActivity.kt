package com.joe.ktorex01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.joe.ktorex01.models.ResponseModel
import com.joe.ktorex01.network.ApiService
import com.joe.ktorex01.ui.theme.Ktorex01Theme

class MainActivity : ComponentActivity() {
    private val apiService by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ktorex01Theme {
                val products = produceState(
                    initialValue = emptyList<ResponseModel>(),
                    producer = {
                        value = apiService.getProducts()
                    }
                )

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android World")
                    LazyColumn {
                        items(products.value) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        bottom = 6.dp,
                                        top = 6.dp,
                                    )
                                    .background(Color.Gray)) {

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    //set the image url
                                    val painter = rememberImagePainter(
                                        data = it.image,
                                        builder = {
                                            error(R.drawable.ic_launcher_background)
                                        }
                                    )

                                    Image(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(150.dp),
                                        contentScale = ContentScale.Crop,
                                        contentDescription = "Coil Image",
                                        painter = painter
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .height(4.dp)
                                    )
                                    Text(
                                        text = it.title,
                                        fontSize = 18.sp
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .height(4.dp)
                                    )
                                    Text (
                                        text = it.description,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
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
    Ktorex01Theme {
        Greeting("Android")
    }
}