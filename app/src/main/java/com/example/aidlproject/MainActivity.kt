package com.example.aidlproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.aidlproject.ui.theme.AIDLProjectTheme

class MainActivity : ComponentActivity() {
    lateinit var viewModel: AidlViewModel
    var getdata: String = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AidlViewModel::class.java]
//        viewModel.bindService(this)
        viewModel.sendData("Hello from client!")
        enableEdgeToEdge()
        setContent {
            AIDLProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val sharedResult by viewModel.sharedResult.collectAsState()
                    Log.d("MainActivity", "onCreate::::: $sharedResult")
                    getdata = sharedResult
                    Column(
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    ) {
                        val context = LocalContext.current
                        OnClickButton(onClick = { viewModel.bindService(context) })
                        AIDLClientResponse(name = getdata)
                    }

                }
            }
        }
    }


}


@Composable
private fun OnClickButton(onClick: () -> Unit) {
    Button(onClick = onClick , modifier = Modifier.padding(24.dp).fillMaxWidth()) {
        Text("SendAIDLData")
    }
}

@Composable
private fun AIDLClientResponse(name: String) {
    Text(
        modifier = Modifier
            .padding(24.dp),
        text = name
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AIDLProjectTheme {
        AIDLClientResponse(name = "Hello Word")
    }
}