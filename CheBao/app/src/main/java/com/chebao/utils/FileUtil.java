package com.chebao.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Log;

import com.pvj.xlibrary.log.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtil {
    private final static String TAG = "FileUtil";

    /**
     * 将Bitmap 图片保存到本地路径，并返回路径
     *
     * @param c
     * @param fileName 文件名称
     * @param bitmap   图片
     * @return
     */
    public static String saveFile(Context c, String fileName, Bitmap bitmap) {
        return saveFile(c, "", fileName, bitmap);
    }

    public static String saveFile(Context c, String filePath, String fileName, Bitmap bitmap) {
        byte[] bytes = bitmapToBytes(bitmap);
        return saveFile(c, filePath, fileName, bytes);
    }

    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static String saveFile(Context c, String filePath, String fileName, byte[] bytes) {
        String fileFullName = "";
        FileOutputStream fos = null;
        String dateFolder = new SimpleDateFormat("yyyyMMdd", Locale.CHINA)
                .format(new Date());
        try {
            String suffix = "";
            if (filePath == null || filePath.trim().length() == 0) {
                filePath = Environment.getExternalStorageDirectory() + "/Snoppa/" + dateFolder + "/";
            }
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File fullFile = new File(filePath, fileName + suffix);
            fileFullName = fullFile.getPath();
            fos = new FileOutputStream(new File(filePath, fileName + suffix));
            fos.write(bytes);
        } catch (Exception e) {
            fileFullName = "";
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    fileFullName = "";
                }
            }
        }
        return fileFullName;
    }

    /**
     * 获取默认的snoppa路径
     *
     * @return snoppaPath = /storage/emulated/0/Snoppa
     */
    public static File getSnoppaFile() {
        final String snoppaPath = Environment.getExternalStorageDirectory().getPath() + "/Snoppa";
        File mSnoppaDir = new File(snoppaPath);

        if (!mSnoppaDir.exists()) {
            mSnoppaDir.mkdirs();
        }
        return mSnoppaDir;
    }

    /**
     * 返回保存的文件夹名
     *
     * @return
     */
    public static String getVideoFilePath() {
        File destDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Snoppa/");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Snoppa/";
        return path;
    }


    /**
     * 删除单个文件
     *
     * @param filePath 被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static File createFile(String filePath, boolean isDelete) throws IOException {
        File file = new File(filePath);
        if (file.exists() && isDelete) {
            file.delete();
        }
        return createFile(file);

    }

    public static File createFile(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 获取文件，并将文件以指定大小分包
     *
     * @param filePath
     * @param carLength
     * @return
     */
    public static ArrayList<byte[]> readInputStream(String filePath, int carLength) throws IOException {
        ArrayList<byte[]> dataPackageList = new ArrayList<byte[]>();
        FileInputStream inputStream = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return null;
            }

            inputStream = new FileInputStream(filePath);
            InputStreamReader ir = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(ir);
            FileReader fr = new FileReader(filePath);

            byte[] buffet = new byte[carLength];
            int length = -1;
            int available = inputStream.available();
            int remainder = available % carLength;//获取最后一个包的长度
            int times = 0;
            int totalTimes = (available / carLength) + 1;//总共的包数
            while ((length = inputStream.read(buffet)) != -1) {

                times++;
                if (totalTimes == times) {
                    //最后一个包
                    byte[] tempByte = new byte[remainder];
                    //第一个是要复制的数组，第二个是从要复制的数组的第几个开始，第三个是复制到那，
                    // 四个是复制到的数组第几个开始，最后一个是复制长度
                    System.arraycopy(buffet, 0, tempByte, 0, tempByte.length);
                    dataPackageList.add(tempByte);

                } else {
                    byte[] tempByte = new byte[carLength];
                    //第一个是要复制的数组，第二个是从要复制的数组的第几个开始，第三个是复制到那，
                    // 四个是复制到的数组第几个开始，最后一个是复制长度
                    System.arraycopy(buffet, 0, tempByte, 0, carLength);
                    dataPackageList.add(tempByte);

                }
            }
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dataPackageList;
    }


    /**
     * 生成数据 相当于拼接成一列火车
     *
     * @param original
     * @return
     */
    public static ArrayList<ArrayList<byte[]>> processingFirmwareUpdata(ArrayList<byte[]> original) {
        if (original.size() < 0) {
            return null;
        }

        //成型的数据，相当于一列火车
        ArrayList<ArrayList<byte[]>> trainList = new ArrayList<ArrayList<byte[]>>();


        for (int i = 0; i < original.size(); i++) {

            byte[] by1 = new byte[20];
            byte[] by2 = new byte[20];
            byte[] by3 = new byte[20];
            byte[] by4 = new byte[20];
            byte[] by5 = new byte[20];

            //车厢%10
            int remainder = i % 10;
            Logger.d(TAG, "余数 = " + remainder);
            //数据等于95 而且不是最后一个包
            if (original.get(i).length == 95 && i != original.size() - 2) {
                //一个大包  相当于列车车厢
                ArrayList<byte[]> coachList = new ArrayList<>();

                //处理第一个包
                by1[0] = 0x55;
                by1[1] = 100;
                by1[2] = (byte) remainder;
                //一个长度为95的包，将其切割
                //第一个是要复制的数组，第二个是从要复制的数组的第几个开始，第三个是复制到那，
                // 四个是复制到的数组第几个开始，最后一个是复制长度
                System.arraycopy(original.get(i), 0, by1, 3, 17);
                coachList.add(by1);
                System.arraycopy(original.get(i), 17, by2, 0, 20);
                coachList.add(by2);
                System.arraycopy(original.get(i), 37, by3, 0, 20);
                coachList.add(by3);
                System.arraycopy(original.get(i), 57, by4, 0, 20);
                coachList.add(by4);
                System.arraycopy(original.get(i), 77, by5, 0, 18);
//			by5[18] = createCRC(original.get(i),remainder);//crc校验
                by5[19] = 0x5a;//结束位
                coachList.add(by5);
                //将数据填完，相当于完成车厢创建，拼接到火车上；
                trainList.add(coachList);

            }

            if (i == (original.size() - 1)) {
                //最后一个列车车厢
                ArrayList<byte[]> lastCoachList = new ArrayList<>();
                //如果字节数没有95的
                //车厢长度
                int lastCoachLength = original.get(i).length;
                //是否超过十五个人
                if (lastCoachLength - 15 > 0) {
                    //第一个包分十五位客人，说明要用两个以上的列
                    //首先处理第一列里面的人
                    byte[] lastBy1 = new byte[20];

                    //处理第一个包
                    lastBy1[0] = 0x55;
                    lastBy1[1] = (byte) (lastCoachLength + 5);
                    lastBy1[2] = (byte) remainder;
                    System.arraycopy(original.get(i), 0, by1, 3, 17);
                    lastCoachList.add(by1);
                    int coachTail = (lastCoachLength - 15) % 20;//火车尾
                    //减去第一个列 15 和其他列的20，如果有剩余的
                    if (coachTail > 0 && coachTail < 18) {
                        //减去第一列后，剩余的列数,如果大于0，那么在剩余的数里面加上火车尾
                        int listNum = (lastCoachLength - 15) / 20;
                        for (int j = 0; j < listNum; j++) {
                            //将这些列填满
                            byte[] list = new byte[20];
                            System.arraycopy(original.get(i), 17 + (20 * i), list, 0, 20);
                            lastCoachList.add(list);
                        }
                        //创建最后一节车厢的最后一列
                        byte[] listLast = new byte[coachTail + 2];
                        System.arraycopy(original.get(i), 17 + (20 * listNum), listLast, 0, coachTail);
//                    listLast[listLast.length-2] = createCRC(original.get(i),remainder);//crc位
                        listLast[listLast.length - 1] = 0x5a;//结束位
                        lastCoachList.add(listLast);
                    } else if ((lastCoachLength - 15) % 20 == 18) {
                        //如果剩余的最后一列等于18，那么在最后一排加上火车尾
                        int listNum = (lastCoachLength - 15) / 20;
                        for (int j = 0; j < listNum - 1; j++) {
                            //将这些列填满
                            byte[] list = new byte[20];
                            System.arraycopy(original.get(i), 17 + (20 * i), list, 0, 20);
                            lastCoachList.add(list);
                        }
                        //创建最后一节车厢的最后一列
                        byte[] listLast = new byte[20];
                        System.arraycopy(original.get(i), 17 + (20 * listNum), listLast, 0, coachTail);
//                    listLast[listLast.length-2] = createCRC(original.get(i),remainder);//crc位
                        listLast[listLast.length - 1] = 0x5a;//结束位
                        lastCoachList.add(listLast);
                    }

                } else {
                    //小于十五，那么就可以用一个列装完
                    //创建最后一节车厢的最后一列
                    byte[] listLast = new byte[lastCoachLength + 5];
                    //处理第一个包
                    listLast[0] = 0x55;
                    listLast[1] = (byte) (lastCoachLength + 5);
                    listLast[2] = (byte) remainder;
                    System.arraycopy(original.get(i), 0, listLast, 3, lastCoachLength);
//                listLast[listLast.length-2] = createCRC(original.get(i),remainder);//crc位
                    listLast[listLast.length - 1] = 0x5a;//结束位
                    lastCoachList.add(listLast);

                }
                trainList.add(lastCoachList);

            }
        }
        return trainList;
    }


    /**
     * @param data      固件 的数据
     * @param remainder 包数%10 的余数
     * @return
     */
    public static byte createCRC(byte[] data, int remainder) {
        /**
         *   u8 FwCrcCaculate(u8 *ptr, u16 len) {
         u8 i = 0;
         u8 crc = 0xff;

         while (len--) {
         crc ^= *ptr++;
         for (i = 8; i > 0; i--) {
         if (crc & 0x80) {
         crc = (crc << 1) ^ 0x4D;
         } else {
         crc = (crc << 1);
         }
         }
         }
         return (crc);
         }
         */
        byte[] tempByte = new byte[data.length + 2];//创建一个临时数组 data长度加上长度位和包余数位
        int coachLength = data.length + 5;//车厢的长度等于data加上5
        if (data.length == 0) {
            Logger.d(TAG, "=====================");
        }
        tempByte[0] = (byte) coachLength;
        tempByte[1] = (byte) remainder;
        System.arraycopy(data, 0, tempByte, 2, data.length);

        byte crc = (byte) 0xff;

        for (byte by : tempByte) {
            crc ^= by;
            for (int i = 8; i > 0; i--) {
                if ((crc & 0x80) != 0) {
                    crc = (byte) ((crc << 1) ^ 0x4D);

                } else {
                    crc = (byte) (crc << 1);
                }
            }
        }
        Logger.d(TAG, "crc = " + crc);
        return crc;
    }

    public static List<String> makeMediaFileList() {
        return Arrays.asList(getSnoppaFile().list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".mp4")) {
//                    Logger.i(TAG, "makeMediaFileList=" + filename);
                    return true;
                }
                return false;
            }
        }));
    }

    public static boolean isMedia(String url) {
        String[] suffixes = url.split("\\.");
        if (suffixes[suffixes.length - 1].equals("mp4")) {
            return true;
        }

        return false;
    }

    public static void Unzip(String zipFile, String targetDir) {
        int BUFFER = 4096; //这里缓冲区我们使用4KB，
        String strEntry; //保存每个zip的条目名称

        try {
            BufferedOutputStream dest = null; //缓冲输出流
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry; //每个zip条目的实例

            while ((entry = zis.getNextEntry()) != null) {

                try {
                    Log.i("Unzip: ", "=" + entry);
                    int count;
                    byte data[] = new byte[BUFFER];
                    strEntry = entry.getName();

                    File entryFile = new File(targetDir + strEntry);
                    File entryDir = new File(entryFile.getParent());
                    if (!entryDir.exists()) {
                        entryDir.mkdirs();
                    }

                    FileOutputStream fos = new FileOutputStream(entryFile);
                    dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            zis.close();
        } catch (Exception cwj) {
            cwj.printStackTrace();
        }
    }

    public static String GetFileName(String Path, String Extension)  //搜索目录，扩展名，是否进入子文件夹
    {
        File[] files = new File(Path).listFiles();
        String filename = null;
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (!f.isDirectory()) {
                filename = f.getName();
                if (filename.contains(Extension)) {
                    return filename;
                } else {
                    filename = null;
                }

            }
//            else if (f.isDirectory() && f.getPath().indexOf("/.") == -1)  //忽略点文件（隐藏文件/文件夹）
//                GetFileName(f.getPath(), Extension, IsIterative);
        }

        return filename;
    }

    // 获取当前目录下除ky04所有的bin文件
    public static Vector<String> GetFileNames(String fileAbsolutePath) {
        Vector<String> vecFile = new Vector<String>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();
                if (!filename.contains("KY04")) {//ky04固件不入列表
                    // 判断是否为bin结尾
                    if (filename.trim().toLowerCase().endsWith(".bin")) {
                        vecFile.add(filename);
                    }
                }

            }
        }
        return vecFile;
    }

    // 获取当前目录下所有的bin文件
    public static Vector<String> GetAllFileName(String fileAbsolutePath) {
        Vector<String> vecFile = new Vector<String>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();
                // 判断是否为bin结尾
                if (filename.trim().toLowerCase().endsWith(".bin")) {
                    vecFile.add(filename);
                }

            }
        }
        return vecFile;
    }

    public static boolean IsFile(String path) {
        File file = new File(path);
//如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            System.out.println("//不存在");
            return false;
//            file.mkdir();
        } else {
            System.out.println("//目录存在");
        }
        return true;
    }
 public static boolean IsHaveFile(String path) {
     File file=new File(path);
     if(!file.exists())
     {
//         try {
//             file.createNewFile();
//
//         } catch (IOException e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//         }
         return false;
     }else {
         return true;

     }



 }

   public static void deleteAllFiles(File dir) {

       if (dir == null || !dir.exists() || !dir.isDirectory())
           return;
       for (File file : dir.listFiles()) {
           if (file.isFile())
               file.delete(); // 删除所有文件
           else if (file.isDirectory())
               deleteAllFiles(file); // 递规的方式删除文件夹
       }
       dir.delete();// 删除目录本身
    }

    // 获取文件
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file)  {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            size = 0 ;
        }
        return size;
    }

    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
     *
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }



}
