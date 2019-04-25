package personal.work.test.trabalhomobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaVencedor extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_vencedor);
        ImageView imagem = findViewById(R.id.imagem);
        TextView txt = findViewById(R.id.txtVencedor);
        TextView cabecalho = findViewById(R.id.txtVencedor2);
        Intent it = getIntent();

        String caminho = it.getStringExtra("caminho");
        String resultado = it.getStringExtra("resultado");


            Bitmap bitmap = BitmapFactory.decodeFile(caminho);
            imagem.setImageBitmap(bitmap);

        Log.d("resultado",resultado);
        if(resultado.equals("J1")){
            txt.setText("Jogador 1");
        }
        if(resultado.equals("J2")) {
            txt.setText("Jogador 2");
        }
        if(resultado.equals("Empate")){
            txt.setText("Deu Velha!");
            cabecalho.setText("");                                      //Com Else else o P1 da velha, sem esse Else o P1 e P2 funcionam normal
            imagem.setImageResource(R.drawable.velha);
        }
    }

    public void volta(View view){
        this.finish();
    }
}
