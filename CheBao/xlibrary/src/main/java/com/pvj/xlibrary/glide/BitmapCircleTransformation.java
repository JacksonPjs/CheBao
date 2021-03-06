package com.pvj.xlibrary.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 *  圆形头像等图片
 */
public class BitmapCircleTransformation extends BitmapTransformation {
    public BitmapCircleTransformation(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool , toTransform);
    }


    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        //创建一个正方形的空白bitmap对象，将来在该Bitmap上绘制图案
        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_4444);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_4444);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        // 选择原图的中心点，裁切出一个正方形区域，将来绘制在空白的Bitmap上
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }

}
