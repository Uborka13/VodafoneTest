package hu.ubi.soft.vodafonetest.ui.fragment.dashboard.listadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.ubi.soft.vodafonetest.R
import hu.ubi.soft.vodafonetest.databinding.LayoutHeaderBinding
import hu.ubi.soft.vodafonetest.databinding.LayoutRefillOfferBinding
import hu.ubi.soft.vodafonetest.extensions.onClick
import hu.ubi.soft.vodafonetest.ui.fragment.dashboard.RefillOffers

class RefillOfferListAdapter(diffUtil: RefillDiffUtil) :
    ListAdapter<RefillDataItem, RecyclerView.ViewHolder>(diffUtil) {

    var listener: OnItemSelectedListener? = null

    class RefillViewHolder(val binding: LayoutRefillOfferBinding) :
        RecyclerView.ViewHolder(binding.root)

    class TextViewHolder(val binding: LayoutHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.id.list_item_header_id -> {
                TextViewHolder(
                    LayoutHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            R.id.list_item_refill_offers_id -> {
                RefillViewHolder(
                    LayoutRefillOfferBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw ClassCastException("Unknown viewType $viewType")
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is RefillDataItem.Header -> {
                R.id.list_item_header_id
            }
            is RefillDataItem.RefillItem -> {
                R.id.list_item_refill_offers_id
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextViewHolder -> {
                holder.binding.tvHeader.text = (getItem(position) as RefillDataItem.Header).text
            }
            is RefillViewHolder -> {
                val item: RefillDataItem.RefillItem = getItem(position) as RefillDataItem.RefillItem
                holder.binding.tvOfferName.text = item.offer.name
                holder.binding.tvOfferPrice.text = "${item.offer.price} Ft"
                holder.binding.ivSelect.onClick {
                    listener?.onSelected(item.offer)
                }
            }
        }

    }

    companion object RefillDiffUtil : DiffUtil.ItemCallback<RefillDataItem>() {
        override fun areItemsTheSame(oldItem: RefillDataItem, newItem: RefillDataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RefillDataItem, newItem: RefillDataItem): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemSelectedListener {
        fun onSelected(item: RefillOffers)
    }


}

sealed class RefillDataItem {
    abstract val id: String

    data class RefillItem(val offer: RefillOffers) : RefillDataItem() {
        override val id: String = offer.id
    }

    data class Header(val text: String) : RefillDataItem() {
        override val id: String = text
    }
}