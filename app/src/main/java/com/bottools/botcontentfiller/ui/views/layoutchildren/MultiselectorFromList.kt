package com.bottools.botcontentfiller.ui.views.layoutchildren

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.util.AttributeSet
import android.widget.Button
import android.widget.LinearLayout
import io.realm.RealmList

abstract class MultiselectorFromList<T> : AbstractChild {

    @JvmOverloads
    constructor(possibleArray: List<T>, checkedItems: RealmList<T>, description: String, fragmentManager: FragmentManager, context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        val view = Button(context)
        view.text = description
        view.setOnClickListener {
            val dialog = DialogSelection.newInstance(createItemsMap(), createCheckedMap())
            dialog.callback = { checkedIndexes: BooleanArray ->
                checkedItems.clear()
                checkedIndexes.forEachIndexed { i, it ->
                    if (it) {
                        checkedItems.add(possibleArray[i])
                    }
                }
                changeListener?.onChange()
            }
            dialog.show(fragmentManager, " ")
        }
        addView(view, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
    }

    abstract fun createCheckedMap(): BooleanArray
    abstract fun createItemsMap(): ArrayList<String>

    class DialogSelection : DialogFragment() {
        lateinit var callback: (BooleanArray) -> Unit

        companion object {

            private const val ITEMS = "items"
            private const val CHECKED_ITEMS = "checked_items"
            fun newInstance(strArray: ArrayList<String>, checkedMap: BooleanArray): DialogSelection {
                val dialogSelection = DialogSelection()
                val bundle = Bundle()
                bundle.putStringArrayList(ITEMS, strArray)
                bundle.putBooleanArray(CHECKED_ITEMS, checkedMap)
                dialogSelection.arguments = bundle
                return dialogSelection
            }
        }

        override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
            val items = arguments?.getStringArray(ITEMS)
            var checkedItems = arguments?.getBooleanArray(CHECKED_ITEMS)
            if (checkedItems==null) {
                checkedItems = BooleanArray(items!!.size)
            }
            val builder = AlertDialog.Builder(context!!)
            builder.setMultiChoiceItems(items, checkedItems, { dialog, which, isChecked ->
                checkedItems[which] = isChecked
            })

            builder.setPositiveButton("OK", { dialog, which ->
                callback.invoke(checkedItems)
            })

            builder.setNegativeButton("Cancel", { dialog, which ->
                dismiss()
            })

            return builder.create()
        }
    }

}