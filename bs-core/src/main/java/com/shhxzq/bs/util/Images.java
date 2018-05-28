package com.shhxzq.bs.util;

import com.shhxzq.bs.constants.AppConstants;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;

/**
 * @author kangyonggan
 * @since 16/5/10
 */
@Component
public class Images {

    //大Logo
    public static String large(String source) throws Exception {
        return thumbnails(source, "l", 200, 200);
    }

    //中Logo
    public static String middle(String source) throws Exception {
        return thumbnails(source, "m", 128, 128);
    }

    //小Logo
    public static String small(String source) throws Exception {
        return thumbnails(source, "s", 64, 64);
    }

    private static String thumbnails(String source, String suffix, int width, int height) throws Exception {
        String desc = FileUpload.extractFilePath(source, suffix);
        Thumbnails.of(AppConstants.APP_ROOT + source)
                .size(width, height)
                .keepAspectRatio(false)
                .toFile(AppConstants.APP_ROOT + desc);

        return desc;
    }
}
