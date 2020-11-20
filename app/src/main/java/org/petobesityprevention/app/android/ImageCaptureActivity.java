package org.petobesityprevention.app.android;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ImageCaptureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2Fragment.newInstance())
                    .commit();
        }
    }

}








//import android.content.Context;
//import android.graphics.ImageFormat;
//import android.graphics.SurfaceTexture;
//import android.hardware.camera2.CameraAccessException;
//import android.hardware.camera2.CameraCaptureSession;
//import android.hardware.camera2.CameraCharacteristics;
//import android.hardware.camera2.CameraDevice;
//import android.hardware.camera2.CameraManager;
//import android.hardware.camera2.CameraMetadata;
//import android.hardware.camera2.CaptureRequest;
//import android.media.ImageReader;
//import android.os.Bundle;
//import android.util.Size;
//import android.util.SparseIntArray;
//import android.view.Surface;
//import android.view.TextureView;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ImageCaptureActivity extends AppCompatActivity {
//    //private static final int REQUEST_CODE = 1;
//    private Button imgCapture;
//    private TextureView textureView;
//
//    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
//    static {
//        ORIENTATIONS.append(Surface.ROTATION_0, 90);
//        ORIENTATIONS.append(Surface.ROTATION_90, 0);
//        ORIENTATIONS.append(Surface.ROTATION_180, 270);
//        ORIENTATIONS.append(Surface.ROTATION_270, 180);
//    }
//
//    private String cameraId;
//    private CameraDevice cameraDevice;
//    private CameraCaptureSession cameraCaptureSession;
//    private CaptureRequest.Builder captureRequestBuilder;
//    private Size imageDimension;
//    private ImageReader imageReader;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_image_capture);
//
//        textureView = findViewById(R.id.id_textureView);
//        assert textureView != null;
//
//        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
//            @Override
//            public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {
//                openCamera();
//            }
//
//            @Override
//            public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {
//
//            }
//
//            @Override
//            public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
//                return false;
//            }
//
//            @Override
//            public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {
//
//            }
//        });
//
//        imgCapture = findViewById(R.id.id_image_button);
//        imgCapture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                takePicture();
//            }
//        });
////        imageView = findViewById(R.id.id_imageview);
////
////        Button image_capture = findViewById(R.id.id_image_button);
////        image_capture.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                startActivityForResult(intent, REQUEST_CODE);
////            }
////        });
//    }
//
//    private void takePicture() {
//        if(cameraDevice == null)
//            return;
//        CameraManager manager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
//        try {
//            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());
//            Size[] jpegSizes = null;
//            if (characteristics != null) {
//                jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);
//            }
//
//            int width = 640;
//            int height = 480;
//            if(jpegSizes != null && jpegSizes.length > 0) {
//                width = jpegSizes[0].getWidth();
//                height = jpegSizes[0].getHeight();
//            }
//            ImageReader reader = ImageReader.newInstance(width,height,ImageFormat.JPEG, 1);
//            List<Surface> outputSurface = new ArrayList<>(2);
//            outputSurface.add(reader.getSurface());
//            outputSurface.add(new Surface(textureView.getSurfaceTexture()));
//
//            CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
//            captureBuilder.addTarget(reader.getSurface());
//            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
//
//            int rotation = getWindowManager().getDefaultDisplay().getRotation();
//            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void openCamera() {
//    }
//
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////
////        if (requestCode == REQUEST_CODE) {
////            Bitmap photo = (Bitmap)data.getExtras().get("data");
////            imageView.setImageBitmap(photo);
////        }
////    }
//}