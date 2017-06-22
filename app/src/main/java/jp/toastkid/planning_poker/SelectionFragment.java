package jp.toastkid.planning_poker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author toastkidjp
 */

public class SelectionFragment extends Fragment {

    private enum Suite {
        ZERO("0"), HALF("1/2"), ONE("1"), TWO("2"), THREE("3"), FIVE("5"), EIGHT("8"),
        THIRTEEN("13"), TWENTY("20"), FORTY("40"), HUNDRED("100"), QUESTION("?"), INFINITE("∞");

        private String text;

        Suite(final String text) {
            this.text = text;
        }
    }

    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.fragment_main, null);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        final RecyclerView containerView
                = (RecyclerView) view.findViewById(R.id.cards_view);
        containerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        final Adapter adapter = new Adapter();
        containerView.setAdapter(adapter);
        new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP, ItemTouchHelper.UP) {
                    @Override
                    public boolean onMove(
                            final RecyclerView recyclerView,
                            final RecyclerView.ViewHolder viewHolder,
                            final RecyclerView.ViewHolder target
                    ) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        adapter.notifyItemMoved(fromPos, toPos);
                        ((CardViewHolder) viewHolder).open();
                        return true;
                    }

                    @Override
                    public void onSwiped(
                            final RecyclerView.ViewHolder viewHolder,
                            final int direction
                    ) {
                        ((CardViewHolder) viewHolder).open();
                    }
                }).attachToRecyclerView(containerView);
    }

    private class Adapter extends RecyclerView.Adapter<CardViewHolder> {

        @Override
        public CardViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            final View cardView = inflater.inflate(R.layout.card_item, null);
            return new CardViewHolder(cardView);
        }

        @Override
        public void onBindViewHolder(final CardViewHolder holder, final int position) {
            final String text = Suite.values()[position].text;
            holder.setText(text);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    holder.open();
                }
            });
        }

        @Override
        public int getItemCount() {
            return Suite.values().length;
        }
    }

    private class CardViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        CardViewHolder(final View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.card_text);
        }

        void setText(final String text) {
            textView.setText(text);
            if (text.codePointCount(0, text.length()) >= 3) {
                Log.i("code", text + " " + text.codePointCount(0, text.length()));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 120);
            }
        }

        void open() {
            startActivity(
                    CardViewActivity.makeIntent(getContext(), textView.getText().toString()));
        }
    }
}
