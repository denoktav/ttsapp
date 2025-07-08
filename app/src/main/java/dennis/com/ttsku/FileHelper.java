package dennis.com.ttsku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileHelper {
    //final static String fileName = "tts_history.txt";
    final static String path = "/data/data/dennis.com.ttsku/databases/" ;
    final static String TAG = FileHelper.class.getName();

    public static  String ReadFile( Context context,String fileName){
        String line = null;
        try {
            File file = new File(path+ fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fileInputStream = new FileInputStream (new File(path + fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line);
            }
            fileInputStream.close();
            line = stringBuilder.toString();
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }
        catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return line;
    }

    public static boolean saveToFile(String data,String fileName){
        try {
            File file = new File(path+ fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file,false);
            fileOutputStream.write((data).getBytes());
            return true;
        }  catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }  catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return  false;
    }

    public static  String getData( Context context){
        String str = null;
        boolean fupdate=false;
        String [] data;
        data = new String[10];
        try {
            FileInputStream fileInputStream = new FileInputStream (new File(path + "tts.txt"));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            File file = new File(path+ "tts.txt");
            Cursor cursor;
            SQLiteDatabase db;
            DatabaseOpenHelper dbHelper;
            dbHelper = new DatabaseOpenHelper(context,"ttsdb.db");
            db = dbHelper.getWritableDatabase();
            String s;
            while ((str = bufferedReader.readLine()) != null)
            {
                s = str;
                for(int i=0;i<10;i++) {
                    if(i==9) {
                        data[i]=s;
                    } else {
                        int n = s.indexOf(",");
                        data[i]=s.substring(0,n);
                        s = s.substring(n+1);
                    }
                }
                cursor=db.rawQuery("SELECT COUNT(*) FROM soal_mst WHERE id="+data[0]+" AND nomor="+data[1]+" AND jenis='"+data[8]+"'",null);
                cursor.moveToFirst();
                if(cursor.getInt(0) == 0) {  //-->insert jika data belum ada dalam Db
                    cursor.close();
                    ContentValues cv = new ContentValues();
                    cv.put("id",data[0]);
                    cv.put("nomor",data[1]);
                    cv.put("soal",data[2]);
                    cv.put("jawab",data[3]);
                    cv.put("row1",data[4]);
                    cv.put("col1",data[5]);
                    cv.put("row2",data[6]);
                    cv.put("col2",data[7]);
                    cv.put("jenis",data[8]);
                    cv.put("fl",data[9]);
                    db.insert("soal_mst",null,cv);
                    fupdate=true;
                } else {cursor.close();}
            }
            fileInputStream.close();
            bufferedReader.close();
            db.close();
            file.delete();
        }
        catch(IOException ex) {
            return ex.getMessage();
        }
        if(fupdate) {
            return "-Soal TTS berhasil diupdate-";
        } else {return "Update soal TTS terbaru belum tersedia";}
    }

}