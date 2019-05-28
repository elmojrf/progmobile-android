package com.example.progmobile_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.progmobile_android.model.ManagerFacade;
import com.example.progmobile_android.model.entities.Event;
import com.example.progmobile_android.model.repository.ServerCallback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ManagerFacade managerFacade;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        managerFacade = new ManagerFacade(this);

        textView = findViewById(R.id.test);
        imageView = findViewById(R.id.imageView);

        //Exemplo de como usar o CallBack
        managerFacade.getListEvents(new ServerCallback() {
            @SuppressWarnings({"unchecked", "RedundantCast"})
            @Override
            public void onSuccess(Object object) {
                textView.setText(((List<Event>) object).toString());
            }
        });

        //Exemplo carregar imagem evento
        String url = "https://thegraphicsfairy.com/wp-content/uploads/blogger/_CarNcodpCMA/S-xGzI1quqI/AAAAAAAAHcw/6OV1SRWgWFI/s1600/ticket-graphicsfairy002d.jpg";
        Picasso.get().load(url).into(imageView);
    }
}
