package eventcoordinator2017.myevent.utils;

/**
 * Created by Mark Jansen Calderon on 2/10/2017.
 */


import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.util.Util;

/**
 * A {@link com.bumptech.glide.load.engine.Resource} implementation for
 * {@link PaletteBitmap}.
 */
public class PaletteBitmapResource implements Resource<PaletteBitmap> {
    private PaletteBitmap paletteBitmap;
    private BitmapPool bitmapPool;

    public PaletteBitmapResource(PaletteBitmap paletteBitmap, BitmapPool bitmapPool) {
        this.paletteBitmap = paletteBitmap;
        this.bitmapPool = bitmapPool;
    }

    @Override
    public PaletteBitmap get() {
        return paletteBitmap;
    }

    @Override
    public int getSize() {
        return Util.getBitmapByteSize(paletteBitmap.bitmap);
    }

    @Override
    public void recycle() {
        if (!bitmapPool.put(paletteBitmap.bitmap)) {
            paletteBitmap.bitmap.recycle();
        }
    }
}