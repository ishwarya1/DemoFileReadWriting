package sg.edu.rp.soi.demofilereadwriting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
Button btnWrite, btnRead;
    String folderLocation;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRead = findViewById(R.id.btnread);
        btnWrite = findViewById(R.id.btnwrite);
        tv = findViewById(R.id.tv);
        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                   new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }
        folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFolder";

        File folder = new File(folderLocation);
        if (folder.exists() == false){
            boolean result = folder.mkdir();
            if (result == true){
                Log.d("File Read/Write", "Folder created");
            }
        }
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code for file writing
                try{
                    folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFolder";
                    File targetFile = new File(folderLocation,"data.txt");
                    FileWriter writer = new FileWriter(targetFile,true);
                    writer.write("Hello World" + "\n");
                    writer.flush();
                    writer.close();
                    Toast.makeText(getApplicationContext(),"Successfully write",Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Failed to do so",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //Code for file reading
                    String folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Folder";
                    File targetFile = new File(folderLocation, "data.txt");
                    if (targetFile.exists() == true) {
                        String data = "";
                        try {
                            FileReader reader = new FileReader(targetFile);
                            BufferedReader br = new BufferedReader(reader);
                            String line = br.readLine();
                            while (line != null) {
                                data += line + "\n";
                                line = br.readLine();
                            }
                    br.close();
                    reader.close();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Failed to read!", Toast.LENGTH_LONG).show();e.printStackTrace();
                    e.printStackTrace();
                }
                    Log.d("content", data);
                    tv.setText(data);
                }
            }
        });

    }
}
