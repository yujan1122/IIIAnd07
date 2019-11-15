package tw.org.iii.iiiand07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private MyView myView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView = findViewById(R.id.myView);
    }

    public void clearMyView(View view) {
        myView.clear();
    }

    public void undo(View view) {
        myView.undo();
    }

    public void redo(View view) {
        myView.redo();
    }
}
