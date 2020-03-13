package com.example.cleanarchtemplate.presentation.ui.core

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchtemplate.BuildConfig

/**
 * Базовый класс, где VH - тип контента, который будет находиться в адаптере
 * Для выделения общего поведения списков используемых в приложении
 *
 * @author EvgenySamarin [github](https://github.com/EvgenySamarin)
 * @since 20190913 v3
 */
abstract class BaseAdapter<VH : BaseAdapter.BaseViewHolder> : RecyclerView.Adapter<VH>() {
    var items: ArrayList<Any> = ArrayList() //элементы списка
    var onClick: OnShortClickListener? = null //интерфейс для нажатий на элемент списка
    private var onLongClickListener: OnLongClickListener? = null
    private var onDeleteItemListener: OnDeleteItemListener? = null
    private var onTouchItemListener: OnTouchItemListener? = null
    private var mRecentlyDeletedItem: Any? = null
    private var mRecentlyDeletedItemPosition: Int? = null


    abstract val layoutRes: Int //id макета элемента списка

    /** создаёт наполнитель для списка*/
    abstract fun createHolder(view: View, viewType: Int): VH

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position)) //наполняем список контентом из элемента
        holder.onClick = onClick
        holder.onTouchItem = onTouchItemListener
        holder.onLongClick = onLongClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return createHolder(v, viewType)
    }

    /** возвращает из списка элемент */
    fun getItem(position: Int): Any = items[position]

    /** добавляет элемент в список */
    fun add(newItem: Any) {
        items.add(newItem)
    }

    /** @since 20200220 v1: add element to specific position */
    fun add(position: Int, newItem: Any) {
        items.add(position, newItem)
    }

    /** добавляет элементы в список */
    fun add(newItems: List<Any>) {
        items.addAll(newItems)
    }

    /** @since 20200120 v1: delete item and update recycler view */
    fun deleteItem(position: Int) {
        mRecentlyDeletedItem = items[position]
        mRecentlyDeletedItemPosition = position
        onDeleteItemListener?.onDeleteItem(mRecentlyDeletedItem, position)
    }

    fun deleteItem(item: Any) {
        val position = items.indexOf(item)
        items.remove(item)
        notifyItemRemoved(position)
    }

    /** @since 20200120 v1: undo recently deleted item */
    fun undoDelete() {
        mRecentlyDeletedItem?.let {
            items.add(mRecentlyDeletedItemPosition ?: 0, it)
            notifyItemInserted(mRecentlyDeletedItemPosition ?: 0)
        }
    }

    /** очищает список */
    fun clear() = items.clear()

    /** @since 20200120 v1: set delete listener callback with action as parameter */
    fun setOnDeleteItem(deleteAction: (Any?, Int) -> Unit) {
        onDeleteItemListener = object : OnDeleteItemListener {
            override fun onDeleteItem(item: Any?, position: Int) {
                deleteAction(item, position)
            }
        }
    }

    /**
     * Создает экземпляр описанного ниже интерфейса onClick и передает параметры интерфейса в
     * параметры вызываемой фунцкии
     *
     * @param click функция нажатия click: (Any?, View) -> Unit
     */
    fun setOnShortClick(click: (item: Any?, view: View) -> Unit) {
        onClick = object : OnShortClickListener {
            override fun onClick(item: Any?, view: View) {
                click(item, view)
            }
        }
    }

    fun setOnLongClickListener(click: (Any?, View) -> Unit) {
        onLongClickListener = object : OnLongClickListener {
            override fun onLongClick(item: Any?, view: View) {
                click(item, view)
            }
        }
    }

    /** @since 20200122 v1: set custom on touch listener */
    fun setOnTouchListener(touch: (View, MotionEvent) -> Boolean) {
        onTouchItemListener = object : OnTouchItemListener {
            override fun onTouchItem(view: View, motionEvent: MotionEvent) =
                touch(view, motionEvent)
        }
    }

    /** Для выделения общего поведения всех вьбхолдеров */
    abstract class BaseViewHolder(protected val view: View) : RecyclerView.ViewHolder(view) {
        var onClick: OnShortClickListener? = null //экземпляр интерфейса нажатий
        var onTouchItem: OnTouchItemListener? = null
        var onLongClick: OnLongClickListener? = null
        var item: Any? = null //элемент списка

        init {
            view.setOnClickListener {
                //если присвоен слушатель, обрабатываем его указав при этом элемент для взаимодействия
                onClick?.onClick(item, it)
            }
            view.setOnLongClickListener {
                onLongClick?.onLongClick(item, it)
                true
            }
            view.setOnTouchListener { view, motionEvent ->
                onTouchItem?.onTouchItem(view, motionEvent) ?: false
            }
        }

        /** абстрактная функция, заполняющая макет элемента списка данными */
        protected abstract fun onBind(item: Any)

        /**
         * присваивает элемент списка вьюхолдеру. Делегирует заполнение вьюхолдера функции
         * определённой в классе наследнике
         */
        fun bind(item: Any) {
            this.item = item
            onBind(item)
        }
    }

    /** слушатель нажатий на элементы списка который можно будет подсунуть для элементов списка */
    interface OnShortClickListener {
        fun onClick(item: Any?, view: View)
    }

    interface OnLongClickListener {
        fun onLongClick(item: Any?, view: View)
    }

    interface OnTouchItemListener {
        fun onTouchItem(view: View, motionEvent: MotionEvent): Boolean
    }

    interface OnDeleteItemListener {
        fun onDeleteItem(item: Any?, position: Int)
    }

    companion object {
        const val BASE_UPLOAD_URL = BuildConfig.SERVER_UPLOAD
    }
}