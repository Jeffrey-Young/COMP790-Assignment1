package edu.unc.jeffrey.assignment1;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jeffrey on 9/5/2017.
 */

public class Square {

    private static final int SQUARE_SIZE = 60;

    private Button b;
    private int row;
    private int column;
    private boolean hasQueen;
    private Context ctx;

    public Square(Context ctx, int row, int column) {
        this.ctx = ctx;
        this.row = row;
        this.column = column;
        this.hasQueen = false;

        b = new Button(ctx);
        b.setLayoutParams(new ActionBar.LayoutParams(SQUARE_SIZE, SQUARE_SIZE));
        if ((row % 2) == (column % 2)) {
            b.setBackgroundColor(Color.rgb(245,222,179));
        } else {
            b.setBackgroundColor(Color.rgb(139,69,19));
        }
        b.setText(row + "" + column);
        b.setId((row * 10) + column);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                Square current = MainActivity.getSquareByID(button.getId());
                if (current.getHasQueen()) {
                    //set appropriate background color based on position
                    if ((current.getRow() % 2) == (current.getColumn() % 2)) {
                        b.setBackgroundColor(Color.rgb(245,222,179));
                    } else {
                        b.setBackgroundColor(Color.rgb(139,69,19));
                    }
                    current.setHasQueen(false);
                    MainActivity.setRow(current.getRow(), -1);
                    MainActivity.setColumn(current.getColumn(), -1);
                } else {
                    button.setBackgroundResource(R.drawable.queen);
                    current.setHasQueen(true);
                    MainActivity.setRow(current.getRow(), 1);
                    MainActivity.setColumn(current.getColumn(), 1);
                }
                //now check for collision if necessary (if this was a queen placement)
                if (current.getHasQueen() && current.hasCollision()) {
                    current.setCollisionText();
                }
                current.checkGameOver();
            }
        });
    }

    public void setCollisionText() {
        Toast.makeText(ctx.getApplicationContext(), "COLLISION", Toast.LENGTH_LONG).show();
        Square[][] board = MainActivity.getBoard();
        this.setGameOver();
    }



    private boolean hasCollision() {
        Square[][] board = MainActivity.getBoard();
        int[] rows = MainActivity.getRows();
        int[] columns = MainActivity.getColumns();

        //check rows
        for (int i = 0; i < rows.length; i++) {
            if (rows[i] > 1) {
                Log.v("COLLISION", "ROW COLLISION");
                return true;
            }
        }
        //check columns
        for (int i = 0; i < columns.length; i++) {
            if (columns[i] > 1) {
                Log.v("COLLISION", "COLUMN COLLISION");
                return true;
            }
        }

        // check diagonals, intuition here is whenever vertical and horizontal distances are equal
        // we are on a diagonal
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (board[row][column].getHasQueen()) {
                    for (int row2 = 0; row2 < 8; row2++) {
                        for (int column2 = 0; column2 < 8; column2++) {
                            if (row2 == row && column2 == column) {
                                continue;
                            }
                            if (board[row2][column2].hasQueen && (Math.abs(row2 - row) == Math.abs(column2 - column))) {
                                Log.v("COLLISION", "DIAGONAL COLLISION");
                                return true;
                            }
                        }
                    }

                }
            }
        }

        return false;
    }

    public void checkGameOver() {
        Square[][] board = MainActivity.getBoard();
        int sum = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getHasQueen()) {
                    sum++;
                }
            }
        }
        if (sum >= 8) {
            Toast.makeText(ctx.getApplicationContext(), "YOU WON!!!!!!", Toast.LENGTH_SHORT).show();

            setGameOver();
        }
    }

    private void setGameOver() {
        Square[][] board = MainActivity.getBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Button x = board[i][j].getButton();
                x.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ctx.getApplicationContext(), "Press Restart to play again!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    public Button getButton() {
        return b;
    }

    public boolean getHasQueen() {
        return hasQueen;
    }

    public void setHasQueen(boolean option) {
        hasQueen = option;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Context getContext() {
        return ctx;
    }
}
