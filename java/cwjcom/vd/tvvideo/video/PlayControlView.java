package cwjcom.vd.tvvideo.video;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import cwjcom.vd.tvvideo.R;

/**
 * Created by ${陈文杰} on 2018-07-06.
 * Email：1181620038@qq.com
 * 描述：播放控制类
 */
public class PlayControlView extends RelativeLayout implements View.OnClickListener{
    private ImageView backIv,fastIv,stopIv,speedIv,videoSizeIv,cacheImage;
    private TextView videoNameTv,playTimeTv,proText;
    private SeekBar volBar,videoBar;
    private TvVideo videoView;
    private LinearLayout videoLL;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private String duration;//视频时长
    private String durationPosion;//当前播放时间
    private int videoCurrent;//手指快进或者后退的位置。//根据这个位置来设置
    private int time;
    private boolean isPase=true;  //当前的播放状态。//暂停，还是正在播放 true
        int  proges=1;
    public PlayControlView(Context context) {
        super(context);

    }
    long  dura;
    private static long mPosition;//快进 后退当前播放时间
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                     dura=videoView.getCurrentPosition();//当前播放时间
                    mPosition= dura;
                            playTimeTv.setText(toTime(dura)+"/"+duration);
                            videoBar.setMax(videoView.getDuration()/100);
                            videoBar.setProgress(videoView.getCurrentPosition()/100);

                    if (isPase){
                        handler.sendEmptyMessageDelayed(
                                1,
                                1 * 1000);
                    }else{

                    }
                    break;
                    case 2 :
                    break;
                    case 3 :
                        setVisibilityControl(true);
                    break;

            }

        }
    };
    public PlayControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.video_control,this,true);
        initView();
    }
    //初始化
    public void initView(){
        backIv=findViewById(R.id.back_iv);//ic_back
        fastIv=findViewById(R.id.fast_iv);//快退
        speedIv=findViewById(R.id.speed_iv);//快进
        stopIv=findViewById(R.id.stop_iv);//暂停开始
        videoSizeIv=findViewById(R.id.video_size_iv);//全屏缩小
        cacheImage=findViewById(R.id.cache_iv);//播放前的图片
        //-------
        videoNameTv=findViewById(R.id.name_tv);//名称
        playTimeTv=findViewById(R.id.playtime_tv);//播放总时长
        proText=findViewById(R.id.pro_tv);//进度条声音
        //-------
        volBar=findViewById(R.id.vol_bar);//声音
        videoBar=findViewById(R.id.pre_bar);//播放进度
        //-------
        progressBar=findViewById(R.id.progress);//进度条。
        //--------
        videoView=findViewById(R.id.video);//视频
        //------悬浮层
        relativeLayout=findViewById(R.id.control_rl);
        videoLL=findViewById(R.id.video_ll);

        backIv.setOnClickListener(this);
        fastIv.setOnClickListener(this);
        speedIv.setOnClickListener(this);
        stopIv.setOnClickListener(this);
        videoSizeIv.setOnClickListener(this);
//        cacheImage.setOnClickListener(this);
        initVideo();

    }

    /**
     * 播放视频前的准备。
     */
    public void initVideo(){
        relativeLayout.setVisibility(GONE);
        videoView.setFocusable(true);//VideoView 抢占焦点问题。
    }

    /**
     * 设置进度条是否显示和隐藏 及文字
     */
    public void setProgressBarText(int visb,String text){
        if (visb==1){//显示
            progressBar.setVisibility(VISIBLE);
            proText.setVisibility(VISIBLE);
            proText.setText(text+"");
        }else{
            progressBar.setVisibility(GONE);
            proText.setText(GONE);
            proText.setText(text+"");
        }


    }

    /**
     *  隐藏 ConreolView 控制层
     * @param isGone true 就隐藏 反之。
     */
    public void setVisibilityControl(boolean isGone){
        if (isGone){
            relativeLayout.setVisibility(GONE);
            videoView.setFocusable(true);//VideoView 抢占焦点问题。

        }else{
            videoView.setFocusable(false);//控制层显示的时候就不控制。
            relativeLayout.setVisibility(VISIBLE);

        }
    }

    /**
     * 设置 视频名称
     * @param name
     */
    public void setVideoName(String name){
        videoNameTv.setText(name);
    }


    //ic_stop 还是播放
    public void pauseStart(){
        if (videoView.canPause()){//是否可以暂停
            if (videoView.isPlaying()){//是否在播放
                videoView.pause();
                isPase=false;
                stopIv.setImageResource(R.drawable.ic_start);
            }else{
                stopIv.setImageResource(R.drawable.ic_stop);
                //播放
                isPase=true;
                 start();
                handler.sendEmptyMessage(1);

            }
        }else{//不可暂停，直播。

        }

    }
    long mins=5*1000;
    //快退
    public void fastVideo(){
        if (videoView.canSeekBackward()){//是否可以后退
            videoView.seekTo((int) (mPosition-mins));//5秒
            mPosition=mPosition-mins;
            start();
        }else{

        }
    }
    //快进
    public void speedVideo(){
        if (videoView.canSeekForward()){//是否可以快进
            videoView.seekTo((int) (mPosition+mins));//5秒
            mPosition=mPosition+mins;
            start();

        }else{

        }
    }



    /**
     *  设置播放前的图片
     * @param imagePath
     */
    public void setImage(String imagePath){
//        welcome.setImageResource(R.drawable.welcome);

    }
    public void setImage(int imagePath){
//        Glide.with(activity)
//                .load(imagePath)
//                .into(cacheImage);
    }

    /**
     * 本地文件
     * @param path
     */
    public void setVideoURI(String path){
        videoView.setVideoURI(Uri.parse(path));
    }

    /**
     * 播放启动
     */

    public void start(){
        videoView.start();
        cacheImage.setVisibility(VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            onMontorVideo();
        }

    }

    /**
     * 网络直播地址
     * @param paths
     */
    public void setVideoPath(String paths){
        videoView.setVideoPath(paths);

    }
    /**
     * video 事件监听
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void onMontorVideo(){
        /*播放完成*/
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
            //替换图片
            }
        });
        /*播放错误*/
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                setProgressBarText(1,"视频播放错误");
                return false;
            }
        });
        /*播放前准备*/
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                cacheImage.setVisibility(GONE);
                setProgressBarText(2,"视频加载中");
                handler.sendEmptyMessage(1);//发送handler;

            }
        });
        /*播放中发生事件，*/
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        //开始播放监听
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                duration= toTime(videoView.getDuration());
                durationPosion=toTime(videoView.getCurrentPosition());
                handler.sendEmptyMessage(1);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_iv://ic_back
                break;
            case R.id.fast_iv://快退
                fastVideo();
                break;
            case R.id.speed_iv://快进
                speedVideo();
                break;
            case R.id.stop_iv://暂停开始
                pauseStart();
                break;
            case R.id.video_size_iv://全屏缩小
                break;
            case R.id.cache_iv://播放前的图片
                break;
        }
    }

    //关闭hander
    public void closeHandler(){
        handler.removeCallbacksAndMessages(null);
    }


    //使用键盘或者遥控器用dispatchKeyEvent
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getAction()){
            case KeyEvent.ACTION_DOWN:{//检测有按键
                setVisibilityControl(false);//显示
                stopTimer();

            }
            case KeyEvent.ACTION_UP:{//检测无操作
                timerTask();
                break;

            }
        }
        return  super.dispatchKeyEvent(event);

    }

    //使用触摸用onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                setVisibilityControl(false);
                stopTimer();
            }
            case MotionEvent.ACTION_UP:{
                timerTask();
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 未操作后5秒隐藏
     */
    Timer timer;
    TimerTask task;
    public void timerTask(){
         timer = new Timer();
          task = new TimerTask() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
                handler.sendEmptyMessage(3);
            }
        };
          if (timer!=null){
              timer.schedule(task, times);//3秒后执行TimeTask的run方法
          }
    }
    int times=20000;

    public void stopTimer(){
        if(timer!=null){
            timer.cancel();
            timer = null;
        }

        if (task!=null){
            task.cancel();
            task=null;

        }


    }
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        // TODO Auto-generated method stub
//        Resources resources = this.getResources();
//        DisplayMetrics dm = resources.getDisplayMetrics();
//        float density = dm.density;
//        int dwidth = dm.widthPixels;
//        int dheight = dm.heightPixels;
//
//        int width = getDefaultSize(dwidth, widthMeasureSpec);
//        int height = getDefaultSize(dheight, heightMeasureSpec);
//        setMeasuredDimension(width, height);
//    }
    public String toTime(long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(ms);
        System.out.println(hms);
        Log.e("toTime===",""+hms);
        Log.e("dr===",""+dura);
        return hms;
    }



}
