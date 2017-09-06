package edu.unc.jeffrey.assignment1;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static Square[][] board;
    private static int[] rows;
    private static int[] columns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawGrid(null);
    }

    public void drawGrid(View v) {
        board = new Square[8][8];
        rows = new int[8];
        columns = new int[8];
        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        grid.removeAllViews(); // necessary to reset
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Square(this, i, j);
                grid.addView(board[i][j].getButton());
            }
        }
    }

    public static Square getSquareByID(int id) {
        return board[id / 10][id % 10];
    }

    public static Square[][] getBoard() {
        return board;
    }

    public static int[] getRows() {
        return rows;
    }

    public static int[] getColumns() {
        return columns;
    }


    public static void setRow(int i, int value) {
        rows[i] += value;
    }

    public static void setColumn(int i, int value) {
        columns[i] += value;
    }

}
