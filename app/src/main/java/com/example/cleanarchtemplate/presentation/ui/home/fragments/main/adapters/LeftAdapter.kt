package com.example.cleanarchtemplate.presentation.ui.home.fragments.main.adapters

import android.view.View
import com.example.cleanarchtemplate.R
import com.example.cleanarchtemplate.domain.example.entity.ExampleEntity
import com.example.cleanarchtemplate.presentation.ui.core.BaseAdapter
import kotlinx.android.synthetic.main.item_list_home_left.view.*

/**
 * Адаптер для обработки поведения элементов списка детей
 *
 * @author EvgenySamarin
 * @since 20190923 v3
 */
class LeftAdapter : BaseAdapter<LeftAdapter.ChildrenViewHolder>() {

    override val layoutRes = R.layout.item_list_home_left

    override fun createHolder(view: View, viewType: Int): ChildrenViewHolder =
        ChildrenViewHolder(view)

    class ChildrenViewHolder(view: View) : BaseViewHolder(view) {
        init {
            view.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any) {
            (item as? ExampleEntity)?.let {
                view.txt_item_home_left.text = it.payload
            }
        }
    }
}