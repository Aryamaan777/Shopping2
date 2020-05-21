package Aryamaan.Shopping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ProductsAdapter(private val products: ArrayList<Product>): RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.product_row,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount()= products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text=products[position].storeName
        holder.current.text=products[position].current.toString()
        holder.hour1.text=products[position].customerInHour1.toString()
        holder.hour2.text=products[position].customerInHour2.toString()
        holder.hour3.text=products[position].customerInHour3.toString()

    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val title: TextView =itemView.findViewById(R.id.storename)
        val current:TextView=itemView.findViewById(R.id.currentval)
        val hour1:TextView=itemView.findViewById(R.id.hour1val)
        val hour2:TextView=itemView.findViewById(R.id.hour2val)
        val hour3:TextView=itemView.findViewById(R.id.hour3val)
    }
}