package com.example.myapplication.ui.screen.anime

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.config.appModules
import com.example.myapplication.shared.domain.anime.Anime
import com.example.myapplication.shared.enumerations.Gender
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewModel.anime.AnimeList
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun AnimeScreen() {
    val vm: AnimeList = koinViewModel();
    val animes = vm.animes.collectAsState();

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .safeContentPadding()
    ) {
        when {
            animes.value.isEmpty() -> {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            animes.value.isNotEmpty() -> {
                item {
                    GenreSelect(vm = vm)
                }
                items(
                    items = animes.value,
                    key = { anime -> anime.name ?: anime.hashCode() }
                ) { anime ->
                    Row {
                        AnimeCard(anime)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimeScreenPreview() {
    KoinApplication(application = { modules(appModules) }) {
        MyApplicationTheme {
            AnimeScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreSelect(
    vm: AnimeList,
) {
    var expanded by remember { mutableStateOf(false) }
    val gender by vm.gender.collectAsState();

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = gender.ifEmpty { "Selecione o genero..." },
            onValueChange = { },
            label = { Text("Gênero") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor(
                    type = ExposedDropdownMenuAnchorType.PrimaryEditable,
                    true
                )
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Gender.entries.forEach { genre ->
                DropdownMenuItem(
                    text = { Text(genre.name) },
                    onClick = {
                        vm.getAnimesByGenre(genre)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun AnimeCard(anime: Anime) {
    val unselected = R.drawable.favorite;
    val selected = R.drawable.selected;
    var isSelected = remember { false };

    Row(
        modifier = Modifier
            .padding(all = 8.dp),
        verticalAlignment = Alignment.Top,
    ) {
        ProfileImage(url = anime.img?.jpg?.imageUrl.orEmpty())

        Spacer(modifier = Modifier.width(20.dp))

        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            anime.name?.let { Text(it) }
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                anime.episodeNumber?.let { Text("Episódios: $it") }

                Spacer(modifier = Modifier.width(8.dp))

                anime.isFavorite?.let {
                    IconButton(
                        onClick = { isSelected = true },
                        modifier = Modifier
                            .size(24.dp)
                    ) {
                        Image(
                            painter = painterResource(if (isSelected) selected else unselected),
                            contentDescription = "Ícone de Favorito",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun ProfileImage(url: String) {
    AsyncImage(
        model = url,
        contentDescription = "Imagem do Anime...",
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = CircleShape
            ),
        contentScale = ContentScale.Inside
    )
}