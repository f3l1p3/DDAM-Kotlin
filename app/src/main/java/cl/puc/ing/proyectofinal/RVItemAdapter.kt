package cl.puc.ing.proyectofinal

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView.Adapter
import cl.puc.ing.proyectofinal.model.Pokemon
import com.bumptech.glide.Glide

class RVItemAdapter(private val list:MutableList<Pokemon>): Adapter<RVViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.listview_item,parent,false)
        return RVViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        val pokemonItem: Pokemon=list[position]

        holder.pokemonNameTxtVW.text=pokemonItem?.name
        Glide.with(holder.itemView.context).load(pokemonItem?.sprite?.frontDefault).into(holder.pokemonImgVW)
        holder.itemView.setOnClickListener { goToDetailActivity(holder.itemView.context,pokemonItem) }
    }

    private fun goToDetailActivity(context: Context, item: Pokemon) {
        val intent = Intent(context, ItemDetailActivity::class.java)

        intent.putExtra("POKEMON_DATA", item)

        context.startActivity(intent)
    }

}