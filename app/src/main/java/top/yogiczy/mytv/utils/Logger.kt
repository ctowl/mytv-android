<<<<<<<< HEAD:app/src/main/java/top/yogiczy/mytv/utils/Logger.kt
package top.yogiczy.mytv.utils
========
package top.yogiczy.mytv.core.data.utils
>>>>>>>> ee27a07f525f5a5f2b5114240b2ba6bfabe66f88:core/data/src/main/java/top/yogiczy/mytv/core/data/utils/Logger.kt

import android.util.Log
import kotlinx.serialization.Serializable

/**
 * 日志工具类
 */
class Logger private constructor(
    private val tag: String
) {
    fun d(message: String, throwable: Throwable? = null) {
        Log.d(tag, message, throwable)
<<<<<<<< HEAD:app/src/main/java/top/yogiczy/mytv/utils/Logger.kt
        // addHistoryItem(HistoryItem(LevelType.DEBUG, tag, message, throwable?.message))
========
        addHistoryItem(HistoryItem(LevelType.DEBUG, tag, message, throwable?.message))
>>>>>>>> ee27a07f525f5a5f2b5114240b2ba6bfabe66f88:core/data/src/main/java/top/yogiczy/mytv/core/data/utils/Logger.kt
    }

    fun i(message: String, throwable: Throwable? = null) {
        Log.i(tag, message, throwable)
        addHistoryItem(HistoryItem(LevelType.INFO, tag, message, throwable?.message))
    }

    fun w(message: String, throwable: Throwable? = null) {
        Log.w(tag, message, throwable)
        addHistoryItem(HistoryItem(LevelType.WARN, tag, message, throwable?.message))
    }

    fun e(message: String, throwable: Throwable? = null) {
        Log.e(tag, message, throwable)
        addHistoryItem(HistoryItem(LevelType.ERROR, tag, message, throwable?.message))
    }

    fun wtf(message: String, throwable: Throwable? = null) {
        Log.wtf(tag, message, throwable)
        addHistoryItem(HistoryItem(LevelType.ERROR, tag, message, throwable?.message))
    }

    companion object {
        fun create(tag: String) = Logger(tag)

        private var _history = mutableListOf<HistoryItem>()
        val history: List<HistoryItem>
            get() = _history

        fun addHistoryItem(item: HistoryItem) {
            if (listOf(LevelType.INFO, LevelType.WARN, LevelType.ERROR).contains(item.level)) {
                _history.add(item)
                if (_history.size > Constants.LOG_HISTORY_MAX_SIZE) {
                    _history = _history.takeLast(Constants.LOG_HISTORY_MAX_SIZE).toMutableList()
                }
            }
        }
    }

    enum class LevelType {
        DEBUG, INFO, WARN, ERROR
    }

    @Serializable
    data class HistoryItem(
        val level: LevelType,
        val tag: String,
        val message: String,
        val cause: String? = null,
        val time: Long = System.currentTimeMillis(),
    )
}

/**
 * 注入日志
 */
abstract class Loggable(private val tag: String? = null) {
    protected val log: Logger
        get() = Logger.create(tag ?: javaClass.simpleName)
}