package jp.toastkid.planning_poker

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * @author toastkidjp
 */

class SelectionFragment : Fragment() {

    private enum class Suite private constructor(val text: String) {
        ZERO("0"), HALF("1/2"), ONE("1"), TWO("2"), THREE("3"), FIVE("5"), EIGHT("8"),
        THIRTEEN("13"), TWENTY("20"), FORTY("40"), HUNDRED("100"), QUESTION("?"), INFINITE("∞")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = Adapter()
        cards_view.adapter = adapter

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        cards_view.layoutManager = layoutManager

        layoutManager.scrollToPosition(adapter.medium())

        ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP, ItemTouchHelper.UP) {
                    override fun onMove(
                            recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder
                    ): Boolean {
                        val fromPos = viewHolder.adapterPosition
                        val toPos = target.adapterPosition
                        adapter.notifyItemMoved(fromPos, toPos)
                        (viewHolder as CardViewHolder).open()
                        return true
                    }

                    override fun onSwiped(
                            viewHolder: RecyclerView.ViewHolder,
                            direction: Int
                    ) {
                        (viewHolder as CardViewHolder).open()
                    }
                }).attachToRecyclerView(cards_view)
    }

    private inner class Adapter : RecyclerView.Adapter<CardViewHolder>() {

        private val maximumSize = Suite.values().size * 50

        private val medium = maximumSize / 2

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val cardView = inflater.inflate(R.layout.card_item, parent, false)
            return CardViewHolder(cardView)
        }

        override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
            val text = Suite.values()[position % Suite.values().size].text
            holder.setText(text)
            holder.itemView.setOnClickListener { holder.open() }
        }

        override fun getItemCount(): Int {
            return maximumSize
        }

        internal fun medium(): Int {
            return medium
        }
    }

    private inner class CardViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView

        init {
            textView = itemView.findViewById(R.id.card_text) as TextView
        }

        internal fun setText(text: String) {
            textView.text = text
            if (text.length >= 3) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 120f)
            }
        }

        internal fun open() {
            try {
                context?.let {
                    startActivity(
                            CardViewActivity.makeIntent(it, textView.text.toString())
                    )
                }
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}
