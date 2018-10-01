package pt.felipegouveia.popularmoviesstage1.util;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;

public class Converter {

    public static float convertDPToPixels(int dp, FragmentActivity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        return dp * logicalDensity;
    }
}
