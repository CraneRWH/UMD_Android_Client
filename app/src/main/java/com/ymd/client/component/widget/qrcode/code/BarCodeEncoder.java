package com.ymd.client.component.widget.qrcode.code;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

public class BarCodeEncoder {

	private BarCodeEncoder() {
	}

	/**
	 * 生成条形码
	 * 
	 * @param context
	 * @param content
	 *            需要生成的内容
	 * @param desiredWidth
	 *            生成条形码的宽带
	 * @param desiredHeight
	 *            生成条形码的高度
	 * @param displayCode
	 *            是否在条形码下方显示内容
	 * @return
	 */
	public static void CreatBarCode(Context context, String content,
			int desiredWidth, int desiredHeight, int textHeight,
			boolean displayCode, BarCodeCreatedInterface listener) {
		BarCodeEncoder.creatBarcode(context, content, desiredWidth,
				desiredHeight, textHeight, displayCode, listener);
	}

	/**
	 * 生成条形码
	 * 
	 * @param context
	 * @param content
	 *            需要生成的内容
	 * @param desiredWidth
	 *            生成条形码的宽带
	 * @param desiredHeight
	 *            生成条形码的高度
	 * @param displayCode
	 *            是否在条形码下方显示内容
	 * @return
	 */
	private static void creatBarcode(final Context context,
			final String content, final int desiredWidth,
			final int desiredHeight, final int textHeight,
			final boolean displayCode, final BarCodeCreatedInterface listener) {

		new AsyncTask<Void, Void, Bitmap>() {
			@Override
			protected Bitmap doInBackground(Void... params) {
				Bitmap ruseltBitmap = null;
				/**
				 * 条形码的编码类型
				 */
				BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;
				try {
					if (displayCode) {
						Bitmap barcodeBitmap = encodeAsBitmap(content,
								barcodeFormat, desiredWidth, desiredHeight);
						Bitmap codeBitmap = creatCodeBitmap(content,
								desiredWidth, textHeight, context);
						ruseltBitmap = mixtureBitmap(barcodeBitmap, codeBitmap,
								new PointF(0, desiredHeight));
					} else {
						ruseltBitmap = encodeAsBitmap(content, barcodeFormat,
								desiredWidth, desiredHeight);
					}
				} catch (Exception e) {
				}
				return ruseltBitmap;
			}

			@Override
			protected void onPostExecute(Bitmap bitmap) {
				if (listener != null) {
					if (bitmap != null) {
						listener.onEncodeBarCodeSuccess(bitmap);
					} else {
						listener.onEncodeBarCodeFailure();
					}
				}
			}
		}.execute();
	}

	/**
	 * 生成条形码的Bitmap
	 * 
	 * @param contents
	 *            需要生成的内容
	 * @param format
	 *            编码格式
	 * @param desiredWidth
	 * @param desiredHeight
	 * @return
	 * @throws WriterException
	 */
	protected static Bitmap encodeAsBitmap(String contents,
			BarcodeFormat format, int desiredWidth, int desiredHeight) {
		final int WHITE = 0xFFFFFFFF;
		final int BLACK = 0xFF000000;

		MultiFormatWriter writer = new MultiFormatWriter();
		BitMatrix result = null;
		try {
			result = writer.encode(contents, format, desiredWidth,
					desiredHeight, getEncodeHintMap());
		} catch (WriterException e) {
			e.printStackTrace();
		}

		// BitMatrix转换成Bitmap
		int width = result.getWidth();
		int height = result.getHeight();
		int[] pixels = new int[width * height];
		// All are 0, or black, by default
		for (int y = 0; y < height; y++) {
			int offset = y * width;
			for (int x = 0; x < width; x++) {
				pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	/**
	 * 生成显示编码的Bitmap
	 * 
	 * @param contents
	 * @param width
	 * @param textHeight
	 * @param context
	 * @return
	 */
	protected static Bitmap creatCodeBitmap(String contents, int width,
			int textHeight, Context context) {
		TextView tv = new TextView(context);
		LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		tv.setLayoutParams(layoutParams);
		// 每4位添加一个空格，最后6位不添加
		StringBuilder str1 = new StringBuilder(contents);
		for (int i = 4; i <= str1.length() - 1; i += 5) {
			str1.insert(i, ' ');
			if (i == 14) {
				break;
			}
		}
		contents = str1 + "";
		tv.setText(contents);
		tv.setGravity(Gravity.CENTER);
		tv.setWidth(width);
		tv.setHeight(textHeight);
		tv.setDrawingCacheEnabled(true);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		tv.setTextColor(Color.BLACK);
		tv.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());

		tv.buildDrawingCache();
		Bitmap bitmapCode = tv.getDrawingCache();
		return bitmapCode;
	}

	/**
	 * 将两个Bitmap合并成一个
	 * 
	 * @param first
	 * @param second
	 * @param fromPoint
	 *            第二个Bitmap开始绘制的起始位置（相对于第一个Bitmap）
	 * @return
	 */
	protected static Bitmap mixtureBitmap(Bitmap first, Bitmap second,
			PointF fromPoint) {
		if (first == null || second == null || fromPoint == null) {
			return null;
		}
		Bitmap newBitmap = Bitmap.createBitmap(first.getWidth(),
				first.getHeight() + second.getHeight(), Config.ARGB_4444);
		Canvas cv = new Canvas(newBitmap);
		cv.drawBitmap(first, 0, 0, null);
		cv.drawBitmap(second, fromPoint.x, fromPoint.y, null);
		cv.save(Canvas.ALL_SAVE_FLAG);
		cv.restore();

		return newBitmap;
	}

	public interface BarCodeCreatedInterface {
		/**
		 * 创建条形码图片成功
		 * 
		 * @param bitmap
		 */
		void onEncodeBarCodeSuccess(Bitmap bitmap);

		/**
		 * 创建条形码图片失败
		 */
		void onEncodeBarCodeFailure();
	}

	/**
	 * 获得设置好的编码参数
	 * 
	 * @return 编码参数
	 */
	private static Hashtable<EncodeHintType, Object> getEncodeHintMap() {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		// 设置编码为utf-8
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		// 设置QR二维码的纠错级别——这里选择最高H级别
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		return hints;
	}
}
