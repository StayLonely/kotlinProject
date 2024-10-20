package com.example.kotlinproject.components

import com.example.kotlinproject.models.Manga

fun getFakeDataBase(): List<Manga> {
    val mangaList = listOf(
        Manga(
            id = 1,
            title = "Naruto",
            imageUrl = "https://m.media-amazon.com/images/I/91RpwagB7uL.jpg",
            rating = 8.5f,
            isFavorite = false,
            author = "Masashi Kishimoto",
            chapters = 700,
            description = "Naruto Uzumaki, a young ninja with dreams of becoming the strongest ninja, faces many challenges on his journey."
        ),
        Manga(
            id = 2,
            title = "One Piece",
            imageUrl = "https://static.wikia.nocookie.net/onepiece/images/0/0e/Volume_1.png",
            rating = 9.0f,
            isFavorite = true,
            author = "Eiichiro Oda",
            chapters = 1000,
            description = "Monkey D. Luffy and his pirate crew search for the ultimate treasure known as One Piece."
        ),
        Manga(
            id = 3,
            title = "Monogatari",
            imageUrl = "https://cdns-images.dzcdn.net/images/cover/1dc201816ddb71224f2de38698318488/500x500.jpg",
            rating = 9.5f,
            isFavorite = false,
            author = "Nisio Isin",
            chapters = 26,
            description = "Koyomi Araragi deals with various oddities and supernatural encounters that test his perception of reality."
        ),
        Manga(
            id = 4,
            title = "Attack on Titan",
            imageUrl = "https://m.media-amazon.com/images/I/81qPzeEO5IL._AC_UF1000,1000_QL80_.jpg",
            rating = 9.3f,
            isFavorite = true,
            author = "Hajime Isayama",
            chapters = 139,
            description = "In a world where humanity resides within enormous walled cities to protect themselves from Titans, Eren Yeager joins the fight against these monstrous beings."
        ),
        Manga(
            id = 5,
            title = "Death Note",
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/a/a9/Death_Note_Manga_Cover_Russian.jpg",
            rating = 9.0f,
            isFavorite = true,
            author = "Tsugumi Ohba",
            chapters = 12,
            description = "A high school student discovers a mysterious notebook that allows him to kill anyone just by writing their name in it, igniting a moral battle between him and a genius detective."
        ),
        Manga(
            id = 6,
            title = "My Hero Academia",
            imageUrl = "https://m.media-amazon.com/images/I/81AjnD8nvHL._AC_UF1000,1000_QL80_.jpg",
            rating = 8.8f,
            isFavorite = false,
            author = "Kohei Horikoshi",
            chapters = 335,
            description = "In a world where superpowers are the norm, a quirkless boy dreams of becoming a hero and enrolls in a prestigious academy for aspiring heroes."
        ),
        Manga(
            id = 7,
            title = "Demon Slayer: Kimetsu no Yaiba",
            imageUrl = "https://static.wikia.nocookie.net/kimetsu-no-yaiba/images/8/83/Kimetsu_no_Yaiba_V1.png/revision/latest?cb=20181206190730",
            rating = 9.2f,
            isFavorite = false,
            author = "Koyoharu Gotouge",
            chapters = 205,
            description = "Tanjiro Kamado, a kind-hearted boy, becomes a demon slayer to avenge his family and cure his sister, who was turned into a demon."
        ),
        Manga(
            id = 8,
            title = "Sword Art Online",
            imageUrl = "https://m.media-amazon.com/images/I/81mVoZWR6wL._AC_UF1000,1000_QL80_.jpg",
            rating = 7.8f,
            isFavorite = false,
            author = "Reki Kawahara",
            chapters = 24,
            description = "Players become trapped in a virtual reality MMORPG, and the only way to escape is to clear the game by defeating the final boss."
    )
    )
    return mangaList
}
