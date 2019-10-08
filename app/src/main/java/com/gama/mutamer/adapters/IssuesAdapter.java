package com.gama.mutamer.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gama.mutamer.R;
import com.gama.mutamer.data.models.Issue;
import com.gama.mutamer.helpers.DateHelper;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.interfaces.IClickListener;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mustafa on 8/8/17.
 * Release the GEEK
 */

public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.IssueViewHolder> {

    /***
     * Vars
     */
    private ArrayList<Issue> mIssues;
    private IClickListener mListener;
    private Locale mLocale;
    private Activity mActivity;


    public IssuesAdapter(ArrayList<Issue> issues, Activity activity, IClickListener listener) {
        this.mIssues = issues;
        this.mListener = listener;
        this.mActivity = activity;
        mLocale = new LanguageHelper().getCurrentLocale(activity);
    }

    @NonNull
    @Override
    public IssueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IssueViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_issue, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IssueViewHolder holder, int position) {
        int[] icons = {R.drawable.ic_pending, R.drawable.ic_pending, R.drawable.ic_done};
        int[] colors = {R.color.colorPrimary, R.color.colorPrimary, R.color.orange};
        Issue issue = mIssues.get(position);
        holder.tvIssueTitle.setText(issue.getTitle());
        holder.tvIssueDate.setText(DateHelper.formatDateTime(issue.getCreatedDate(), mLocale));
        holder.tvIssueStatus.setText(mActivity.getResources().getStringArray(R.array.IssueStatus)[issue.getStatus()]);
        holder.ivAttachments.setVisibility(issue.isHasAttachments() ? View.VISIBLE : View.INVISIBLE);
        holder.tvIssueBody.setText((issue.getBody().length() >= 200) ? issue.getBody().substring(0, 200) : issue.getBody());
        holder.vStatus.setBackgroundColor(ContextCompat.getColor(mActivity, colors[issue.getStatus()]));
        holder.ivIssueStatus.setImageResource(icons[issue.getStatus()]);
    }


    @Override
    public int getItemCount() {
        return mIssues.size();
    }

    class IssueViewHolder extends RecyclerView.ViewHolder {

        /***
         * Views
         */
        @BindView(R.id.tvIssueTitle) TextView tvIssueTitle;
        @BindView(R.id.tvIssueDate) TextView tvIssueDate;
        @BindView(R.id.tvIssueBody) TextView tvIssueBody;
        @BindView(R.id.ivAttachments) ImageView ivAttachments;
        @BindView(R.id.tvIssueStatus) TextView tvIssueStatus;
        @BindView(R.id.vCont) View mRootLayout;
        @BindView(R.id.ivIssueStatus) ImageView ivIssueStatus;
        @BindView(R.id.vStatus) View vStatus;

        IssueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.vCont) void itemPressed(View v) {
            if (mListener != null) {
                mListener.ItemClicked(mIssues.get(getAdapterPosition()).getId());
            }
        }

    }
}