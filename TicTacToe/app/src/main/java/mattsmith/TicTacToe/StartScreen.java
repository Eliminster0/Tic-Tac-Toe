package mattsmith.TicTacToe;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class StartScreen extends AppCompatActivity {

    TextView title;
    public static Typeface jedi;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        jedi = Typeface.createFromAsset(getAssets(), "font/jedi.ttf");

        title = (TextView) findViewById(R.id.textView);
        title.setTypeface(jedi);
        title.setTextSize(70);
        title.setTextColor(ContextCompat.getColor(this, R.color.titletext));
        title.setLineSpacing(0, (float) 0.5);
        title.setText(getString(R.string.game_name));
    }

    public void startGameButton(View view){
        Intent intent = new Intent(this,GameManager.class );
        intent.putExtra("value", 0);
        startActivity(intent);
    }

    public void startMultiplayerGameButton(View view){
        Intent intent = new Intent(this,GameManager.class );
        intent.putExtra("value", 1);
        startActivity(intent);
    }
}
