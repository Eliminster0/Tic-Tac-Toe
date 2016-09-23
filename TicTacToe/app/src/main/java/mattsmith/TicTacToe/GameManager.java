package mattsmith.TicTacToe;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

@SuppressLint("ClickableViewAccessibility")
public class GameManager extends Activity implements View.OnTouchListener {
    protected GameScreen gameScreen;
    int x;
    int y;
    int[] gameState = new int[10];
    static final String SAVEDGAME = "savedGame";
    public int tileSelected = 0;
    public int twoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        twoPlayer = getIntent().getExtras().getInt("value");

        gameScreen = new GameScreen(this);
        gameScreen.setOnTouchListener(this);
        setContentView(gameScreen);

        for (int i = 0; i < 10; i++){
            gameState[i] = 0;
        }
    }

    protected void onPause() { super.onPause(); }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("GameScale", "rotate");
        gameScreen.initDraw();
        // updates last touch location for rotation
        int tmp = x;
        x = y;
        y = tmp;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(!checkForWinner(gameState) && !allSpacesOccupied(gameState)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    x = (int) event.getX();
                    y = (int) event.getY();
                    int Xpos = checkXpos(v, x);
                    int Ypos = checkYpos(v, y);
                    Log.d("GameScale", String.format(" %d %d", Xpos, Ypos));
                    tileSelected = (Ypos * 3) + Xpos;
                    updateGameState(tileSelected);
                    Log.d("GameScale", "" + tileSelected);
                    if(!checkForWinner(gameState) && twoPlayer == 0) {
                        aiTurn();
                    }
                    break;
                default:
            }
        }
        gameScreen.invalidate();
        return true;
    }

    private void aiTurn() {
        for(int i = 0; i < 9; i++){
            if(gameState[i] == 0){
                gameState[i] = 2;
                gameState[9] = 0;
                break;
            }
        }
    }

    public int checkXpos(View v, int x) {
        int width = v.getWidth();
        if (x <= width / 3) {
            return 0;
        } else if (x <= 2 * width / 3 && x > width / 3) {
            return 1;
        } else {
            return 2;
        }
    }

    public int checkYpos(View v,int y) {
        int height = v.getHeight();
        if (y <= height / 3) {
            return 0;
        } else if (y <= 2 * height / 3 && y > height / 3) {
            return 1;
        } else {
            return 2;
        }
    }

    public void updateGameState(int tile){
       if(gameState[9] == 0) {
           if (gameState[tile] == 0) {
               gameState[tile] = 1;
               gameState[9]    = 1;
           }
       }
       else {
           if (gameState[tile] == 0) {
               gameState[tile] = 2;
               gameState[9]    = 0;
           }
       }
    }

    public boolean allSpacesOccupied(int[] gameState){
        for(int i = 0; i < 9; i++){
            if(gameState[i] == 0){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onSaveInstanceState (Bundle savedInstanceState) {
        savedInstanceState.putIntArray(SAVEDGAME, gameState);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        gameState = savedInstanceState.getIntArray(SAVEDGAME);

    }

    public boolean checkForWinner(int gameState[]){
        for(int i = 1; i < 3; i++){
            if(gameState[0] == i && gameState[1] == i && gameState[2] == i){
                return true;
            }else if(gameState[0] == i && gameState[3] == i && gameState[6] == i ){
                return true;
            }else if(gameState[0] == i && gameState[4] == i && gameState[8] == i){
                return true;
            }else if(gameState[3] == i && gameState[4] == i && gameState[5] == i){
                return true;
            }else if (gameState[6] == i && gameState[7] == i && gameState[8] == i){
                return true;
            }else if (gameState[6] == i && gameState[4] == i && gameState[2] == i){
                return true;
            }
        }
        return false;
    }
}