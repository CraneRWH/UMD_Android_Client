package com.ymd.client.component.widget.photo.selectphoto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class Bimp {
	public static int max = 0;
	public static boolean act_bool = true;
	public static List<Bitmap> bmp = new ArrayList<Bitmap>();	
	
	//图片sd地址  上传服务器时把图片调用下面方法压缩后 保存到临时文件夹 图片压缩后小于100KB，失真度不明显
	public static List<String> drr = new ArrayList<String>();
	

	public static Bitmap revitionImageSize(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		//设置这两个属性可以减少内存损耗
		options.inInputShareable = true;
		options.inPurgeable = true;
		
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 1000)
					&& (options.outHeight >> i <= 1000)) {
				in = new BufferedInputStream(
						new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}
	public static Bitmap convertToBitmap(String path) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		// 返回为空
		BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
//		if (width > w || height > h) {
//			// 缩放
//			scaleWidth = ((float) width) / w;
//			scaleHeight = ((float) height) / h;
//		}
		opts.inJustDecodeBounds = false;
		float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int)scale;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
		return Bitmap.createScaledBitmap(weak.get(), width, height, true);
	}
	public static Bitmap compressImage(String path) throws IOException{
		Bitmap bitmap = convertToBitmap(path);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int option = 100;  
		// 循环判断如果压缩后图片是否大于100kb,大于继续压缩 
		while ( baos.toByteArray().length / 1024 >200) {
			// 重置baos即清空baos 
			baos.reset();
			// 这里压缩options%，把压缩后的数据存放到baos中  
			bitmap.compress(Bitmap.CompressFormat.JPEG, option, baos);
			option -= 10;//每次都减少10  
		}  
		// 把压缩后的数据baos存放到ByteArrayInputStream中  
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		// 把ByteArrayInputStream数据生成图片  
		Bitmap mBitmap = BitmapFactory.decodeStream(isBm, null, null);
		return mBitmap;  
	}
}
