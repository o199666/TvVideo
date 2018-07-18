package cwjcom.vd.tvvideo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class MainActivity extends Activity implements View.OnClickListener {
    private VideoView videoView;
    private Button mp4,avi,mkv,rmvb,web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView=findViewById(R.id.vv);
        mp4=findViewById(R.id.start_btn1);
        mkv=findViewById(R.id.start_btn2);
        avi=findViewById(R.id.start_btn4);
        rmvb=findViewById(R.id.start_btn3);
        web=findViewById(R.id.start_btn5);
        mp4.setOnClickListener(this);
        mkv.setOnClickListener(this);
        avi.setOnClickListener(this);
        rmvb.setOnClickListener(this);
        web.setOnClickListener(this);
        //  videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.mov1));
        //                videoView.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_btn1:
                Intent intent2=new Intent(this,WebJInYiTong.class);
                startActivity(intent2);

                break;
            case R.id.start_btn2:
                videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.mk3));
                videoView.start();

                break;
            case R.id.start_btn3:
                videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.rmvb11));
                videoView.start();

                break;
            case R.id.start_btn4:
                videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.avi1));
                videoView.start();
                break;
            case R.id.start_btn5:
                Intent intent=new Intent(this,WebVideoActivity.class);
                startActivity(intent);
                break;

        }
    }
}
