package java.ac.uk.tees.w9547666.retailstoreapplication.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import java.ac.uk.tees.w9547666.retailstoreapplication.R;
import java.ac.uk.tees.w9547666.retailstoreapplication.util.Utils;
import java.ac.uk.tees.w9547666.retailstoreapplication.util.Utils.AnimationType;
import java.ac.uk.tees.w9547666.retailstoreapplication.view.activities.HomeActivity;

/**
 * @author w9547666
 */
/**
 * Fragment that appears in the "content_frame", shows a animal.
 */
public class SettingsFragment extends Fragment {

    private TextView submitLog;
    private Toolbar mToolbar;

    /**
     * Instantiates a new settings fragment.
     */
    public SettingsFragment() {
        // Empty constructor required for fragment subclasses
    }

    public static Fragment newInstance() {
        // TODO Auto-generated method stub
        return new SettingsFragment();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_settings, container,
                false);

        getActivity().setTitle("About App");

        mToolbar = (Toolbar) rootView.findViewById(R.id.htab_toolbar);
        if (mToolbar != null) {
            ((HomeActivity) getActivity()).setSupportActionBar(mToolbar);
        }

        if (mToolbar != null) {
            ((HomeActivity) getActivity()).getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);

            mToolbar.setNavigationIcon(R.drawable.ic_drawer);

        }

        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).getmDrawerLayout()
                        .openDrawer(GravityCompat.START);
            }
        });

        mToolbar.setTitleTextColor(Color.WHITE);

        /*submitLog = (TextView) rootView.findViewById(R.id.submit_log_txt);

        if (PreferenceHelper.getPrefernceHelperInstace().getBoolean(
                getActivity(), PreferenceHelper.SUBMIT_LOGS, true)) {

            submitLog.setText("Disable");
        } else {
            submitLog.setText("Enable");
        }

        rootView.findViewById(R.id.submit_log).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (PreferenceHelper.getPrefernceHelperInstace()
                                .getBoolean(getActivity(),
                                        PreferenceHelper.SUBMIT_LOGS, true)) {
                            PreferenceHelper
                                    .getPrefernceHelperInstace()
                                    .setBoolean(getActivity(),
                                            PreferenceHelper.SUBMIT_LOGS, false);

                            submitLog.setText("Disable");
                        } else {
                            PreferenceHelper.getPrefernceHelperInstace()
                                    .setBoolean(getActivity(),
                                            PreferenceHelper.SUBMIT_LOGS, true);
                            submitLog.setText("Enable");
                        }

                    }
                });*/

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {

                    Utils.switchContent(R.id.frag_container,
                            Utils.HOME_FRAGMENT,
                            ((HomeActivity) (getContext())),
                            AnimationType.SLIDE_UP);

                }
                return true;
            }
        });

       /* rootView.findViewById(R.id.picasso).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/square/picasso"));
                        startActivity(browserIntent);

                    }
                });

        rootView.findViewById(R.id.acra).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/ACRA/acra"));
                        startActivity(browserIntent);

                    }
                });

        rootView.findViewById(R.id.pull_zoom_view).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/Frank-Zhu/PullZoomView"));
                        startActivity(browserIntent);

                    }
                });

        rootView.findViewById(R.id.list_buddies).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/jpardogo/ListBuddies"));
                        startActivity(browserIntent);

                    }
                });

        rootView.findViewById(R.id.list_jazzy).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/twotoasters/JazzyListView"));
                        startActivity(browserIntent);

                    }
                });

        rootView.findViewById(R.id.email_dev).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        final Intent emailIntent = new Intent(
                                Intent.ACTION_SEND);
                        emailIntent.setType("text/plain");
                        emailIntent
                                .putExtra(
                                        Intent.EXTRA_EMAIL,
                                        new String[]{"serveroverloadofficial@gmail.com"});
                        emailIntent.putExtra(
                                Intent.EXTRA_SUBJECT,
                                "Hello There");
                        emailIntent.putExtra(Intent.EXTRA_TEXT,
                                "Add Message here");

                        emailIntent.setType("message/rfc822");

                        try {
                            startActivity(Intent.createChooser(emailIntent,
                                    "Send email using..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getActivity(),
                                    "No email clients installed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });*/

        return rootView;
    }
}
