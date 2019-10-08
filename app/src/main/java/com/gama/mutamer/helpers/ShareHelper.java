package com.gama.mutamer.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ShareHelper {

    public static Intent customChooserIntentNoFb(Intent target, String shareTitle, Context context) {
// blacklist
        String[] blacklist = new String[]{"com.facebook.katana"};
        List<Intent> targetedShareIntents = new ArrayList<Intent>();
        List<HashMap<String, String>> intentMetaInfo = new ArrayList<HashMap<String, String>>();
        Intent chooserIntent;

        Intent dummy = new Intent(target.getAction());
        dummy.setType(target.getType());
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(dummy, 0);

        if (!resInfo.isEmpty()) {
            for (ResolveInfo resolveInfo : resInfo) {
                if (resolveInfo.activityInfo == null || Arrays.asList(blacklist).contains(resolveInfo.activityInfo.packageName))
                    continue;

                HashMap<String, String> info = new HashMap<String, String>();
                info.put("packageName", resolveInfo.activityInfo.packageName);
                info.put("className", resolveInfo.activityInfo.name);
                info.put("simpleName", String.valueOf(resolveInfo.activityInfo.loadLabel(context.getPackageManager())));
                intentMetaInfo.add(info);
            }

            if (!intentMetaInfo.isEmpty()) {
                // sorting for nice readability
                Collections.sort(intentMetaInfo, new Comparator<HashMap<String, String>>() {
                    @Override
                    public int compare(HashMap<String, String> map, HashMap<String, String> map2) {
                        return map.get("simpleName").compareTo(map2.get("simpleName"));
                    }
                });

                // create the custom intent list
                for (HashMap<String, String> metaInfo : intentMetaInfo) {
                    Intent targetedShareIntent = (Intent) target.clone();
                    targetedShareIntent.setPackage(metaInfo.get("packageName"));
                    targetedShareIntent.setClassName(metaInfo.get("packageName"), metaInfo.get("className"));
                    targetedShareIntents.add(targetedShareIntent);
                }

                chooserIntent = Intent.createChooser(targetedShareIntents.remove(targetedShareIntents.size() - 1), shareTitle);
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
                return chooserIntent;
            }
        }

        return Intent.createChooser(target, shareTitle);
    }
}
