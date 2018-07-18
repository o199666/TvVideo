package cwjcom.vd.tvvideo.video;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class TvVideo extends VideoView{
    public TvVideo(Context context) {
        super(context);
    }
    public TvVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
