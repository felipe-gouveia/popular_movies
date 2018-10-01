package pt.felipegouveia.popularmoviesstage1.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import pt.felipegouveia.popularmoviesstage1.R;
import pt.felipegouveia.popularmoviesstage1.view.ui.MainActivity;

public class Navigator {

    // Fragments Navigation
    public static void replaceFragmentWithAnimation(int container, android.support.v4.app.Fragment fragment, String tag, FragmentActivity fragmentActivity) {
        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(container, fragment);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    // Fragments Navigation
    public static void replaceFragment(int container, android.support.v4.app.Fragment fragment, String tag, FragmentActivity fragmentActivity) {
        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        transaction.replace(container, fragment);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    /**
     * @param container the R.id of the view that will receive the fragment
     * @param fragment the fragment
     * @param tag
     * @param fragmentActivity the containing activity
     */
    public static void addFragment(int container, android.support.v4.app.Fragment fragment, String tag, FragmentActivity fragmentActivity) {
        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        transaction.add(container, fragment);
        transaction.commit();
    }

    public static void toMain(Context context){
        context.startActivity(new Intent(context, MainActivity.class));
        ((Activity) context).finish();
    }

}
