package com.chebao.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

/**
 * 创建日期：2018/6/5 on 17:20
 * 描述:
 * 作者:jackson Administrator
 */
public class QRCodeUtil  {
    //单例模式：懒汉模式
    private static QRCodeUtil INSTANCE = null;

    private QRCodeUtil(){

    }

    public static QRCodeUtil getInstance(){

        if (INSTANCE == null){

            INSTANCE = new QRCodeUtil();
        }

        return INSTANCE;
    }

    /**
     *String transactionString //要生成二维码的字符串
     *int QRCode_SIZE //二维码的大小[生成二维码是一个正方形的二维码，长宽同一]
     */
    public Bitmap encodeTransactionString(String transactionString, int QRCode_SIZE){

        boolean transactionString_Status = transactionString.isEmpty();

        if(!transactionString_Status){

            try {

                Hashtable<EncodeHintType,String> hints = new Hashtable<>();
                hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
                BitMatrix bitMatrix = new QRCodeWriter().encode(transactionString, BarcodeFormat.QR_CODE,QRCode_SIZE,QRCode_SIZE,hints);
                int[] pixels = new int[QRCode_SIZE * QRCode_SIZE];

                for (int y=0;y<QRCode_SIZE;y++){
                    for (int x=0;x<QRCode_SIZE;x++){

                        if (bitMatrix.get(x,y)){
                            pixels[y*QRCode_SIZE+x] = 0xff000000;
                        }else {
                            pixels[y*QRCode_SIZE+x] = Color.WHITE;
                        }
                    }
                }

                Bitmap bitmap = Bitmap.createBitmap(QRCode_SIZE,QRCode_SIZE, Bitmap.Config.ARGB_8888);
                bitmap.setPixels(pixels,0,QRCode_SIZE,0,0,QRCode_SIZE,QRCode_SIZE);
                return bitmap;

            }catch (WriterException e){

                e.printStackTrace();
                return null;
            }

        }else {

            return null;
        }

    }


}
