package com.projetb3.movynov.activities

import com.projetb3.movynov.dataclasses.MediaMovie
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.projetb3.movynov.R
import com.projetb3.movynov.viewmodels.MainViewModel

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val JsonTest = "[\n" +
            "        {\n" +
            "            \"adult\": false,\n" +
            "            \"backdrop_path\": \"/8ZTVqvKDQ8emSGUEMjsS4yHAwrp.jpg\",\n" +
            "            \"id\": 27205,\n" +
            "            \"title\": \"Inception\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"original_title\": \"Inception\",\n" +
            "            \"overview\": \"Dom Cobb est un voleur expérimenté, le meilleur dans l'art dangereux de l'extraction, voler les secrets les plus intimes enfouis au plus profond du subconscient durant une phase de rêve, lorsque l'esprit est le plus vulnérable. Les capacités de Cobb ont fait des envieux dans le monde tourmenté de l'espionnage industriel alors qu'il devient fugitif en perdant tout ce qu'il a un jour aimé. Une chance de se racheter lui est alors offerte. Une ultime mission grâce à laquelle il pourrait retrouver sa vie passée mais uniquement s'il parvient à accomplir l'impossible inception.\",\n" +
            "            \"poster_path\": \"/aej3LRUga5rhgkmRP6XMFw3ejbl.jpg\",\n" +
            "            \"media_type\": \"movie\",\n" +
            "            \"genre_ids\": [\n" +
            "                28,\n" +
            "                878,\n" +
            "                12\n" +
            "            ],\n" +
            "            \"popularity\": 85.643,\n" +
            "            \"release_date\": \"2010-07-15\",\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 8.363,\n" +
            "            \"vote_count\": 33720\n" +
            "        },\n" +
            "        {\n" +
            "            \"adult\": false,\n" +
            "            \"backdrop_path\": \"/3dPhs7hUnQLphDFzdkD407VZDYo.jpg\",\n" +
            "            \"id\": 286217,\n" +
            "            \"title\": \"Seul sur Mars\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"original_title\": \"The Martian\",\n" +
            "            \"overview\": \"Au cours d’une mission spatiale habitée sur Mars, et à la suite d’un violent orage, l’astronaute Mark Watney est laissé pour mort et abandonné sur place par son équipage. Mais Watney a survécu et se retrouve seul sur cette planète hostile. Avec de maigres provisions, il ne doit compter que sur son ingéniosité, son bon sens et son intelligence pour survivre et trouver un moyen d’alerter la Terre qu’il est encore vivant. À des millions de kilomètres de là, la NASA et une équipe de scientifiques internationaux travaillent sans relâche pour ramener « le Martien » sur terre, pendant que, en parallèle, ses coéquipiers tentent secrètement d’organiser une audacieuse voire impossible mission de sauvetage.\",\n" +
            "            \"poster_path\": \"/7wTv70QMeSFbt3MKyewwXzuXAEv.jpg\",\n" +
            "            \"media_type\": \"movie\",\n" +
            "            \"genre_ids\": [\n" +
            "                18,\n" +
            "                12,\n" +
            "                878\n" +
            "            ],\n" +
            "            \"popularity\": 37.546,\n" +
            "            \"release_date\": \"2015-09-30\",\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 7.673,\n" +
            "            \"vote_count\": 18140\n" +
            "        },\n" +
            "        {\n" +
            "            \"adult\": false,\n" +
            "            \"backdrop_path\": \"/hZkgoQYus5vegHoetLkCJzb17zJ.jpg\",\n" +
            "            \"id\": 550,\n" +
            "            \"title\": \"Fight Club\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"original_title\": \"Fight Club\",\n" +
            "            \"overview\": \"Le narrateur, sans identité précise, vit seul, travaille seul, dort seul, mange seul ses plateaux‐repas pour une personne comme beaucoup d’autres personnes seules qui connaissent la misère humaine, morale et sexuelle. C’est pourquoi il va devenir membre du Fight club, un lieu clandestin où il va pouvoir retrouver sa virilité, l’échange et la communication. Ce club est dirigé par Tyler Durden, une sorte d’anarchiste entre gourou et philosophe qui prêche l’amour de son prochain.\",\n" +
            "            \"poster_path\": \"/t1i10ptOivG4hV7erkX3tmKpiqm.jpg\",\n" +
            "            \"media_type\": \"movie\",\n" +
            "            \"genre_ids\": [\n" +
            "                18,\n" +
            "                53,\n" +
            "                35\n" +
            "            ],\n" +
            "            \"popularity\": 88.495,\n" +
            "            \"release_date\": \"1999-10-15\",\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 8.434,\n" +
            "            \"vote_count\": 26446\n" +
            "        },\n" +
            "        {\n" +
            "            \"adult\": false,\n" +
            "            \"backdrop_path\": \"/dqK9Hag1054tghRQSqLSfrkvQnA.jpg\",\n" +
            "            \"id\": 155,\n" +
            "            \"title\": \"The Dark Knight : Le Chevalier noir\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"original_title\": \"The Dark Knight\",\n" +
            "            \"overview\": \"La suite de Batman Begins, The Dark Knight, le réalisateur Christopher Nolan et l'acteur Christian Bale, qui endosse à nouveau le rôle de Batman/Bruce Wayne dans sa guerre permanente contre le crime. Avec l'aide du Lieutenant de Police Jim Gordon et du Procureur Harvey Dent, Batman entreprend de démanteler définitivement les organisations criminelles de Gotham. L'association s'avère efficace, mais le trio se heurte bientôt à un nouveau génie du crime, plus connu sous le nom du Joker, qui va plonger Gotham dans l'anarchie et pousser Batman à la limite entre héros et assassin. Heath Ledger interprète Le Joker : le méchant suprême et Aaron Eckhart joue le rôle de Dent. Maggie Gyllenhaal complète le casting en tant que Rachel Dawes. De retour après Batman Begins, Gary Oldman est à nouveau Gordon, Michael Caine interprète Alfred, et Morgan Freeman est Lucius Fox.\",\n" +
            "            \"poster_path\": \"/pyNXnq8QBWoK3b37RS6C3axwUOy.jpg\",\n" +
            "            \"media_type\": \"movie\",\n" +
            "            \"genre_ids\": [\n" +
            "                18,\n" +
            "                28,\n" +
            "                80,\n" +
            "                53\n" +
            "            ],\n" +
            "            \"popularity\": 76.904,\n" +
            "            \"release_date\": \"2008-07-14\",\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 8.509,\n" +
            "            \"vote_count\": 29735\n" +
            "        },\n" +
            "        {\n" +
            "            \"adult\": false,\n" +
            "            \"backdrop_path\": \"/2nqsOT2AqPkTW81bWaLRtjgjqVM.jpg\",\n" +
            "            \"id\": 11324,\n" +
            "            \"title\": \"Shutter Island\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"original_title\": \"Shutter Island\",\n" +
            "            \"overview\": \"En 1954, le marshal Teddy Daniels et son coéquipier Chuck Aule sont envoyés enquêter sur l’île de Shutter Island, dans un hôpital psychiatrique où sont internés de dangereux criminels. L’une des patientes, Rachel Solando, a inexplicablement disparu. Comment la meurtrière a‐t‐elle pu sortir d’une cellule fermée de l’extérieur ? Le seul indice retrouvé dans la pièce est une feuille de papier sur laquelle on peut lire une suite de chiffres et de lettres sans signification apparente. Œuvre cohérente d’une malade, ou cryptogramme ?\",\n" +
            "            \"poster_path\": \"/fQ0vGVTtxjCdAJnxwPZ88O3Wzrh.jpg\",\n" +
            "            \"media_type\": \"movie\",\n" +
            "            \"genre_ids\": [\n" +
            "                18,\n" +
            "                53,\n" +
            "                9648\n" +
            "            ],\n" +
            "            \"popularity\": 57.333,\n" +
            "            \"release_date\": \"2010-02-14\",\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 8.197,\n" +
            "            \"vote_count\": 21708\n" +
            "        }]"
    private lateinit var mainViewModel : MainViewModel
    private lateinit var drawerLayout : DrawerLayout
    private var movies : List<MediaMovie> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popular_layout)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setSupportActionBar(findViewById(R.id.popular_toolbar))
        supportActionBar?.title = ""

        /**
         * Navigation Drawer
         */
        drawerLayout = findViewById(R.id.popular_drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.popular_nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, findViewById(R.id.popular_toolbar), R.string.open_nav, R.string.close_nav)

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        movies = Gson().fromJson(JsonTest, Array<MediaMovie>::class.java).toList()

        val recyclerView : RecyclerView = findViewById(R.id.popular_recycler)
        with(recyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MovieAdapter(movies)
        }

        //onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true){
        //override fun handleOnBackPressed() {
        //}
        //})

    }

    public override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

    class MovieAdapter(private val movies : List<MediaMovie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>()
    {
        // create new views
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // inflates the card_view_design view
            // that is used to hold list item
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mediamovie, parent, false)

            return ViewHolder(view)
        }

        // binds the list items to a view
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val ItemsViewModel = movies[position]

            // sets the text to the textview from our itemHolder class
            holder.title.text = ItemsViewModel.title
            holder.overview.text = ItemsViewModel.overview
        }

        // return the number of the items in the list
        override fun getItemCount(): Int {
            return movies.size
        }

        class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
            val title: TextView = itemView.findViewById(R.id.popular_recycler_movietitle)
            val overview: TextView = itemView.findViewById(R.id.popular_recycler_movieoverview)
        }
    }
}