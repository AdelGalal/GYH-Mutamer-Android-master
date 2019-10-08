package com.gama.mutamer.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.gama.mutamer.activities.MainActivity;
import com.gama.mutamer.helpers.BroadcastHelper;

import static com.gama.mutamer.helpers.BroadcastHelper.BROADCAST_EXTRA_METHOD_NAME;

/***
 * Parent Fragment to Provide Common fragments operations
 */
public abstract class BaseFragment extends Fragment {

    private Receiver mReceiver;


    /***
     * Set Activity Title
     * @param title string to be set as activity title
     */
    protected void setActivityTitle(@NonNull String title) {
        if (getActivity() == null) {
            return;
        }
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setMainTitle(title);
        }
    }

    @Override public void onResume() {
        super.onResume();
        if (getParentActivity() != null) {
            mReceiver = new Receiver();
            getParentActivity()
                    .registerReceiver(mReceiver, new IntentFilter(BroadcastHelper.ACTION_NAME));
        }
    }

    @Override public void onPause() {
        super.onPause();
        if (getParentActivity() != null &&
                mReceiver != null) {
            getParentActivity().unregisterReceiver(mReceiver);
        }
    }

    protected abstract void dataChanged(String action);

    /***
     * Navigate to new fragment
     * @param id new Fragment Id
     */
    protected void navigateToFragment(int id) {
        if (getActivity() == null) {
            return;
        }
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).navigateToFragment(id);
        }
    }

    /***
     * Launch Activity without any data
     * @param type Activity Class
     */
    protected void launchActivity(Class type) {
        if (getActivity() == null) {
            return;
        }

        startActivity(new Intent(getActivity(), type));
    }


    protected void updateActivityNavigation() {
        if (getActivity() == null) return;
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).updateNavigation();
    }


    /***
     * Get fragment parent activity if any
     * @return Fragment's parent activity
     */
    protected MainActivity getParentActivity() {
        if (getActivity() == null) return null;
        if (getActivity() instanceof MainActivity)
            return (MainActivity) getActivity();
        return null;
    }


    class Receiver extends BroadcastReceiver {

        @Override public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(BroadcastHelper.ACTION_NAME) &&
                    intent.hasExtra(BROADCAST_EXTRA_METHOD_NAME)) {
                dataChanged(intent.getStringExtra(BROADCAST_EXTRA_METHOD_NAME));
            }
        }
    }
}
