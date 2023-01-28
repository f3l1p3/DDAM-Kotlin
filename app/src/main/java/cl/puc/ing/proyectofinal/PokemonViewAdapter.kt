package cl.puc.ing.proyectofinal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import cl.puc.ing.proyectofinal.R
import com.bumptech.glide.Glide
import cl.puc.ing.proyectofinal.model.Pokemon

class PokemonViewAdapter(context: Context) : ArrayAdapter<Pokemon>(context, 0) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view= convertView ?: LayoutInflater.from(parent.context).inflate(R.layout.listview_item,parent,false)

        val pokemonImgVW: ImageView = view.findViewById(R.id.pokemon_imgvw)
        val pokemonNameTxtVW: TextView = view.findViewById(R.id.pokemon_name_txtvw)

        val pokemonItem: Pokemon?=getItem(position)

        pokemonNameTxtVW.text=pokemonItem?.name
        Glide.with(parent.context).load(pokemonItem?.sprite?.frontDefault).into(pokemonImgVW)

        return view
    }
}