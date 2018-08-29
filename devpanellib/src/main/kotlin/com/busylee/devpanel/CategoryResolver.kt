package com.busylee.devpanel

import com.busylee.devpanel.info.Category
import com.busylee.devpanel.info.InfoEntry
import java.util.*

/**
 * Utility internal class which helps to add new info entries.
 * Actually it is only one proper class to add new info entry
 */
class CategoryManager(
        private val devPanel: DevPanel
) {

    /**
     * Only one way to add new info properly
     */
    fun add(infoEntry: InfoEntry<*>, categoryName: String? = null) {
         category(categoryName).apply {
             entries.add(infoEntry)
         }
    }

    /**
     * Find category by name, or create new one from root category, if categoryName is specified
     * else just return the root category
     */
    private fun category(categoryName: String?): Category {
        return categoryName?.let {
            lookForCategoryByName(it) ?: Category(it).apply {
                devPanel.mRootCategory.categories.add(this)
            }
        } ?: devPanel.mRootCategory
    }

    /**
     * Look for category by name in current available variety
     */
    private fun lookForCategoryByName(
            requiredCategoryName: String,
            category: Category = devPanel.mRootCategory): Category? {
        val queue = LinkedList<Category>()
        queue.add(category)
        while (queue.isNotEmpty()) {
            val currentItem = queue.poll()
            if(currentItem.name == requiredCategoryName) {
                return currentItem
            }
            currentItem.categories.forEach {
                queue.add(it)
            }
        }
        return null
    }
}