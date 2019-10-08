package com.gama.mutamer.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.gama.mutamer.R;
import com.gama.mutamer.data.models.general.Notification;
import com.gama.mutamer.helpers.DateHelper;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.interfaces.IClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mustafa on 6/10/16.
 * Release the GEEK
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {


    /***
     * Vars
     */
    private ArrayList<Notification> mNotifications;
    private IClickListener mListener;
    private Locale mLocale;
    private int lastPosition = -1;
    private Activity mActivity;
    private ArrayList<Integer> icons = new ArrayList<>(Arrays.asList(
            R.drawable.ic_sms,
            R.drawable.ic_language,
            R.drawable.ic_image
    ));

    public NotificationsAdapter(ArrayList<Notification> notifications, Activity activity, IClickListener listener) {

        this.mNotifications = notifications;
        if (activity == null) return;
        this.mListener = listener;
        this.mActivity = activity;
        mLocale = new LanguageHelper().getCurrentLocale(activity);
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = mNotifications.get(position);
        holder.tvNotificationTitle.setText(notification.getTitle());
        holder.ivNotificationType.setImageDrawable(ContextCompat.getDrawable(mActivity, icons.get(notification.getType())));
        if (!notification.isRead()) {
            holder.tvNotificationTitle.setTypeface(holder.tvNotificationTitle.getTypeface(), Typeface.BOLD);
        }
        holder.tvDayOfMonth.setText(DateHelper.formatDay(notification.getDate(),mLocale));
        holder.tvMonthName.setText(DateHelper.formatMonthName(notification.getDate(),mLocale));
        //holder.tvNotificationDate.setText(DateHelper.formatDateTime(notification.getDate(), mLocale));
        if (notification.getType() == 0) {
            holder.tvNotificationBody.setText((notification.getBody().length() >= 120) ? notification.getBody().substring(0, 120) : notification.getBody());
        } else {
            holder.tvNotificationBody.setText(notification.getType() == 1 ? mActivity.getString(R.string.web_site) : mActivity.getString(R.string.image));
        }
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mActivity, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull NotificationViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {


        /***
         * Views
         */
        @BindView(R.id.tvNotificationTitle) TextView tvNotificationTitle;
        @BindView(R.id.tvDayOfMonth) TextView tvDayOfMonth;
        @BindView(R.id.tvMonthName) TextView tvMonthName;
        @BindView(R.id.tvNotificationBody) TextView tvNotificationBody;
        @BindView(R.id.ivNotificationType) ImageView ivNotificationType;
        @BindView(R.id.vCont) View mRootLayout;

        NotificationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.vCont)
        void itemPressed(View v) {
            if (mListener != null) {
                mListener.ItemClicked(mNotifications.get( getAdapterPosition()).getId());
            }
        }

        void clearAnimation() {
            mRootLayout.clearAnimation();
        }

    }
}
