package cl.puc.ing.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import cl.puc.ing.proyectofinal.R
import com.bumptech.glide.Glide
import cl.puc.ing.proyectofinal.model.Pokemon

class ItemDetailActivity : AppCompatActivity() {

    private lateinit var item: Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        val pokemonSpriteImageView : ImageView = findViewById(R.id.pokemon_detail_imgvw)
        val pokemonNameTextView : TextView =  findViewById(R.id.pokemon_detail_txtvw)


        item = savedInstanceState?.getSerializable("POKEMON_DATA") as Pokemon? ?: intent.getSerializableExtra("POKEMON_DATA") as Pokemon

        pokemonNameTextView.text = item.name
        Glide.with(this).load(item.sprite.frontDefault).into(pokemonSpriteImageView)
    }
}