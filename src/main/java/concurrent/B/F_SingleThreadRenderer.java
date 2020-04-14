package concurrent.B;

import java.util.*;

/**
 * SingleThreadRendere
 * <p/>
 * Rendering page elements sequentially
 *  串行渲染页面元素
 *  第一种方式:遇到图片加载图片遇到文字加载文字
 *  第二种(本类采用):先加载文字，遇到图片则预留位置。文字加载完成后下载所有图片,在加载所有图片
 * @author Brian Goetz and Tim Peierls
 */
public abstract class F_SingleThreadRenderer {
    void renderPage(CharSequence source) {
        renderText(source);
        List<ImageData> imageData = new ArrayList<ImageData>();
        for (ImageInfo imageInfo : scanForImageInfo(source))
            imageData.add(imageInfo.downloadImage());
        for (ImageData data : imageData)
            renderImage(data);
    }

    interface ImageData {
    }

    interface ImageInfo {
        ImageData downloadImage();
    }

    abstract void renderText(CharSequence s);
    abstract List<ImageInfo> scanForImageInfo(CharSequence s);
    abstract void renderImage(ImageData i);
}
