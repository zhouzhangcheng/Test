package com.newgame.sdk.yyaost;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.newgame.sdk.yyaost.R;
/**
 * @author coolszy
 * @date 2012-4-26
 * @blog http://blog.92coding.com
 */

public class UpdateManager {
	/* ������ */
	private static final int DOWNLOAD = 1;
	/* ���ؽ��� */
	private static final int DOWNLOAD_FINISH = 2;
	/* ���������XML��Ϣ */
	HashMap<String, String> mHashMap;
	/* ���ر���·�� */
	private String mSavePath;
	/* ����app���� */
	public static String appName="";
	/* ����app�汾��Ϣ */
	public static String upgradeMsg="";
	/* ����app�汾�� */
	public static String androidvesion="";
	/* ��¼���������� */
	public static int progress;
	/* �Ƿ�ȡ������ */
	private boolean cancelUpdate = false;

	private Context mContext;
	/* ���½����� */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;

	public static String updateUrl = "";

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// ��������
			case DOWNLOAD:
				// ���ý�����λ��
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// ��װ�ļ�
				installApk();
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context) {
		this.mContext = context;
	}

	/**
	 * ���汾����
	 */
	public void checkUpdate(String str1) {
		try {

			String[] str = str1.split(",");
			Double version = Double.valueOf(str[0]).doubleValue();
			// String androidur = str[1];
			androidvesion=str[5];
			updateUrl = str[1];
			appName=str[2];
			upgradeMsg=str[3];
			// version = version1;
			// versionurl = androidurl;
			if (isUpdate(version)) {
				// �汾�и���
				showNoticeDialog();
			} else {
//				 Toast.makeText(mContext, R.string.soft_update_no,
//				 Toast.LENGTH_LONG)
//				 .show();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * ���汾����
	 */
	public void stcheckUpdate(String str1) {
		try {

			String[] str = str1.split(",");
			Double version = Double.valueOf(str[0]).doubleValue();
			// String androidur = str[1];
			updateUrl = str[1];
			appName=str[2];
			// version = version1;
			// versionurl = androidurl;
			if (isUpdate(version)) {
				// �汾�и���
				showNoticeDialog();
			} else {
				 Toast.makeText(mContext, R.string.soft_update_no,
				 Toast.LENGTH_LONG)
				 .show();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * �������Ƿ��и��°汾
	 * 
	 * @return
	 */
	private boolean isUpdate(Double serviceCode) {
		// ��ȡ��ǰ����汾
		Double versionCode = (double) getVersionCode(mContext);
		if (serviceCode > versionCode) {
			return true;
		}
		return false;
	}

	/**
	 * ��ȡ����汾��
	 * 
	 * @param context
	 * @return
	 */
	private int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// ��ȡ����汾�ţ���ӦAndroidManifest.xml��android:versionCode
			versionCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * ��ʾ������¶Ի���
	 */
	private void showNoticeDialog() {
		// ����Ի���
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("��������汾"+androidvesion);
		StringBuilder sb = new StringBuilder();
		String[] str= upgradeMsg.split("&");
		for(int i=0;i<str.length;i++)
		{
			sb.append(str[i]);
			sb.append("\n");
		}
		builder.setMessage(sb.toString());
//		builder.setMessage("�����°��𺳷��������������о�ϲ����\n �°����ԣ�\n 1����ҳ������а�̽��Ƶ����Ŀ��������ҩ��Ƭ������Ʒ�����ðٻ���\n 2�����������������Ƶ�������㰴ʱ�Σ��¡������꣩�鿴������Ʒ��\n 3��������ҳȫ�¸İ棬��Ҿ�Ʒȫ��λ��Ƕ�չʾ��\n 4�����̹����̨����Ʊ���ߣ����� �����ӷ�Ʊ��ѡ�");
		// ����
		builder.setPositiveButton(R.string.soft_update_updatebtn,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// ��ʾ���ضԻ���
						showDownloadDialog();
					}
				});
		// �Ժ����
		builder.setNegativeButton(R.string.soft_update_later,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
//		Dialog noticeDialog = builder.create();
		builder.create().show();
	}

	/**
	 * ��ʾ������ضԻ���
	 */
	private void showDownloadDialog() {
		// ����������ضԻ���
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_updating);
		// �����ضԻ������ӽ�����
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.softupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		builder.setView(v);
		// ȡ������
		builder.setNegativeButton(R.string.soft_update_cancel,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// ����ȡ��״̬
						cancelUpdate = true;
					}
				});
		mDownloadDialog = builder.create();
		mDownloadDialog.show();
		// �����ļ�
		downloadApk();
	}

	/**
	 * ����apk�ļ�
	 */
	private void downloadApk() {
		// �������߳��������
		new downloadApkThread().start();
	}

	/**
	 * �����ļ��߳�
	 * 
	 * @author coolszy
	 * @date 2012-4-26
	 * @blog http://blog.92coding.com
	 */
	private class downloadApkThread extends Thread {
		@Override
		public void run() {
			try {
				// �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// ��ô洢����·��
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					mSavePath = sdpath + "download";
					URL url = new URL(updateUrl);
					// ��������
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// ��ȡ�ļ���С
					int length = conn.getContentLength();
					// ����������
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// �ж��ļ�Ŀ¼�Ƿ����
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath, appName);
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// ����
					byte buf[] = new byte[1024];
					// д�뵽�ļ���
					do {
						int numread = is.read(buf);
						count += numread;
						// ���������λ��
						progress = (int) (((float) count / 1648640) * 100);
						// ���½���
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// �������
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// д���ļ�
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// ���ȡ����ֹͣ����.
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// ȡ�����ضԻ�����ʾ
			mDownloadDialog.dismiss();
		}
	};

	/**
	 * ��װAPK�ļ�
	 */
	private void installApk() {
		File apkfile = new File(mSavePath, appName);
		if (!apkfile.exists()) {
			return;
		}
		// ͨ��Intent��װAPK�ļ�
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
}
