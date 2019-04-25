package personal.work.test.trabalhomobile;

import android.util.Log;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.toUnsignedString;
import static java.lang.Integer.valueOf;

public class Jogo {
    private static final int J1 = 5;
    private static final int J2 = 1;
    private int atual = 0;
    private int campeao = 0;
    private int tabuleiro[][] = new int[3][3];
    private int njogadas = 0;
    private String  label = "";

    public Jogo(){
        zeraTabuleiro();
    }

    public int jogar(String position){
        setPosition(position);
        VerificaCampeao();
        this.switchPlayer();
        this.njogadas++;
        if(this.njogadas==9){
            this.campeao = 10;
        }
        return this.campeao;
    }

    public void setJogador(String j){
        if(j.equals("J1")){
            this.atual = J1;
        }else if(j.equals("J2")){
            this.atual = J2;
        }
    }

    public void clear (){
        zeraTabuleiro();
        this.atual = 0;
        this.campeao = 0;
        this.njogadas = 0;
        this.label = "";
    }

    public String getLabel(){
        return this.label;
    }

    private void setPosition(String v){
        String r[] = v.split("_");
        int linha = parseInt(r[0]);
        int coluna = parseInt(r[1]);

        if(tabuleiro[linha][coluna]==0){
            tabuleiro[linha][coluna] = this.atual;
        }
    }
    private void switchPlayer(){
        if(this.atual==J1){
            this.atual =J2;
        }else{
            this.atual =J1;
        }
    }
    private void zeraTabuleiro(){
        for(int i = 0;i<3;i++){
            for(int j = 0;j<3;j++){
                tabuleiro[i][j] = 0;
            }
        }
    }

    private void VerificaCampeao(){
        if(this.campeao!=10){
            Log.d("campeao",Integer.toString(this.campeao));
            verificaLinha();
            Log.d("campeaolinha",Integer.toString(this.campeao));
            if(this.campeao ==0){
                verificaColuna();
                Log.d("campeaocoluna",Integer.toString(this.campeao));
                if(this.campeao ==0){

                    verificaDiagonal();
                    Log.d("campeaodiagonal",Integer.toString(this.campeao));
                }
            }
        }


    }

    private void verificaLinha(){
        int soma = 0;
        for(int i = 0;i<3;i++){
            for(int j = 0;j<3;j++){
                soma = soma + tabuleiro[i][j] ;
            }
            Log.d("vlsoma",Integer.toString(soma));
            if(soma != 0){
                if(soma==15){
                    this.campeao = J1;
                    this.label = "Linha " + valueOf(i);
                    break;
                }else if(soma==3){
                    this.campeao = J2;
                    this.label = "Linha " + valueOf(i);
                    break;
                }
            }

            soma = 0;
        }
    }

    private void verificaColuna(){
        int soma = 0;
        for(int j = 0;j<3;j++){
            for(int i = 0;i<3;i++){
                soma = soma + tabuleiro[i][j] ;
            }
            Log.d("vcsoma",Integer.toString(soma));
            if(soma!=0){
                if(soma==15){
                    this.label = "Coluna " + valueOf(j);
                    this.campeao = J1;
                    break;
                }else if(soma==3){
                    this.label = "Coluna " + valueOf(j);
                    this.campeao = J2;
                    break;
                }
            }

            soma = 0;
        }
    }

    private void verificaDiagonal(){
        int diagonal = tabuleiro[0][0] + tabuleiro[1][1] + tabuleiro[2][2];
        Log.d("diagonalp",Integer.toString(diagonal));
        if(diagonal!=0){
            if(diagonal==15){
                this.label = "Diagonal_Principal";
                this.campeao = J1;
            }else if(diagonal==3){
                this.label = "Diagonal_Principal";
                this.campeao = J2;
            }else{
                diagonal = tabuleiro[2][0] + tabuleiro[1][1] + tabuleiro[0][2];
                Log.d("diagonals",Integer.toString(diagonal));
                if(diagonal==15){
                    this.label = "Diagonal_Secundaria";
                    this.campeao = J1;
                }else if(diagonal==3){
                    this.label = "Diagonal_Secundaria";
                    this.campeao = J2;
                }
            }
        }
    }
}
