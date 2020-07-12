package com.bjtu.bookshop.service;

import com.bjtu.bookshop.bean.response.PicResponses;
import com.bjtu.bookshop.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;

@Service
public class PicService {
    @Value("${config-string.picture-path}")
    private String picturePrefix;

    private static final Set<String> okSuffix = new HashSet<String>() {{
        add("jpg");
        add("png");
        add("bmp");
    }};


    public PicResponses.PicUploadResponse picUpload(int uid, MultipartFile file) {
        String picPath = picturePrefix + uid + "\\";
        File father = new File(picPath);
        if (!father.exists()) {
            father.mkdirs();
        }

        String fileName = file.getOriginalFilename();
        String pictureSuffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        String innerName = "";
        if (!okSuffix.contains(pictureSuffix)) {
            return new PicResponses.PicUploadResponse(-2333, "");
        }

        File outPicture = null;
        FileOutputStream fos = null;
        do {
            innerName = StringUtil.getRandString();
            String picturePath = picPath + innerName + "." + pictureSuffix;
            outPicture = new File(picturePath);
        } while (outPicture.exists());

        try {
            outPicture.createNewFile();
            fos = new FileOutputStream(outPicture);
            fos.write(file.getBytes());
            return new PicResponses.PicUploadResponse(0, "" + uid + "/" + innerName + "." + pictureSuffix);
        } catch (Exception ignored) {
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (Exception ignored) {
            }
        }

        return new PicResponses.PicUploadResponse(-777, "");
    }
}

