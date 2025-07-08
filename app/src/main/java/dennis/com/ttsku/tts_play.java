package dennis.com.ttsku;

import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class tts_play extends AppCompatActivity {
    static Cursor cursor;
    static SQLiteDatabase db;
    DatabaseOpenHelper dbHelper;
    private TextView[][]  txt;
    private TextView[] btnHuruf;
    TextView txSoal;
    ImageView btnKanan,btnKiri;
    Spinner spinTTSNo;
    String jenis="D",jawaban="",jnssoal="D";
    Integer nSoal,maxRow=0,maxCol=0,maxSoal;
    Integer minRow=0,minCol=0,warna;
    Integer row1,col1,row2,col2,nomor;
    Boolean tblJawab=true,selesai=false,sukses;
    ProgDialog pDialog;


    MediaPlayer mediaPlayer;
    private static final int[][] arrBox = {{R.id.t01_01,R.id.t01_02,R.id.t01_03,R.id.t01_04,
            R.id.t01_05,R.id.t01_06,R.id.t01_07,R.id.t01_08,R.id.t01_09,R.id.t01_10,
            R.id.t01_11,R.id.t01_12},{R.id.t02_01,R.id.t02_02,R.id.t02_03,R.id.t02_04,
            R.id.t02_05,R.id.t02_06,R.id.t02_07,R.id.t02_08,R.id.t02_09,R.id.t02_10,
            R.id.t02_11,R.id.t02_12},{R.id.t03_01,R.id.t03_02,R.id.t03_03,R.id.t03_04,
            R.id.t03_05,R.id.t03_06,R.id.t03_07,R.id.t03_08,R.id.t03_09,R.id.t03_10,
            R.id.t03_11,R.id.t03_12},{R.id.t04_01,R.id.t04_02,R.id.t04_03,R.id.t04_04,
            R.id.t04_05,R.id.t04_06,R.id.t04_07,R.id.t04_08,R.id.t04_09,R.id.t04_10,
            R.id.t04_11,R.id.t04_12},{R.id.t05_01,R.id.t05_02,R.id.t05_03,R.id.t05_04,
            R.id.t05_05,R.id.t05_06,R.id.t05_07,R.id.t05_08,R.id.t05_09,R.id.t05_10,
            R.id.t05_11,R.id.t05_12},{R.id.t06_01,R.id.t06_02,R.id.t06_03,R.id.t06_04,
            R.id.t06_05,R.id.t06_06,R.id.t06_07,R.id.t06_08,R.id.t06_09,R.id.t06_10,
            R.id.t06_11,R.id.t06_12},{R.id.t07_01,R.id.t07_02,R.id.t07_03,R.id.t07_04,
            R.id.t07_05,R.id.t07_06,R.id.t07_07,R.id.t07_08,R.id.t07_09,R.id.t07_10,
            R.id.t07_11,R.id.t07_12},{R.id.t08_01,R.id.t08_02,R.id.t08_03,R.id.t08_04,
            R.id.t08_05,R.id.t08_06,R.id.t08_07,R.id.t08_08,R.id.t08_09,R.id.t08_10,
            R.id.t08_11,R.id.t08_12},{R.id.t09_01,R.id.t09_02,R.id.t09_03,R.id.t09_04,
            R.id.t09_05,R.id.t09_06,R.id.t09_07,R.id.t09_08,R.id.t09_09,R.id.t09_10,
            R.id.t09_11,R.id.t09_12},{R.id.t10_01,R.id.t10_02,R.id.t10_03,R.id.t10_04,
            R.id.t10_05,R.id.t10_06,R.id.t10_07,R.id.t10_08,R.id.t10_09,R.id.t10_10,
            R.id.t10_11,R.id.t10_12},{R.id.t11_01,R.id.t11_02,R.id.t11_03,R.id.t11_04,
            R.id.t11_05,R.id.t11_06,R.id.t11_07,R.id.t11_08,R.id.t11_09,R.id.t11_10,
            R.id.t11_11,R.id.t11_12},{R.id.t12_01,R.id.t12_02,R.id.t12_03,R.id.t12_04,
            R.id.t12_05,R.id.t12_06,R.id.t12_07,R.id.t12_08,R.id.t12_09,R.id.t12_10,
            R.id.t12_11,R.id.t12_12}};
    private static final int[] arrBtnHuruf = {R.id.btn00,R.id.btn01,R.id.btn02,R.id.btn03,
            R.id.btn04,R.id.btn05,R.id.btn06,R.id.btn07,R.id.btn08,R.id.btn09,R.id.btn10,
            R.id.btn11};

    ActivityResultLauncher<Intent> congrat = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    int result = o.getResultCode();
                    //Intent data = o.getData();
                    if (result == RESULT_OK) {
                        tblJawab=true;
                        showMoveButton(false);
                        txSoal.setText("ANDA BERHASIL MENYELESAIKAN TTS INI");

                    }

                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts_play);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Keluar();
            }
        });

        txSoal = findViewById(R.id.txt_soal);
        spinTTSNo = findViewById(R.id.spinTTS);
        btnKanan = findViewById(R.id.btn_kanan);
        btnKiri = findViewById(R.id.btn_kiri);
        dbHelper = new DatabaseOpenHelper(this,"ttsdb.db");
        db = dbHelper.getReadableDatabase();
        txt = new TextView[13][13];
        btnHuruf = new TextView[12];
        pDialog = new ProgDialog(this);

        String w=FileHelper.ReadFile(this,"warna.txt");
        if (w.isEmpty()) {w="0";}
        warna=Integer.parseInt(w);
        goWarna();
        TTSid();
        String nomor=FileHelper.ReadFile(this,"nomor.txt");
        if (nomor.isEmpty()) {nomor="0";}
        int n = Integer.parseInt(nomor);
        if(n==0) {n++;}
        spinTTSNo.setSelection(n-1);
        nSoal=Integer.parseInt(spinTTSNo.getSelectedItem().toString());

        cursor=db.rawQuery("SELECT distinct(id) FROM soal_mst",null);
        cursor.moveToFirst();
        maxSoal=cursor.getCount();
        cursor.close();

        for(int r=1;r<13;r++) {
            for(int i=1;i<13;i++) {
                txt[r][i]= findViewById(arrBox[r-1][i-1]);
                txt[r][i].setTextSize(20);
                ViewGroup.LayoutParams lp = txt[r][i].getLayoutParams();
                lp.width=60;
                lp.height=75;
                txt[r][i].setOnClickListener((View)-> {
                    int btnid=View.getId();
                    String ss=getResources().getResourceEntryName(btnid);
                    int x = Integer.parseInt(ss.substring(1,3));
                    int y = Integer.parseInt(ss.substring(4,6));
                    boxClicked(x,y);
                });
            }
        }

        for(int i=0;i<12;i++) {
            btnHuruf[i]= findViewById(arrBtnHuruf[i]);
            btnHuruf[i].setOnClickListener((View)-> {
                int btnid=View.getId();
                String ss=getResources().getResourceEntryName(btnid);
                int nn =  Integer.parseInt(ss.substring(ss.length()-2));
                btnHurufClicked(nn);
            });
        }

        goTTS();

        spinTTSNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nSoal=Integer.parseInt(spinTTSNo.getSelectedItem().toString());
                FileHelper.saveToFile(String.valueOf(nSoal),"nomor.txt");
                jenis="D";
                goTTS();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        findViewById(R.id.tts_bg).setOnClickListener((View)-> {
            warna++;
            if(warna==5) {warna=0;}
            goWarna();

        });

        findViewById(R.id.btn_info).setOnClickListener((View)-> {
            //Intent intent = new Intent(tts_play.this, info_tts.class);
            Intent intent = new Intent(tts_play.this, about.class);
            congrat.launch(intent);

        });

        findViewById(R.id.btn_download).setOnClickListener((View)-> {
            ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if(conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()) {
                goDownload();
            } else {
                Toast.makeText(getApplicationContext(),"Gadget anda tidak terkoneksi ke internet",Toast.LENGTH_LONG).show();
            }

        });

        findViewById(R.id.btn_prev).setOnClickListener((New)-> {
            if(nSoal>1) {
                nSoal--;
            }else {
                nSoal=maxSoal;
            }
            spinTTSNo.setSelection(nSoal-1);
        });

        findViewById(R.id.btn_next).setOnClickListener((New)-> {
            if(nSoal<maxSoal) {
                nSoal++;
            }else {
                nSoal=1;
            }
            spinTTSNo.setSelection(nSoal-1);
        });

        btnKanan.setOnClickListener((View)-> { //->Ke kanan atau Turun
            if(!jawaban.isEmpty()){
                if(jnssoal.equals("D")) {
                    if(col1 < maxCol ) {
                        txt[row1][col1].setTextColor(getColor(R.color.black));
                        txt[row1][col1].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_aktif));;
                        col1++;
                        txt[row1][col1].setTextColor(getColor(R.color.white));
                        txt[row1][col1].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_input));
                    }
                } else {
                    if(row1 < maxRow) {
                        txt[row1][col1].setTextColor(getColor(R.color.black));
                        txt[row1][col1].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_aktif));;
                        row1++;
                        txt[row1][col1].setTextColor(getColor(R.color.white));
                        txt[row1][col1].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_input));;

                    }
                }
            }

        });

        btnKiri.setOnClickListener((View)-> { //->Ke kiri atau Naik
            if(!jawaban.isEmpty()){
                if(jnssoal.equals("D")) {
                    if(col1 > minCol ) {
                        txt[row1][col1].setTextColor(getColor(R.color.black));
                        txt[row1][col1].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_aktif));;
                        col1--;
                        txt[row1][col1].setTextColor(getColor(R.color.white));
                        txt[row1][col1].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_input));
                    }
                } else {
                    if(row1>minRow) {
                        txt[row1][col1].setTextColor(getColor(R.color.black));
                        txt[row1][col1].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_aktif));;
                        row1--;
                        txt[row1][col1].setTextColor(getColor(R.color.white));
                        txt[row1][col1].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_input));;

                    }
                }
            }
        });

    }

    private void ClearBox(){
        for(int i = 1;i<=12;i++) {
            for(int j = 1;j<=12;j++) {
                txt[i][j].setBackgroundColor(Color.BLACK);
                txt[i][j].setTextColor(getColor(R.color.black));
                txt[i][j].setText("*");
                txt[i][j].setHint("a");
            }
        }
    }
    private void boxClicked(Integer r, Integer c){
        if(txt[r][c].getText() == "*") {return;}
        if(jenis.equals("D")) {
            soalMendatar(r,c);
        } else {soalMenurun(r,c);}

    }

    private void soalMendatar(int r, int c) {
        int col=1;
        if(c > 1) {
            for(int i=1;i<13;i++) {
                col=c-i;
                if(txt[r][col].getText().toString().equals("*")) {
                    col=col+1;
                    break;
                }else if(col==1) {break;}
            }
        }
        cursor = db.rawQuery("SELECT COUNT(*) FROM soal_mst WHERE jenis='D' AND row1="+r+" AND col1="+col+" AND id="+nSoal,null);
        cursor.moveToFirst();
        if(cursor.getInt(0) > 0) {
            cursor.close();
            BoxColor("E");
            vSoal(r, col);
        } else {
            soalMenurun(r,c);
        }
    }

    private void soalMenurun(int r, int c) {
        int row=1;
        if(r > 1) {
            for(int i=1;i<13;i++) {
                row=r-i;
                if(txt[row][c].getText().toString().equals("*")) {
                    row=row+1;
                    break;
                }else if(row==1) {break;}
            }
        }
        cursor = db.rawQuery("SELECT COUNT(*) FROM soal_mst WHERE jenis='T' AND row1="+row+" AND col1="+c+" AND id="+nSoal,null);
        cursor.moveToFirst();
        if(cursor.getInt(0) > 0) {
            cursor.close();
            BoxColor("E");
            vSoal(row, c);
        } else {
            soalMendatar(r,c);
        }
    }

    private void BoxColor( String flag) {
        int  r1,r2,c1,c2;
        cursor=db.rawQuery("SELECT row1,col1,row2,col2 FROM soal_mst WHERE id="+nSoal,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            r1=cursor.getInt(0);
            c1=cursor.getInt(1);
            r2=cursor.getInt(2);
            c2=cursor.getInt(3);

            for(int i= r1;i<=r2;i++) {
                for(int j = c1;j<=c2;j++) {
                    if(txt[i][j].getHint().toString().equals("b")) {
                        txt[i][j].setTextColor(getColor(R.color.black));
                        txt[i][j].setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.box_benar));
                    } else {
                        txt[i][j].setTextColor(getColor(R.color.black));
                        txt[i][j].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_white));
                    }
                    if(flag.equals("S")) {
                        txt[i][j].setText("");
                        txt[i][j].setHint("");
                    }
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
    }

    private void vSoal(int r, int c) {
        jawaban="";
        cursor=db.rawQuery("SELECT soal,jawab,row1,col1,row2,col2,jenis,nomor,fl FROM soal_mst WHERE row1="+r+" AND col1="+c+" AND jenis='"+jenis+"' AND id="+nSoal,null);
        cursor.moveToFirst();
        if(cursor.isAfterLast()) {
            cursor.close();
            if(jenis.equals("D")) {
                jenis="T";
            } else {
                jenis="D";
            }
            cursor=db.rawQuery("SELECT soal,jawab,row1,col1,row2,col2,jenis,nomor,fl FROM soal_mst WHERE row1="+r+" AND col1="+c+" AND jenis='"+jenis+"' AND id="+nSoal,null);
            cursor.moveToFirst();

        }

        if(!cursor.isAfterLast()){
            btnKanan.setVisibility(View.VISIBLE);
            btnKiri.setVisibility(View.VISIBLE);
            jnssoal=cursor.getString(6);
            nomor=cursor.getInt(7);
            tblJawab = cursor.getInt(8) == 0;
            row1=cursor.getInt(2);
            row2=cursor.getInt(4);
            col1=cursor.getInt(3);
            col2=cursor.getInt(5);
            txSoal.setText(cursor.getString(0));
            jawaban=(cursor.getString(1).trim().toUpperCase());
            if(jawaban.length() > 12) {jawaban=jawaban.substring(0,12);}
            minCol=col1;
            maxCol=col2;
            minRow=row1;
            maxRow=row2;
            if(jnssoal.equals("D")) {
                btnKiri.setImageResource(R.drawable.back32);
                btnKanan.setImageResource(R.drawable.next32);
            } else {
                btnKiri.setImageResource(R.drawable.naik_32);
                btnKanan.setImageResource(R.drawable.turun_32);
            }
            showMoveButton(tblJawab);

            for(int i = row1;i<=row2;i++) {
                for(int j = col1;j<=col2;j++) {
                   txt[i][j].setTextColor(getColor(R.color.black));
                   txt[i][j].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_aktif));
                }
            }
            for(int i = row1;i<=row2;i++) {
                for(int j = col1;j<=col2;j++) {
                    if(!txt[i][j].getHint().toString().equals("b")) {
                        txt[i][j].setTextColor(getColor(R.color.white));
                        txt[i][j].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_input));
                        row1=i;col1=j;
                        j=col2+1;i=row2+1;  //-->exit from Loop (For)
                    }
                }
            }
            cursor.close();
            if(jenis.equals("D")) {
                jenis="T";
            } else {
                jenis="D";
            }
            showKeypad();
        }
        cursor.close();

    }
    private void btnHurufClicked(int n) {
        if(!tblJawab) {return;}
        if(!txt[row1][col1].getHint().toString().equals("b")) {
            mediaPlayer.start();
            txt[row1][col1].setText(btnHuruf[n].getText());
            if(JawabanBenar()) {
                TandaiKotakJawaban();
                TandaiJawabanBenarkeDb();
                if(missionComplete()) {
                    selamat();
                } else {LompatkeSoalBerikut();}
            } else {PindahFokuskeKotakBerikut();}
        }
    }
    private void PindahFokuskeKotakBerikut() {
        MoveFocus();
        for(int i=1;i<13;i++) {
            if(txt[row1][col1].getHint().toString().equals("b")) {
                MoveFocus();
            }
        }
    }

    private void MoveFocus() {
        if(jnssoal.equals("D")) {
            if(col1<maxCol) {
                txt[row1][col1].setTextColor(getColor(R.color.black));
                txt[row1][col1].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_aktif));
                col1++;
                if(!txt[row1][col1].getHint().toString().equals("b")) {
                    txt[row1][col1].setTextColor(getColor(R.color.white));
                    txt[row1][col1].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_input));
                }
            } else {
                if(txt[maxRow][col1].getHint().toString().equals("b")) {
                    col1=maxCol-1;
                    txt[row1][col1].setTextColor(getColor(R.color.white));
                    txt[row1][col1].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_input));
                }
            }
        } else {
            if(row1<maxRow) {
                txt[row1][col1].setTextColor(getColor(R.color.black));
                txt[row1][col1].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_aktif));
                row1++;
                if(!txt[row1][col1].getHint().toString().equals("b")) {
                    txt[row1][col1].setTextColor(getColor(R.color.white));
                    txt[row1][col1].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_input));
                }
            } else {
                if(txt[maxRow][col1].getHint().toString().equals("b")) {
                    row1=maxRow-1;
                    txt[row1][col1].setTextColor(getColor(R.color.white));
                    txt[row1][col1].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_input));
                }
            }
        }

    }

    private void LompatkeSoalBerikut() {
        cursor=db.rawQuery("SELECT row1,col1,nomor FROM soal_mst WHERE fl=0 AND jenis='"+jenis+"' AND id="+nSoal+" ORDER BY nomor",null);
        cursor.moveToFirst();
        if(cursor.isAfterLast()) {
            cursor.close();
            cursor=db.rawQuery("SELECT row1,col1,nomor FROM soal_mst WHERE fl=0 AND jenis='"+jnssoal+"' AND id="+nSoal+" ORDER BY nomor",null);
            cursor.moveToFirst();
        }
        if(!cursor.isAfterLast()) {
            int r = cursor.getInt(0);
            int c = cursor.getInt(1);
            cursor.close();
            vSoal(r,c);
        }
    }
    private boolean JawabanBenar() {
        String hasil="";
        if(jnssoal.equals("D")) {
            for(int i=minCol;i<=maxCol;i++) {
                hasil=hasil+txt[minRow][i].getText().toString();
            }
        }else {
            for(int i=minRow;i<=maxRow;i++) {
                hasil=hasil+txt[i][minCol].getText().toString();
            }
        }
        return hasil.equals(jawaban);

    }
    private void TandaiKotakJawaban() {
        if(jnssoal.equals("D")) {
            for(int i=minCol;i<=maxCol;i++) {
                txt[minRow][i].setHint("b");
                txt[minRow][i].setTextColor(getColor(R.color.black));
                txt[minRow][i].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_benar));
            }
        }else {
            for(int i=minRow;i<=maxRow;i++) {
                txt[i][minCol].setHint("b");
                txt[i][minCol].setTextColor(getColor(R.color.black));
                txt[i][minCol].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_benar));
            }
        }
    }

    private void TandaiJawabanBenarkeDb() {
        cursor=db.rawQuery("SELECT jawab,row1,col1,row2,col2,jenis,nomor FROM soal_mst WHERE id="+nSoal+" AND fl=0",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            int r1 = cursor.getInt(1);
            int r2 = cursor.getInt(3);
            int c1 = cursor.getInt(2);
            int c2 = cursor.getInt(4);
            String js = cursor.getString(5).trim().toUpperCase();
            String hasil="";
            int nn = cursor.getInt(6);
            if(js.equals("D")) {
                for(int i=c1;i<=c2;i++) {
                    hasil=hasil+txt[r1][i].getText().toString();
                }
            }else {
                for(int i=r1;i<=r2;i++) {
                    hasil=hasil+txt[i][c1].getText().toString();
                }
            }
            if(cursor.getString(0).trim().toUpperCase().equals(hasil)) {
                db.execSQL("Update soal_mst SET fl=1 WHERE id="+nSoal+" AND nomor="+nn+" AND jenis='"+js+"'");
            }
            cursor.moveToNext();
        }
        cursor.close();
    }

    private boolean missionComplete() {
        cursor=db.rawQuery("SELECT COUNT(*) FROM soal_mst WHERE id="+nSoal+" AND fl=0",null);
        cursor.moveToFirst();
        int r=cursor.getInt(0);
        cursor.close();
        btnKiri.setVisibility(View.INVISIBLE);
        btnKanan.setVisibility(View.INVISIBLE);
        selesai = r == 0;
        return selesai;
    }

    private void showKeypad () {
        StringBuilder ss= new StringBuilder();
        for(int j=0;j<12;j++) {
            btnHuruf[j].setHint("");
        }
        for(int i=0;i<jawaban.length();i++) {
            boolean ada = false;
            for(int j=0;j<12;j++) {
                if(jawaban.substring(i,i+1).equals(btnHuruf[j].getText().toString())) {
                    btnHuruf[j].setHint("*");
                    ada=true;
                    break;
                }
            }
            if(!ada) {ss.append(jawaban.charAt(i));}
        }

        ss=ss.reverse();
        String ns = String.valueOf(ss);
        for(int i=0;i<ss.length();i++) {  //remove duplicate character
            String s = ss.substring(i,i+1);
            int n=0;
            for(int j=0;j<ss.length();j++) {
                if(s.equals(ss.substring(j,j+1))) {
                    n++;
                }
            }
            if(n>1) {
                ns = ns.replace(s,"");
                ns=ns+s;
            }
        }
        for(int i=0;i<ns.length();i++) {
            for(int j=0;j<12;j++) {
                if(btnHuruf[j].getHint().equals("")) {
                    btnHuruf[j].setText(ns.substring(i,i+1));
                    btnHuruf[j].setHint("*");
                    break;
                }
            }
        }
    }

    private void goWarna() {
        switch (warna) {
            case 0:findViewById(R.id.tts_bg).setBackgroundColor(getColor(R.color.colorAccent));break;
            case 1:findViewById(R.id.tts_bg).setBackgroundColor(getColor(R.color.green));break;
            case 2:findViewById(R.id.tts_bg).setBackgroundColor(getColor(R.color.ungu));break;
            case 3:findViewById(R.id.tts_bg).setBackgroundColor(getColor(R.color.maroon));break;
            case 4:findViewById(R.id.tts_bg).setBackgroundColor(getColor(R.color.colorPrimary));break;
        }
    }

    private void goTTS() {
        selesai=false;
        ClearBox();
        BoxColor("S");
        mediaPlayer = MediaPlayer.create(this,R.raw.btnklik);
        if(!soalCompleted()) {  //Jika soal TTS belum terjawab semua
            cursor=db.rawQuery("SELECT row1,col1 FROM soal_mst WHERE id="+nSoal+" AND fl=0 LIMIT 1",null);
            cursor.moveToFirst();
            int r = cursor.getInt(0);
            int c = cursor.getInt(1);
            showMoveButton(true);
            cursor.close();
            vSoal(r,c);
        } else {
            showMoveButton(false);
        }
    }

    private Boolean soalCompleted () {
        boolean main;
        cursor=db.rawQuery("SELECT COUNT(*) FROM soal_mst WHERE id="+nSoal,null);
        cursor.moveToFirst();
        int t1 = cursor.getInt(0);
        cursor.close();

        int n=0;
        cursor=db.rawQuery("SELECT soal,jawab,row1,col1,row2,col2,jenis,nomor,fl FROM soal_mst WHERE fl=1 AND id="+nSoal,null);
        int t2 = cursor.getCount();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String s = cursor.getString(1).trim().toUpperCase();
            for(int i = cursor.getInt(2);i<=cursor.getInt(4);i++) {
                for(int j = cursor.getInt(3);j<=cursor.getInt(5);j++) {
                    txt[i][j].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.box_benar));
                    txt[i][j].setHint("b");
                    txt[i][j].setText(s.substring(n,n+1));
                    n++;
                }
            }
            n=0;
            cursor.moveToNext();
        }
        cursor.close();
        main = t2==t1;
        selesai=main;
        if(main) {txSoal.setText("ANDA SUDAH BERHASIL MENGERJAKAN TTS INI");}
        return main;
    }

    private void showMoveButton(boolean fl) {
        if(fl) {
            findViewById(R.id.btn_kanan).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_kiri).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.btn_kanan).setVisibility(View.INVISIBLE);
            findViewById(R.id.btn_kiri).setVisibility(View.INVISIBLE);
        }
        for(int i=0;i<12;i++) {
            btnHuruf[i].setEnabled(fl);
        }

    }

    private void selamat() {
        mediaPlayer = MediaPlayer.create(this,R.raw.good);
        mediaPlayer.start();

        Intent intent = new Intent(tts_play.this, good.class);
        congrat.launch(intent);

    }

    private void TTSid(){
        List<String> spinArray = new ArrayList<String>();
        cursor=db.rawQuery("SELECT DISTINCT id FROM soal_mst ORDER BY id",null);
        int i=0;
        while (cursor.moveToNext()) {
            spinArray.add(cursor.getString(0));
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_list,spinArray);
        adapter.setDropDownViewResource(R.layout.spinner_list);
        spinTTSNo.setAdapter(adapter);
        cursor.close();

    }
    private void Keluar() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Tutup aplikasi TTS ?");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FileHelper.saveToFile(String.valueOf(warna),"warna.txt");
                        tts_play.this.finish();

                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void goDownload() {
        sukses=false;
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {  //pre execute
                        pDialog.showProgDialog();
                    }
                });

                try {
                    int count;
                    URL url = new URL("https://drive.google.com/uc?export=download&id=1kphjx5puj2tw9acy8LZw91ILjHv9Iodx");
                    URLConnection connection = url.openConnection();
                    connection.connect();
                    InputStream input = new BufferedInputStream(url.openStream(), 8192);
                    OutputStream output = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        output = Files.newOutputStream(Paths.get("/data/data/dennis.com.ttsku/databases/tts.txt"));
                    }

                    byte[] data = new byte[1024];
                    while ((count = input.read(data)) != -1) {
                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    input.close();
                    sukses=true;

                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!sukses) {
                            Toast.makeText(getApplicationContext(),"Download gagal. File update tidak tersedia atau koneksi internet anda bermasalah",Toast.LENGTH_LONG).show(); }
                        else {
                            String w=FileHelper.getData(tts_play.this);
                            Toast.makeText(getApplicationContext(),w,Toast.LENGTH_LONG).show();
                            if(w.charAt(0) == '-') {TTSid();}
                        }
                        pDialog.closeProgDialog();
                    }
                });
            }
        });


    }


}