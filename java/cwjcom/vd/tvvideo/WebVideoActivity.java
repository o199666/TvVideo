package cwjcom.vd.tvvideo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cwjcom.vd.tvvideo.video.PlayControlView;

/**
 * Created by ${陈文杰} on 2018/7/5 0005.
 * Email：1181620038@qq.com
 * 描述：http://api.etlbus.com/demo/vod/
 */
public class WebVideoActivity extends AppCompatActivity{
    private PlayControlView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webvideo);
        webView=findViewById(R.id.control_view);
        webView.setVideoName("倚天屠龙剑");
        webView.setVideoURI("android.resource://"+getPackageName()+"/"+R.raw.mk3);
        webView.start();
    }
}
