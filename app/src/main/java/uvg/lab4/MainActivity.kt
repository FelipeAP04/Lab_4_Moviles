package uvg.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                RecipeScreen()
        }
    }
}

@Composable
fun RecipeScreen() {
    // Lista mutable para almacenar las recetas
    val recetas = remember { mutableStateListOf<Receta>() }
    var name by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Ingrese el nombre de receta:") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Ingrese el URL de la imagen") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            if (name.isNotEmpty() && url.isNotEmpty()) {
                recetas.add(Receta(name, url))
                name = ""
                url = ""
            }
        }) {
            Text("Añadir receta")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Recetas agregadas:")

        LazyColumn {
            items(recetas) { receta ->
                RecetaItem(receta = receta, onDelete = { recetas.remove(receta) })
            }
        }
    }
}

@Composable
fun RecetaItem(receta: Receta, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = receta.urlImagen,
                contentDescription = "Imagen de la receta",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = receta.nombre,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Button(onClick = onDelete) {
                Text("Eliminar")
            }
        }
    }
}

data class Receta(val nombre: String, val urlImagen: String)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RecipeScreen()
}
