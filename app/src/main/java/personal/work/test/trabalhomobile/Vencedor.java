package personal.work.test.trabalhomobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class Vencedor extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_vencedor);

        Intent intent = getIntent();
        Bundle extra = intent.getBundleExtra("bundle");

        Bitmap bmp = extra.getParcelable("img");
        ImageView image = (ImageView) findViewById(R.id.img_Vencedor);
        image.setImageBitmap(bmp);
    }
}
