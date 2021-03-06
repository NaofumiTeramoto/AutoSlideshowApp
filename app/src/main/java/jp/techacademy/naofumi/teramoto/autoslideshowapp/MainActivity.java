package jp.techacademy.naofumi.teramoto.autoslideshowapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Button;
import android.view.View;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSIONS_REQUEST_CODE = 100;

    int NId=0;
    int iNId=1;
    long NNId[]=new long[500];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);


        // Android 6.0以降の場合
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // パーミッションの許可状態を確認する
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // 許可されている
                getContentsInfo();
            } else {
                // 許可されていないので許可ダイアログを表示する
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
            }
            // Android 5系以下の場合
        } else {
            getContentsInfo();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {
            Log.d("UI_PARTS", "ボタンをタップしました");}
//            long id = NNID[iNId];
            Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
//            ImageView imageVIew = (ImageView) findViewById(R.id.imageView);
//            imageVIew.setImageURI(imageUri);
        else if (v.getId() == R.id.button2) {
            cursor.moveToNext();
        }
        else if (v.getId() == R.id.button3) {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContentsInfo();
                }
                break;
            default:
                break;
        }
    }

    private void getContentsInfo() {

        // 画像の情報を取得する
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // データの種類
                null, // 項目(null = 全項目)
                null, // フィルタ条件(null = フィルタなし)
                null, // フィルタ用パラメータ
                null // ソート (null ソートなし)
        );


        if (cursor.moveToFirst()) {
            do {

                // indexからIDを取得し、そのIDから画像のURIを取得する
                int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                Long id = cursor.getLong(fieldIndex);
                Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                NId++;
                NNId[NId]=id;

                Log.d("ANDROID", "URI : " + imageUri.toString());
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

}
