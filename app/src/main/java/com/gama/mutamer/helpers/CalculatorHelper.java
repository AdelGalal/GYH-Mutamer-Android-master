package com.gama.mutamer.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mustafa on 11/14/17.
 * Release the GEEK
 */

public class CalculatorHelper {
    public boolean LaunchCalculator(Context context) {
        //Get Packages
        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();

        final PackageManager pm = context.getPackageManager();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        for (PackageInfo pi : packs) {
            if (pi.packageName.toLowerCase().contains("calcul")) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("appName", pi.applicationInfo.loadLabel(pm));
                map.put("packageName", pi.packageName);
                items.add(map);
            }
        }
        if (items.size() > 0) {
            String packageName = (String) items.get(0).get("packageName");
            Intent calculatorIntents = pm.getLaunchIntentForPackage(packageName);
            if (calculatorIntents != null) {
                context.startActivity(calculatorIntents);
                return true;
            }
        }
        return false;
    }
}
