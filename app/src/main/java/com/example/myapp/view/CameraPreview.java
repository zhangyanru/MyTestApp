package com.example.myapp.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yanru.zhang on 16/6/23.
 * Email:yanru.zhang@renren-inc.com
 */
public class CameraPreview extends SurfaceView  implements SurfaceHolder.Callback{
    /** LOG标识 */
     private static final String TAG = "CameraPreview";

    /** 分辨率 */
    private int previewWidth =0, previewHeight =0;

    /** 相机拍照监听接口 */
    private OnCameraStatusListener listener;

    public interface OnCameraStatusListener {

        // 相机拍照结束事件
        void onCameraStopped(byte[] data);

        // 拍摄时自动对焦事件
        void onAutoFocus(boolean success);

        // 触摸屏幕对焦事件
        void onTouchFocus(Camera mCamera);
    }

    public void setOnCameraStatusListener(OnCameraStatusListener listener) {
        this.listener = listener;
    }

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private int cameraPosition = 1;//0代表前置摄像头，1代表后置摄像头

    // 创建一个PictureCallback对象，并实现其中的onPictureTaken方法
    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

        // 该方法用于处理拍摄后的照片数据
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            // 停止照片拍摄
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;

            // 调用结束事件
            if (null != listener) {
                listener.onCameraStopped(data);
            }
        }
    };
    private Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] _data, Camera _camera) {
            /* 要处理raw data?写?否 */
        }
    };
    /*为了实现拍照的快门声音及拍照保存照片需要下面三个回调变量*/
    Camera.ShutterCallback myShutterCallback = new Camera.ShutterCallback()
            //快门按下的回调，在这里我们可以设置类似播放“咔嚓”声之类的操作。默认的就是咔嚓。
    {

        public void onShutter() {
            // TODO Auto-generated method stub


        }
    };

    // Preview类的构造方法
    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获得SurfaceHolder对象
        mHolder = getHolder();
        // 指定用于捕捉拍照事件的SurfaceHolder.Callback对象
        // 只要是实现SurfaceHolder.Callback接口的对象都行
        mHolder.addCallback(this);
        // 设置SurfaceHolder对象的类型
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);



    }

    // 在surface创建时激发
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
         Log.e(TAG, "==surfaceCreated==");
        try {
            initCamera();
        } catch (IOException e) {
            e.printStackTrace();
            // 释放手机摄像头
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    // 在surface销毁时激发
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
         Log.e(TAG, "==surfaceDestroyed==");
        if (mCamera != null) {
            mCamera.setPreviewCallback(null); /*在启动PreviewCallback时这个必须在前不然退出出错。
                       这里实际上注释掉也没关系*/
            mCamera.stopPreview();
            // 释放手机摄像头
            mCamera.release();
            mCamera = null;
        }
    }

    // 在surface的大小发生改变时激发
    @Override
    public void surfaceChanged(final SurfaceHolder holder, int format, int w, int h) {

    }

    /**
     * @throws IOException
     *
     */
    protected void initCamera(int i) throws IOException{
        // 获得Camera对象
        mCamera = Camera.open(i);
        // 设置竖屏
        mCamera.setDisplayOrientation(90);
        // 获取照相机参数
        Camera.Parameters parameters = mCamera.getParameters();
        // 设置照片格式
        parameters.setPictureFormat(PixelFormat.JPEG);
        //parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);//加上闪光灯模式会报错
        // 1连续对焦
//        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        // 设置预浏尺寸
//        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);//获取窗口的管理器
//        Display display = wm.getDefaultDisplay();//获得窗口里面的屏幕
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();// 选择合适的预览尺寸
        if (sizeList.size() >= 1) {
            Iterator<Camera.Size> itor = sizeList.iterator();
            while (itor.hasNext()) {
                Camera.Size cur = itor.next();
                if (cur.width >= previewWidth
                        && cur.height >= previewHeight) {
                    previewWidth = cur.width;
                    previewHeight = cur.height;
                    break;
                }
            }
        }
        parameters.setPreviewSize(previewWidth, previewHeight);
        // 设置照片分辨率
        parameters.setPictureSize(previewWidth, previewHeight);
        // 设置照相机参数
        mCamera.setParameters(parameters);
        // 设置用于显示拍照摄像的SurfaceHolder对象
        mCamera.setPreviewDisplay(mHolder);
        // 开始拍照
        mCamera.startPreview();
        mCamera.cancelAutoFocus();// 一定要加上这句，才可以连续聚集

        if (null != listener) {
            listener.onTouchFocus(mCamera);
        }
    }

    protected void initCamera() throws IOException {
        initCamera(0);
    }

    // 停止拍照，并将拍摄的照片传入PictureCallback接口的onPictureTaken方法
    public void takePicture(boolean isAutoFocus) {
         Log.e(TAG, "==takePicture==");
        if (mCamera != null) {
            if (true == isAutoFocus) {
                // 自动对焦
                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (null != listener) {
                            listener.onAutoFocus(success);
                        }
                        // 自动对焦成功后才拍摄
                        if (success) {
                            camera.takePicture(myShutterCallback, rawCallback, pictureCallback);
                        }
                    }
                });
            } else {
                mCamera.takePicture(myShutterCallback, rawCallback, pictureCallback);
            }
        }
    }

    /**
     * 选择前置还是后置摄像头
     */

    public void switchCamera() {
        //切换前后摄像头
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

        for(int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
            if(cameraPosition == 1) {
                //现在是后置，变更为前置
                if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();//停掉原来摄像头的预览
                    mCamera.release();//释放资源
                    mCamera = null;//取消原来摄像头

                    try {
                        initCamera(i);//打开当前选中的摄像头
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    cameraPosition = 0;
                    break;
                }
            } else {
                //现在是前置， 变更为后置
                if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();//停掉原来摄像头的预览
                    mCamera.release();//释放资源
                    mCamera = null;//取消原来摄像头

                    try {
                        initCamera(i);//打开当前选中的摄像头
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    cameraPosition = 1;
                    break;
                }
            }

        }
    }
}
