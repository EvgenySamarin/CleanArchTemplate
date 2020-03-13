package com.example.cleanarchtemplate.presentation.ui.core

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchtemplate.R
import kotlinx.android.synthetic.main.fragment_base_list.*

/**
 * Для выделения поведения фрагментов содержащих список
 *
 * @author EvgenySamarin
 * @since 20190913 v2
 */
abstract class BaseListFragment : BaseFragment() {
    /** link to recycler of fragment */
    lateinit var recycler: RecyclerView
    private lateinit var lm: RecyclerView.LayoutManager

    /** base adapter which must be initialized in the child fragments */
    protected abstract val viewAdapter: BaseAdapter<*>

    //переопределенный id макета фрагмента. Присвоен макет содержащий список. Тип: Int.
    override val layoutId = R.layout.fragment_base_list

    /** инициализация базовых компонент */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lm = LinearLayoutManager(context)
        recycler = view.findViewById<RecyclerView>(R.id.recycler).apply {
            setHasFixedSize(true) //оптимизируем размер recycler
            layoutManager = lm
            adapter = viewAdapter
        }
    }

    /** @since 20191206 v1: Установить цвет фона компонента */
    protected fun setFragmentBackgroundColor(@ColorInt color: Int) {
        frame_base_list?.setBackgroundColor(color)
    }

    /**
     * Проваливаем функцию на уровень вверх, т.е. устанавливать слушателя нажатий будет потомок,
     * длинное нажатие при этом работать не будет
     */
    protected fun setOnItemClickListener(func: (item: Any?, view: View) -> Unit) {
        viewAdapter.setOnShortClick(func)
    }

    /** @since 20200306 v1: set custom behaviour on touch event */
    protected fun setOnItemTouchListener(touch: (view: View, event: MotionEvent) -> Boolean) {
        viewAdapter.setOnTouchListener { view, motionEvent -> touch(view, motionEvent) }
    }

    /** @since 20200306 v1: set custom behaviour on delete item event */
    protected fun setOnDeleteListener(onDelete: (item: Any?, position: Int) -> Unit) {
        viewAdapter.setOnDeleteItem { item, position -> onDelete(item, position) }
    }

    /**
     * Устанавливаем функцию длительного нажатия на элемент адаптера
     */
    protected fun setOnItemLongClickListener(func: (item: Any?, view: View) -> Unit) {
        viewAdapter.setOnLongClickListener(func)
    }
}