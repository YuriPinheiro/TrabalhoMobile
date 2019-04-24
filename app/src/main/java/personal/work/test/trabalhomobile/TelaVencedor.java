package personal.work.test.trabalhomobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TelaVencedor extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_vencedor);
        ImageView imagem = findViewById(R.id.imagem);

        Intent it = getIntent();
        String caminho = it.getStringExtra("caminho");
        Bitmap bitmap =
                BitmapFactory.decodeFile(caminho);

        imagem.setImageBitmap(bitmap);

    }

    public void volta(View view){
        this.onResume();
    }
}
