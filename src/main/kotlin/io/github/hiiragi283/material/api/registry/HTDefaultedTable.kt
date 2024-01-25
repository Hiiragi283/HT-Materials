package io.github.hiiragi283.material.api.registry

import com.google.common.collect.Table
import io.github.hiiragi283.material.impl.registry.HTDefaultedTableImpl
import java.util.function.Consumer

interface HTDefaultedTable<R, C, V> {

    fun getOrCreate(rowKey: R, columnKey: C): V

    fun forEach(consumer: Consumer<Table.Cell<R, C, V>>)

    fun forEach(action: (R, C, V) -> Unit)

    companion object {
        @JvmStatic
        fun <R, C, V> create(mapping: (R, C) -> V): HTDefaultedTable<R, C, V> = HTDefaultedTableImpl(mapping)
    }

}