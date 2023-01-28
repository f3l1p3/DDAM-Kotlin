package cl.puc.ing.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import cl.puc.ing.proyectofinal.R
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import cl.puc.ing.proyectofinal.model.Pokemon
import cl.puc.ing.proyectofinal.model.Sprite
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var viewAdapter: PokemonViewAdapter
    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.list_view)
        val refreshButton: Button = findViewById(R.id.refresh_button)

        viewAdapter= PokemonViewAdapter(this)
        queue = Volley.newRequestQueue(this)

        listView.adapter=viewAdapter
        listView.onItemClickListener=AdapterView.OnItemClickListener { _, _, position, _ -> goToDetailActivity(viewAdapter.getItem(position)!!) }

        refreshButton.setOnClickListener { onRefresh() }
    }

    private fun goToDetailActivity(item: Pokemon) {
        val intent = Intent(this, ItemDetailActivity::class.java)

        intent.putExtra("POKEMON_DATA", item)

        startActivity(intent)
    }

    private fun onRefresh() {
        val url = "https://pokeapi.co/api/v2/pokemon"

        val listRequest = JsonObjectRequest(url, { response: JSONObject ->
            run {
                try {
                    val pokemonListJSON = response.getJSONArray("results")
                    viewAdapter.clear()

                    for (i in 0 until pokemonListJSON.length()) {
                        retrievePokemon(pokemonListJSON.getJSONObject(i))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }, { error ->
            run {
                Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
                error.printStackTrace()
            }
        })
        queue.add(listRequest)
    }

    private fun retrievePokemon(pokemonData: JSONObject?) {
        val url = pokemonData?.getString("url")
        val pokemonRequest =
            JsonObjectRequest(url, { response: JSONObject ->
                parsePokemon(response)
            }, { error ->
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

        viewAdapter.add(pokemon)
    }
}