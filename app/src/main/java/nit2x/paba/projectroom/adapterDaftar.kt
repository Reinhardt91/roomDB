package nit2x.paba.projectroom

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nit2x.paba.projectroom.database.daftarBelanja

class AdapterDaftar(private val daftarBelanja: MutableList<daftarBelanja>) :


    RecyclerView.Adapter<AdapterDaftar.ListViewHolder>() {
    interface OnItemClickCallback {
        fun delData(dtBelanja: daftarBelanja)
    }
    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    fun isiData(daftar: List<daftarBelanja>) {
        daftarBelanja.clear()
        daftarBelanja.addAll(daftar)
        notifyDataSetChanged()
    }


    private lateinit var onItemClickCallback : OnItemClickCallback


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTanggal: TextView = itemView.findViewById<TextView>(R.id.tvTanggal)
        val tvItem: TextView = itemView.findViewById<TextView>(R.id.tvItem)
        val tvJumlah: TextView = itemView.findViewById<TextView>(R.id.tvJumlah)

        val _btnEdit = itemView.findViewById<ImageButton>(R.id.button_edit)
        val _btnDelete = itemView.findViewById<ImageButton>(R.id.button_delete)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_list, parent, false)
        return ListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentItem = daftarBelanja[position]


        holder.tvTanggal.text = currentItem.tanggal
        holder.tvItem.text = currentItem.item
        holder.tvJumlah.text = currentItem.jumlah.toString()

        holder._btnEdit.setOnClickListener{
            val intent = Intent(it.context, TambahDaftar::class.java)
            intent.putExtra("id", currentItem.id)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }
        holder._btnDelete.setOnClickListener{
            onItemClickCallback.delData(currentItem)
        }
    }


    override fun getItemCount(): Int {
        return daftarBelanja.size
    }

}
