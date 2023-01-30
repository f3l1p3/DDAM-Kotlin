package cl.puc.ing.proyectofinal

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cl.puc.ing.proyectofinal.model.Pokemon

class RVViewHolder(itemView: View): ViewHolder(itemView) {
    val pokemonImgVW: ImageView
    val pokemonNameTxtVW: TextView

    init {
        pokemonImgVW = itemView.findViewById(R.id.pokemon_imgvw)
        pokemonNameTxtVW = itemView.findViewById(R.id.pokemon_name_txtvw)
    }

}
