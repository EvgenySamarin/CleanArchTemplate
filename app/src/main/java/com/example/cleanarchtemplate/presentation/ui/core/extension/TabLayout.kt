package com.example.cleanarchtemplate.presentation.ui.core.extension

import com.google.android.material.tabs.TabLayout

/**
 * Вызывает функцию после клика по вкладке
 *
 * @param func функция которая будет выполнена после клика по вкладке
 *
 * @author EvgenySamarin
 * @since 20191003 v1
 */
fun TabLayout.onTabSelected(func: (TabLayout.Tab) -> Unit) {
    this.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
        override fun onTabReselected(p0: TabLayout.Tab?) {}
        override fun onTabUnselected(p0: TabLayout.Tab?) {}
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let {
                func(it)
            }
        }
    })
}