package com.stonic.stonic_erp_movil_fe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stonic.stonic_erp_movil_fe.model.Producto

class ProductAdapter(private val products: List<Producto>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(), Filterable {

    private var filteredProducts: List<Producto> = products

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = filteredProducts[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return filteredProducts.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase()?.trim()
                filteredProducts = if (query.isNullOrEmpty()) {
                    products
                } else {
                    products.filter {
                        it.nombre.lowercase().contains(query) ||
                                it.codigo.lowercase().contains(query)
                    }
                }
                val results = FilterResults()
                results.values = filteredProducts
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredProducts = results?.values as List<Producto>
                notifyDataSetChanged()
            }
        }
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.producto_nombre)
        private val businessTextView: TextView = itemView.findViewById(R.id.producto_codigo)
        private val typeTextView: TextView = itemView.findViewById(R.id.producto_contenido)
        private val quantityTextView: TextView = itemView.findViewById(R.id.producto_idmedida)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.producto_descripcion)

        fun bind(product: Producto) {
            nameTextView.text = product.nombre
            businessTextView.text = product.codigo
            typeTextView.text = product.contenido
            quantityTextView.text = product.idmedida.toString()
            descriptionTextView.text = product.descripcion
        }
    }
}