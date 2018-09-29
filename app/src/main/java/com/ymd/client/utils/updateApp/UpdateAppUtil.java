package com.ymd.client.utils.updateApp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.component.widget.myDialog.MyAlertDialog;
import com.ymd.client.component.widget.myDialog.MyProgressDialog;
import com.ymd.client.model.bean.YmdAppVersion;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.utils.AlertUtil;
import com.ymd.client.utils.CommonShared;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查更新工具类
 * @author 荣维鹤
 */
public class UpdateAppUtil {
	private Map<String, Object> mHashMap;
	private Context mContext;
	private Handler splashHandler;

	// 更新版本要用到的一些信息
	private YmdAppVersion updateInfo = new YmdAppVersion();
	private Message message;

	private String appName = "mass";

	public UpdateAppUtil(Context context, Handler splashHandler) {
		this.mContext = context;
		this.splashHandler = splashHandler;
		this.message = Message.obtain(splashHandler);
		message.what = 1;
	}

	/**
	 * 检查更新
	 */
	public void checkUpdate() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("type", "0");	//判断是ios系统还是android系统的；0：android; 1:ios
		params.put("userType", "1");
		WebUtil.getInstance().requestPOST(mContext, URLConstant.VERSION_NEW,  params, false, new WebUtil.WebCallBack() {

			@Override
			public void onWebSuccess(JSONObject resultJson) {
				try {
					updateInfo = new Gson().fromJson(resultJson.optString("appVersion"), YmdAppVersion.class);
					if (updateInfo != null) {
						successHandler.sendEmptyMessage(0);
					}
					else {
						splashHandler.sendMessage(message);
					}

				}catch (Exception e) {
					e.printStackTrace();
					splashHandler.sendMessage(message);
				}
			}

			@Override
            public void onWebFailed(String errorMsg) {
                splashHandler.sendMessage(message);
            }
        });

	}

	/**
	 * 获取程序版本号
	 */
	public static String getVersionName(Context context) {
		String versionName = "";
		try {
			versionName = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 获取程序版本号
	 */
	public static int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			versionCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	@SuppressLint("HandlerLeak")
	private Handler successHandler = new Handler() {
		public void handleMessage(Message msg) {
			// 如果有更新就提示
			if (isNeedUpdate()) {
				showUpdateDialog();
			}
			else {
				Message m = Message.obtain(splashHandler);
				m.what = 1;
				splashHandler.sendMessage(m);
			}
		};
	};

	private void showUpdateDialog() {
		MyAlertDialog dialog = new MyAlertDialog(mContext);
		dialog.show();
		dialog.setTitle("是否更新");
		dialog.setMessage(updateInfo.getVersionDesc());
		dialog.setPositiveButton(new MyAlertDialog.SureListener() {
			@Override
			public void onSureListener() {
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					downFile(updateInfo.getDownloadUrl());
				} else {
					//	ToolUtil.ToastMessage("SD卡不可用，请插入SD卡",ToolUtil.WRONG);
					AlertUtil.FailDialog(mContext, "SD卡不可用，请插入SD卡");
				}
			}
		});
		dialog.setNegativeButton(new MyAlertDialog.CancelListener() {
			@Override
			public void onCancelListener() {
				if (updateInfo.getUpgrade().equals("1")) {
					System.exit(0);
				} else {
					message.what = 0;
					splashHandler.sendMessage(message);
				}
			}
		});
		/*AlertUtil.DialogMessage(mContext, "是否更新", null,
				updateInfo.getVerDesc(),
				3, 0.4, null, null,
				new CustomDialog.SureListener() {

					@Override
					public void onSureListener() {
						if (Environment.getExternalStorageState().equals(
								Environment.MEDIA_MOUNTED)) {
							downFile("http://"+updateInfo.getApkUrl());
						} else {
							//	ToolUtil.ToastMessage("SD卡不可用，请插入SD卡",ToolUtil.WRONG);
							AlertUtil.FailDialog(mContext, "SD卡不可用，请插入SD卡");
						}
					}
				},
				new CustomDialog.CancelListener() {
					@Override
					public void onCancelListener() {
						if (updateInfo.getVerFlag().equals("1")) {
							System.exit(0);
						} else {
							message.what = 0;
							splashHandler.sendMessage(message);
						}
					}
				});*/
	}

	private boolean isNeedUpdate() {

		int v = ToolUtil.changeInteger(updateInfo.getVersionNo()); // 最新版本的版本号
		if (v > getVersionCode(mContext)) {
			return true;
		} else {
			return false;
		}
	}

	// 获取当前版本的版本号
	private String getVersion() {
		try {
			PackageManager packageManager = mContext.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					mContext.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "no version";
		}
	}

	private MyProgressDialog downDialog;
	void downFile(final String apkUrl) {
		downDialog = new MyProgressDialog(mContext);
		downDialog.show();
		new Thread() {
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(apkUrl);
				HttpResponse response;
				try {
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					double length = (double) entity.getContentLength();   //获取文件大小
					//设置进度条的总长度
					downDialog.setMax(100);
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					if (is != null) {
						String fileUrl = Environment.getExternalStorageDirectory() + "/mass/apk/" + appName;

						File file2 =new File( Environment.getExternalStorageDirectory() + "/mass/apk");
						System.out.println(file2);
						//如果文件夹不存在则创建
						if  (!file2.exists()  && !file2.isDirectory())
						{
							file2.mkdirs();
						}
						File file = new File(fileUrl);
						fileOutputStream = new FileOutputStream(file);
						//这个是缓冲区，即一次读取10个比特，我弄的小了点，因为在本地，所以数值太大一下就下载完了,
						//看不出progressbar的效果。
						byte[] buf = new byte[1024];
						int ch = -1;
						long process = 0;
						int flag = 0;
						while ((ch = is.read(buf)) != -1) {
							fileOutputStream.write(buf, 0, ch);
							process += ch;

							DecimalFormat f = new DecimalFormat("0.##");
							flag = (int)(ToolUtil.changeDouble(f.format(process/length)) * 100);
							downDialog.setProgress(flag);
							downDialog.setProgressNum(flag);
						}

					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					down();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}.start();
	}

	void down() {
		successHandler.post(new Runnable() {
			public void run() {
				downDialog.dismiss();
				update();
			}
		});
	}

	void update() {
		CommonShared.setBoolean("NEW_VERSION", true);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.parse("file://"+ Environment.getExternalStorageDirectory()+ "/mass/apk/" + appName),
				"application/vnd.android.package-archive");
		mContext.startActivity(intent);
		System.exit(0);
	}
}