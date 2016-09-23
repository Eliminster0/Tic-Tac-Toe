package mattsmith.TicTacToe;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.view.View;

public class GameScreen extends View {
    Paint paint;
    GameManager gameManager;
    Bitmap gameSurface, playerOne, playerTwo;
    Canvas game;
    Rect destination;
    Rect[] gameTiles;

    final int SHORT_SIDE = 600;
    final int LONG_SIDE = 800;
    int width = 0;
    int height = 0;

    public GameScreen(Context context) {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gameManager = (GameManager)context;
        destination = new Rect();

        initDraw();

    }

    public void initDraw() {
        if (getResources().getConfiguration().orientation
                   == Configuration.ORIENTATION_LANDSCAPE) {
            width = LONG_SIDE;
            height = SHORT_SIDE;
        } else {
            width = SHORT_SIDE;
            height = LONG_SIDE;
        }
        gameSurface = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        game = new Canvas(gameSurface);
        gameTiles = new Rect[]{
                new Rect(0, 0, width/3,height/3),
                new Rect(width/3,0, 2*width/3,height/3),
                new Rect(2*width/3,0, width, height/3),
                new Rect(0, height/3, width/3,2*height/3),
                new Rect(width/3, height/3, 2*width/3,2*height/3),
                new Rect(2*width/3, height/3, width,2*height/3),
                new Rect(0, 2*height/3, width/3,height),
                new Rect(width/3, 2*height/3, 2*width/3,height),
                new Rect(2*width/3, 2*height/3, width,height)
        };
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = 3;
        playerOne = BitmapFactory.decodeResource(getResources(), R.drawable.empire, bitmapOptions);
        playerTwo = BitmapFactory.decodeResource(getResources(), R.drawable.rebel, bitmapOptions);
    }

    protected void onDraw(Canvas canvas) {
        game.drawRGB(255, 255, 255);
        paint.setStyle(Paint.Style.FILL);

        //draw the game tiles
        for(int i = 0; i < 9; i++){
            if(i%2 == 0){
                paint.setColor(ContextCompat.getColor(getContext(), R.color.squareTwo));
                game.drawRect(gameTiles[i], paint);
            }
            else{
                paint.setColor(ContextCompat.getColor(getContext(), R.color.squareOne));
                game.drawRect(gameTiles[i], paint);
            }
        }

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.YELLOW);
        for (int i = 0; i < 3*height/4; i+=height/3)
            game.drawLine(0, i, width, i, paint);
        for (int i = 0; i < 3*width/4; i+=width/3)
            game.drawLine(i, 0, i, height, paint);

        paint.setTextSize(22);

        for(int i = 0; i < 9; i++){
            switch (gameManager.gameState[i]){
                case 0:
                    break;
                case 1:
                    paint.setColor(Color.BLACK);
                    game.drawBitmap(playerOne,gameTiles[i].exactCenterX() - playerOne.getWidth()/2,
                                    gameTiles[i].exactCenterY() - playerOne.getHeight()/2, paint);
                    break;
                case 2:
                    paint.setColor(Color.RED);
                    game.drawBitmap(playerTwo, gameTiles[i].exactCenterX() - playerTwo.getWidth()/ 2,
                                            gameTiles[i].centerY() - playerTwo.getHeight()/2, paint);
                    break;
                default:
                    break;
            }
        }

        if(gameManager.allSpacesOccupied(gameManager.gameState) ||
              gameManager.checkForWinner(gameManager.gameState)){
            paint.setTypeface(StartScreen.jedi);
            paint.setColor(ContextCompat.getColor(getContext(), R.color.gameOver));
            paint.setTextSize(80);
            if(width == SHORT_SIDE){
                game.drawText(gameManager.getString(R.string.game_over),width/12, height/2, paint);
            } else {
                game.drawText(gameManager.getString(R.string.game_over),width/6 + 5, height*4/7, paint);
            }

        }

        // copy finished game draw update to screen
        canvas.getClipBounds(destination);
        canvas.drawBitmap(gameSurface, null, destination, null);
    }
}
