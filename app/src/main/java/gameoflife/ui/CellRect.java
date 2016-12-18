package gameoflife.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import gameoflife.game.Cell;

/**
 * Created by Duygu on 24/11/2016.
 */

public class CellRect {
    private Rect rectangle;
    private boolean isAlive;
    private Paint cellPaint;
    private  Cell cell;

    public CellRect(boolean isAlive, Rect rectangle, Cell cell) {
        this.isAlive = isAlive;
        this.rectangle = rectangle;
        this.cell = cell;

        cellPaint = new Paint();
        cellPaint.setColor(Color.parseColor("#FFFDD0"));
        cellPaint.setStrokeWidth(3);
        if(isAlive)
            cellPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        else
            cellPaint.setStyle(Paint.Style.STROKE);
    }

    public Rect getRectangle() {
        return rectangle;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Paint getCellPaint() {
        return cellPaint;
    }

    public void setAlive(boolean alive) {
        //System.out.println("Cell life:"+alive);
        isAlive = alive;
        if(isAlive) {
            cellPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            cell.setState(Cell.CellState.ALIVE);
        } else {
            cellPaint.setStyle(Paint.Style.STROKE);
            cell.setState(Cell.CellState.DEAD);
        }

    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
