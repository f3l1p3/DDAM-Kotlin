package cl.puc.ing.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cl.puc.ing.proyectofinal.model.Pokemon
import cl.puc.ing.proyectofinal.model.Sprite
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    companion object {
        val LOG = Logger.getLogger(MainActivity::class.java.name)
    }
    private lateinit var viewAdapter: RVItemAdapter
    private lateinit var queue: RequestQueue
    private val pokemonList: MutableList<Pokemon> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topAppBar: Toolbar =findViewById(R.id.topAppBar)
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.refreshActionMenu -> {
                    onRefresh()
                }
                else -> false
            }
        }

//        LOG.info("toolbar: $toolbar")
//        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeButtonEnabled(true);

        val rvListView: RecyclerView = findViewById(R.id.list_view)
//        val refreshButton: Button = findViewById(R.id.refresh_button)


        viewAdapter= RVItemAdapter(pokemonList)
        queue = Volley.newRequestQueue(this)

        rvListView.layoutManager=GridLayoutManager(this, 2)
        rvListView.adapter=viewAdapter
        onRefresh()
//        refreshButton.setOnClickListener { onRefresh() }
    }

    private fun goToDetailActivity(item: Pokemon) {
        val intent = Intent(this, ItemDetailActivity::class.java)

        intent.putExtra("POKEMON_DATA", item)

        startActivity(intent)
    }

    private fun onRefresh(): Boolean {
        val url = "https://pokeapi.co/api/v2/pokemon"

        val listRequest = JsonObjectRequest(url, { response: JSONObject ->
                                                            run {
                                                                try {
                                                                    val pokemonListJSON = response.getJSONArray("results")
                                                                    pokemonList.clear()

                                                                    for (i in 0 until pokemonListJSON.length()) {
                                                                        retrievePokemon(pokemonListJSON.getJSONObject(i))
                                                                    }
                                                                } catch (e: JSONException) {
                                                                    e.printStackTrace()
                                                                }
//                                                                viewAdapter.notifyDataSetChanged()
                                                            }
                                                        }
                                                        , { error ->
                                                            run {
                                                                Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
                                                                error.printStackTrace()
                                                            }
                                                        })
        queue.add(listRequest)
        return true
    }

    private fun retrievePokemon(pokemonData: JSONObject?) {
        val url = pokemonData?.getString("url")
        val pokemonRequest = JsonObjectRequest(url
                                                    , { response: JSONObject -> parsePokemon(response) }
                                                    , { error ->
                                                        run {
                                                            Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show()
                                                            error.printStackTrace()
                                                        }
                                                    })

        queue.add(pokemonRequest)
    }

    private fun parsePokemon(response: JSONObject) {
        val spriteJson = response.getJSONObject("sprites")
        val sprite = Sprite(
            spriteJson.getString("front_default"),
            spriteJson.getString("front_shiny")
        )

        val pokemon = Pokemon(
            response.getInt("id"),
            response.getString("name"),
            response.getDouble("height"),
            response.getDouble("weight"),
            sprite
        )

        pokemonList.add(pokemon)
        viewAdapter.notifyDataSetChanged()
    }
}