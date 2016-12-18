package gameoflife.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import gameoflife.R;
import gameoflife.game.Cell;
import gameoflife.game.Universe;

import static android.view.MotionEvent.*;

/**
 * Created by Duygu on 24/11/2016.
 */

public class GridView extends View {

    private volatile boolean isRunning = false;
    private Context context;
    private int canvasWidth;
    private int canvasHeight;

    public static final int CELL_SIZE = 60;
    public static int WIDTH = 500;
    public static int HEIGHT = 500;

    List<CellRect> rectangles = new ArrayList<CellRect>();
    private Universe universe;
    private Cell[][] grid;
    private static int ROW;
    private static int COLUMN;

    public GridView(Context context) {
        super(context);
        this.context = context;

    }

    private void initializeGrid() {
        for (int h = 0; h < HEIGHT; h++) {
            for (int w = 0; w < WIDTH; w++) {
                Rect rectangle = new Rect(w * CELL_SIZE,
                        h * CELL_SIZE,
                        (w * CELL_SIZE) +CELL_SIZE,
                        (h * CELL_SIZE) +CELL_SIZE);
                CellRect cellRect = new CellRect(false, rectangle, new Cell(Cell.CellState.DEAD));
                rectangles.add(cellRect);
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();

        WIDTH = canvasWidth / CELL_SIZE;
        HEIGHT = canvasHeight / CELL_SIZE;
        ROW = HEIGHT;
        COLUMN = WIDTH;
        if (rectangles.isEmpty()) initializeGrid();

        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.background));
        // draw background
        canvas.drawRect(0, 0, getWidth(), getHeight(), background);

        for(CellRect cell : rectangles) {
            canvas.drawRect(cell.getRectangle(), cell.getCellPaint());
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        int touchY =  (int) event.getY();
        switch(event.getAction()){
            case ACTION_DOWN:
                System.out.println("Touching down!");
                updateRectangles(touchX, touchY);
                break;
            case ACTION_UP:
                System.out.println("Touching up!");
                break;
            case ACTION_MOVE:
                System.out.println("Sliding your finger around on the screen.");
                break;
        }
        return true;
    }

    public void updateRectangles(int touchX, int touchY) {
        for(CellRect cellRect : rectangles){
            if(cellRect.getRectangle().contains(touchX,touchY)){

                Toast.makeText(context, "Touched down", Toast.LENGTH_SHORT).show();
                if(cellRect.isAlive()) {
                    cellRect.setAlive(false);
                } else {
                    cellRect.setAlive(true);
                }
                invalidate();
            }
        }
        getGrid();
    }

    public void clearGrid() {
        for(CellRect cellRect : rectangles){
            cellRect.setAlive(false);
        }
        invalidate();
    }

    public void updateState() {
        universe = new Universe(getGrid());
        grid = universe.getNextState();
        setRectangles(grid.clone());
        invalidate();
    }

    public Cell[][] getGrid() {
        Log.d("ROW:COL\t", ROW+":"+COLUMN);
        grid = new Cell[ROW][COLUMN];
        for (int i=0; i < ROW; i++) {
            for (int j=0; j < COLUMN; j++) {
                grid[i][j] = rectangles.get( i * COLUMN + j ).getCell();
            }
        }
        printArrayToScreen();
        return grid;
    }

    public void setRectangles(Cell[][] clone) {
        for (int i=0; i < ROW; i++) {
            for (int j=0; j < COLUMN; j++) {
                Cell c = clone[i][j];
                rectangles.get( i * COLUMN + j ).setAlive(c.getState()== Cell.CellState.ALIVE ? true : false);
            }
        }
    }

    private void printArrayToScreen() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<ROW;i++) {
            for(int j=0; j<COLUMN;j++) {
                sb.append((grid[i][j].getState()== Cell.CellState.DEAD ? "O" : "X") + "\t");
            }
            sb.append("\n"); // Append newline after every row
        }
        Log.d("printGrid:\n", sb.toString());
    }
}