package personal.work.test.trabalhomobile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public File arquivoFoto;

    private final int CAMERA1 = 1;
    private final int CAMERA2 = 3;
    private final int PERMISSAO_REQUEST = 2;
    private ImageButton p1;
    private ImageButton p2;
    private ImageButton btn1;
    private ImageButton btn2;
    private ImageButton btn3;
    private ImageButton btn4;
    private ImageButton btn5;
    private ImageButton btn6;
    private ImageButton btn7;
    private ImageButton btn8;
    private ImageButton btn9;
    private Bitmap selfie1 = null;
    private Bitmap selfie2 = null;
    private Bitmap jogador = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        p1 = (ImageButton) findViewById(R.id.p1);
        p2 = (ImageButton) findViewById(R.id.p2);
        btn1 = (ImageButton) findViewById(R.id.btn1);
        btn2 = (ImageButton) findViewById(R.id.btn2);
        btn3 = (ImageButton) findViewById(R.id.btn3);
        btn4 = (ImageButton) findViewById(R.id.btn4);
        btn5 = (ImageButton) findViewById(R.id.btn5);
        btn6 = (ImageButton) findViewById(R.id.btn6);
        btn7 = (ImageButton) findViewById(R.id.btn7);
        btn8 = (ImageButton) findViewById(R.id.btn8);
        btn9 = (ImageButton) findViewById(R.id.btn9);


        // Pede permissão para acessar as mídias gravadas no dispositivo
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSAO_REQUEST);
            }
        }

        // Pede permissão para escrever arquivos no dispositivo
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSAO_REQUEST);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && (requestCode == CAMERA1 || requestCode == CAMERA2)) {
            sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(arquivoFoto))
            );
            setPhoto(arquivoFoto.getAbsolutePath(), requestCode);
        }

    }

    public void clear(View view){

        if(selfie1 != null || selfie2 != null) {
            p1.setImageDrawable(null);
            selfie1 = null;
            p2.setImageDrawable(null);
            selfie2 = null;


        }


    }

    public void mostraVencedor(View view){


            Intent intent = new Intent(MainActivity.this, Vencedor.class);
            Bundle b = new Bundle();
            b.putParcelable("img",jogador);

            intent.putExtra("bundle",b);
            startActivity(intent);

    }


    public void joga(View view){
        ImageButton botao = (ImageButton) view;
        botao.setImageBitmap(jogador);
        botao.setScaleType(ImageView.ScaleType.FIT_XY);
        if(jogador.equals(selfie1)){
            jogador = selfie2;
        }else{
            jogador = selfie1;
        }
    }

    private void setPhoto(String caminho, int id) {
        Bitmap bitmap =
                BitmapFactory.decodeFile(caminho);


        if(id == CAMERA1){
            p1.setImageBitmap(bitmap);
            p1.setScaleType(ImageView.ScaleType.FIT_XY);
            selfie1 = bitmap;
        }else
            if(id == CAMERA2) {
                p2.setImageBitmap(bitmap);
                p2.setScaleType(ImageView.ScaleType.FIT_XY);
                selfie2 = bitmap;
            }
    }


    public void tirarFoto(View view) {
        String tag = (String) view.getTag();
        if(selfie1 != null && selfie2 != null){
            if(tag.equals("btn_p1")) {
                jogador = selfie1;
                mostraMsg("Jogador 1 começa!");
            }
            else {
                jogador = selfie2;
                mostraMsg("Jogador 2 começa!");

            }
        }else {
            Intent takePictureIntent = new
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                try {
                    arquivoFoto = criaArquivo();
                } catch (IOException ex) {
                    mostraAlerta(getString(R.string.erro), getString(
                            R.string.erro_salvando_foto));
                }
                if (arquivoFoto != null) {


                    if (tag.equals("btn_p1")) {
                        Uri photoURI = FileProvider.getUriForFile(getBaseContext(),
                                getBaseContext().getApplicationContext().getPackageName() +
                                        ".provider", arquivoFoto);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, CAMERA1);
                    } else {
                        Uri photoURI = FileProvider.getUriForFile(getBaseContext(),
                                getBaseContext().getApplicationContext().getPackageName() +
                                        ".provider", arquivoFoto);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, CAMERA2);
                    }
                }
            }
        }
    }

    private File criaArquivo() throws IOException {
        String timeStamp = new
                SimpleDateFormat("yyyyMMdd_Hhmmss").format(
                new Date());
        File pasta = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imagem = new File(pasta.getPath() + File.separator
                + "JPG_" + timeStamp + ".jpg");
        return imagem;
    }
    private void mostraAlerta(String titulo, String mensagem) {
        android.app.AlertDialog alertDialog = new
                android.app.AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(mensagem);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


        alertDialog.show();
    }

    private void mostraMsg(String msg){
        Context context = getApplicationContext();

        Toast toast = Toast.makeText(context,msg,Toast.LENGTH_LONG);
        toast.show();
    }
}
