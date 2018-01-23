package biz.dealnote.messenger.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import biz.dealnote.messenger.R;
import biz.dealnote.messenger.adapter.base.RecyclerBindableAdapter;
import biz.dealnote.messenger.model.Poll;
import biz.dealnote.messenger.settings.CurrentTheme;

public class PollAnswersAdapter extends RecyclerBindableAdapter<Poll.Answer, PollAnswersAdapter.ViewHolder> {

    private int checked;
    private boolean checkable;
    private OnAnswerChangedCallback listener;
    private Context context;

    public PollAnswersAdapter(Context context, @NonNull List<Poll.Answer> items) {
        super(items);
        this.context = context;
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int position, int type) {
        Poll.Answer answer = getItem(position);

        holder.tvTitle.setText(answer.getText());
        holder.rbButton.setText(answer.getText());

        holder.tvCount.setText(String.valueOf(answer.getVoteCount()));
        holder.pbRate.setProgress((int) answer.getRate());
        holder.pbRate.setOnClickListener(e->{
            //TODO to show list of people, who vote for this section
            //Now after click on item(progress of people of voting) isn't showing the list of people
            Toast.makeText(context,R.string.not_yet_implemented_message,Toast.LENGTH_LONG).show();
        });
        holder.pbRate.getProgressDrawable().setColorFilter(CurrentTheme.getIconColorActive(context), PorterDuff.Mode.MULTIPLY);

        holder.rbButton.setOnCheckedChangeListener(null);
        holder.rbButton.setChecked(checked == answer.getId());
        holder.rbButton.setOnCheckedChangeListener((compoundButton, b) -> changeChecked(answer.getId()));

        holder.mVotedRoot.setVisibility(checkable ? View.GONE : View.VISIBLE);
        holder.rbButton.setVisibility(checkable ? View.VISIBLE : View.GONE);
    }

    @Override
    protected ViewHolder viewHolder(View view, int type) {
        return new ViewHolder(view);
    }

    @Override
    protected int layoutId(int type) {
        return R.layout.item_poll_answer;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCount;
        RadioButton rbButton;
        TextView tvTitle;
        ProgressBar pbRate;
        View mVotedRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            rbButton = (RadioButton) itemView.findViewById(R.id.item_poll_answer_radio);
            tvCount = (TextView) itemView.findViewById(R.id.item_poll_answer_count);
            tvTitle = (TextView) itemView.findViewById(R.id.item_poll_answer_title);
            pbRate = (ProgressBar) itemView.findViewById(R.id.item_poll_answer_progress);
            mVotedRoot = itemView.findViewById(R.id.voted_root);
        }
    }

    private void changeChecked(int id) {
        if (!checkable) {
            return;
        }

        checked = id;

        if (listener != null) {
            listener.onAnswerChanged(checked);
        }

        notifyDataSetChanged();
    }

    public void setCheckable(boolean checkable) {
        this.checkable = checkable;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public OnAnswerChangedCallback getListener() {
        return listener;
    }

    public void setListener(OnAnswerChangedCallback listener) {
        this.listener = listener;
    }

    public interface OnAnswerChangedCallback {
        void onAnswerChanged(int newid);
    }
}
