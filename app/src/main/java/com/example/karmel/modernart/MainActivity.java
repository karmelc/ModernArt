package com.example.karmel.modernart;
//sdfffffdddddddd
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    ArrayList<LinearLayout> listOfLL;
    ArrayList<Integer> listOfColors;
    private DialogFragment mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(10, 10, 10, 10);

        listOfLL = new ArrayList<LinearLayout>();
        listOfColors = new ArrayList<Integer>();

        LinearLayout ll1 = (LinearLayout) findViewById(R.id.Tile1);
        LinearLayout ll2 = (LinearLayout) findViewById(R.id.Tile2);
        LinearLayout ll3 = (LinearLayout) findViewById(R.id.Tile3);
        LinearLayout ll4 = (LinearLayout) findViewById(R.id.Tile4);
        LinearLayout ll5 = (LinearLayout) findViewById(R.id.Tile5);

        ll1.setBackgroundColor(Color.parseColor("#FF620197"));
        ll2.setBackgroundColor(Color.parseColor("#FFF4008A"));
        ll3.setBackgroundColor(Color.parseColor("#FFFF0000"));
        ll4.setBackgroundColor(Color.parseColor("#FFCECECE"));
        ll5.setBackgroundColor(Color.parseColor("#FF0000FF"));

        lp = (LinearLayout.LayoutParams) ll1.getLayoutParams();
        lp.setMargins(10, 10, 10, 10);
        ll1.setLayoutParams(lp);
        lp = (LinearLayout.LayoutParams) ll2.getLayoutParams();
        lp.setMargins(10, 10, 10, 10);
        ll2.setLayoutParams(lp);
        lp = (LinearLayout.LayoutParams) ll3.getLayoutParams();
        lp.setMargins(10, 10, 10, 10);
        ll3.setLayoutParams(lp);
        lp = (LinearLayout.LayoutParams) ll4.getLayoutParams();
        lp.setMargins(10, 10, 10, 10);
        ll4.setLayoutParams(lp);
        lp = (LinearLayout.LayoutParams) ll5.getLayoutParams();
        lp.setMargins(10, 10, 10, 10);
        ll5.setLayoutParams(lp);

        listOfColors.add(((ColorDrawable) ll1.getBackground()).getColor());
        listOfColors.add(((ColorDrawable) ll2.getBackground()).getColor());
        listOfColors.add(((ColorDrawable) ll3.getBackground()).getColor());
        listOfColors.add(((ColorDrawable) ll4.getBackground()).getColor());
        listOfColors.add(((ColorDrawable) ll5.getBackground()).getColor());


        listOfLL.add(ll1);
        listOfLL.add(ll2);
        listOfLL.add(ll3);
        listOfLL.add(ll4);
        listOfLL.add(ll5);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(205);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    for (int i = 0; i < listOfLL.size(); i++) {
                        if (!isGray(listOfColors.get(i))) {
                            int color = listOfColors.get(i);
                            int a = Color.alpha(color);
                            int r = Math.max(0, Color.red(color) - progress);
                            int g = Math.min(255, Color.green(color) + progress);
                            int b = Math.max(0, Color.blue(color) - progress);

                            color = Color.argb(a, r, g, b);
                            listOfLL.get(i).setBackgroundColor(color);
                        }
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private boolean isGray(int color) {
        return (Color.blue(color) == Color.red(color) && Color.blue(color) == Color.green(color));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            mDialog = AlertDialogFragment.newInstance();
            mDialog.show(getFragmentManager(), "dialog");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Class that creates the AlertDialog
    public static class AlertDialogFragment extends DialogFragment {

        public static AlertDialogFragment newInstance() {
            return new AlertDialogFragment();
        }

        // Build AlertDialog using AlertDialog.Builder
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setTitle("inspired by the works of Modern Art masters such as Piet Mondrian and Ben Nicholson")
                    .setMessage("Click below to learn more!")

                            // User cannot dismiss dialog by hitting back button
                    .setCancelable(false)

                            // Set up No Button
                    .setNegativeButton("Not Now",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    ((MainActivity) getActivity())
                                            .continueWithAns(false);
                                }
                            })

                            // Set up Yes Button
                    .setPositiveButton("Visit MOMA",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        final DialogInterface dialog, int id) {
                                    ((MainActivity) getActivity())
                                            .continueWithAns(true);
                                }
                            }).create();
        }
    }

    private void continueWithAns(boolean shouldContinue) {

        if (mDialog != null)
            mDialog.dismiss();

        if (shouldContinue) {
            String url = "http://www.MoMA.org";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    }
}
