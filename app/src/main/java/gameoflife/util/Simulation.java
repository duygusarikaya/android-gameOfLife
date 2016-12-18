package gameoflife.util;

import gameoflife.ControllerActivity;
import gameoflife.ui.GridView;

/**
 * Created by Duygu on 18/12/2016.
 */

public class Simulation implements Runnable {

    @Override
    public void run() {
        ControllerActivity.getGrid().updateState();
    }


}
