package mx.itesm.bcr.plantifybcr.viewmodels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.bcr.plantifybcr.ListenerRecycler
import mx.itesm.bcr.plantifybcr.R

class plantaGrupoEspAdaptador(): RecyclerView.Adapter<plantaGrupoEspAdaptador.ViewHolder>()
{
    //Adaptador opcional, en caso de requerir funciones específicas en el grupo de plantas, usar este.
    val titles= arrayOf("Cactus","Lavanda", "Vainilla")
    val images = intArrayOf(R.drawable.plant1, R.drawable.plant2, R.drawable.plant3)
    var listener: ListenerRecycler? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): plantaGrupoEspAdaptador.ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_plantawiki,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: plantaGrupoEspAdaptador.ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles[i]
        viewHolder.itemImage.setImageResource(images[i])
        viewHolder.itemView.setOnClickListener {
            listener?.itemClickedGrupo(i)
        }
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView

        init {
            itemImage = itemView.findViewById(R.id.ivPlantaWiki)
            itemTitle = itemView.findViewById(R.id.tvNombrePlantaWiki)
        }
    }
}