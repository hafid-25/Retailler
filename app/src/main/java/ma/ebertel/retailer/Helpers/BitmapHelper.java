package ma.ebertel.retailer.Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public abstract class BitmapHelper {

    // convert the bitmap into an array
    public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = null;
        try {
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] image = stream.toByteArray();
            return image;
        }finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {

                }
            }

        }
    }

    public static Bitmap reizeBitmap(String FilePath, int newWidth, int newHeight){
        Bitmap myBitmap = BitmapFactory.decodeFile(FilePath);
        //Bitmap myBitmap = decodeFile(imgFile);
        // reduis the image with and hight and size to(156 kb)
        return Bitmap.createScaledBitmap(myBitmap, newWidth, newHeight, false);
    }

    public static Bitmap convertByteArrayToBitmap(byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
