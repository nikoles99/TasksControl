package ru.qulix.olesyuknv.taskscontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageButton addButton = (ImageButton)findViewById(R.id.add_button);
        addButton.setOnClickListener(this);
        /*TextView time = (TextView)findViewById(R.id.text);
        time.setOnClickListener(this);*/
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_button:
                startActivity(new Intent("android.intent.action.InputTaskActivity"));
            case R.id.time:
               // showDialogF(1);

        }

    }
}