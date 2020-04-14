package concurrent.B;

import java.util.*;
import java.util.concurrent.*;

import static concurrent.LaunderThrowable.launderThrowable;

/**
 * Renderer
 * <p/>
 * Using CompletionService to render page elements as they become available
 *  使用CompletionService,使页面元素在下载完成后立刻显示出来
 *  提交一组计算任务,在计算完成后获得结果。Future,反复使用get,设置超时时间为0,也可以实现。
 *
 *  为每一幅图片创建下载任务,在线程池中执行,加载text,从CompletionService获得加载完成的图片,然后加载
 * @author Brian Goetz and Tim Peierls
 */
public abstract class F_Renderer {
    private final ExecutorService executor;

    F_Renderer(ExecutorService executor) {
        this.executor = executor;
    }

    void renderPage(CharSequence source) {
        final List<ImageInfo> info = scanForImageInfo(source);
        CompletionService<ImageData> completionService =
                new ExecutorCompletionService<ImageData>(executor);
        for (final ImageInfo imageInfo : info)
            completionService.submit(new Callable<ImageData>() {
                public ImageData call() {
                    return imageInfo.downloadImage();
                }
            });

        renderText(source);

        try {
            for (int t = 0, n = info.size(); t < n; t++) {
                Future<ImageData> f = completionService.take();
                ImageData imageData = f.get();
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
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
