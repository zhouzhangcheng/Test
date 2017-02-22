package com.jock.tbshopcar.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastHelper {
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static ToastHelper toast;
    private static Toast toast1 = null;
    private Context context;
    private static Object synObj = new Object();
    public static ToastHelper getInstance() {
        if (toast == null) {
            toast = new ToastHelper();
        }
        return toast;
    }

    public void init(Context context) {
        this.context = context;
    }

    public Toast _toast(String str) {
        return displayToastShort(str);
    }

    /**
     * ��ʾToast��durationΪshort
     *
     * @param context
     *            {@link Context} ��ǰ�����������
     * @param str
     *            {@link String} ��Ϣ����
     *
     */
    public Toast displayToastShort(String str) {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    /**
     * ��ʾToast��durationΪlong
     *
     * @param context
     *            {@link Context} ��ǰ�����������
     * @param str
     *            {@link String} ��Ϣ����
     *
     */
    public void displayToastLong(String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }
    /**
     * ���ٹر�toast
     * ��ͣ�ķ��ĵ��ĳ����ť��������toast�Ժ�toast���ݻ�һֱ���Ŷӵ���ʾ���������ܺܿ����ʧ���������ܻ�Ӱ���û���ʹ�á�
     *
     * @param context
     *            {@link Context} ��ǰ�����������
     * @param str
     *            {@link String} ��Ϣ����
     *
     * */

    public  void displayToastWithQuickClose(
            final String str) {
        new Thread(new Runnable() {
         
            public void run() {
                handler.post(new Runnable() {
                   
                    public void run() {
                        synchronized (synObj) {
                            if (toast1 != null) {
                                toast1.setText(str);
                                toast1.setDuration(Toast.LENGTH_SHORT);
                            } else {
                                toast1 = Toast.makeText(context, str,
                                        Toast.LENGTH_SHORT);
                            }
                            toast1.show();
                        }
                    }
                });
            }
        }).start();
    }
}
