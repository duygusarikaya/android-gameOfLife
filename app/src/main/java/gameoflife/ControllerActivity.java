package gameoflife;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

import gameoflife.ui.AboutActivity;
import gameoflife.ui.CellRect;
import gameoflife.ui.GridView;
import gameoflife.util.ScheduledTaskManager;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

public class ControllerActivity extends AppCompatActivity {

    public static int TOP;

    private static GridView gridView;

    public static GridView getGrid() {
        return gridView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinearLayout main = (LinearLayout) findViewById(R.id.main_layout);

        int actionbarheight = getSupportActionBar().getHeight();
        int statusbarheight = getStatusBarHeight();
//
        TOP = actionbarheight + statusbarheight;

        gridView = new GridView(this);
        gridView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        main.addView(gridView);
        gridView.requestFocus();
    }



    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            /*case R.id.play:
                ScheduledTaskManager.getInstance(this).start();
                return true;
            case R.id.pause:
                ScheduledTaskManager.getInstance(this).stop();
                return true;*/
            case R.id.next:
                gridView.updateState();
                return true;
            case R.id.clear:
                gridView.clearGrid();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
