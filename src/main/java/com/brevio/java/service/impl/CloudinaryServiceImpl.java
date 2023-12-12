package com.brevio.java.service.impl;

import com.brevio.java.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.brevio.java.listener.CloudinaryListener;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
@Log
public class CloudinaryServiceImpl implements CloudinaryService {
    private static String PUBLIC_ID = "public_id";
    private static String FOLDER_KEY = "folder";
    private static String FOLDER = "assets/";
    private final Cloudinary cloudinary;

    @Override
    public void upload(byte[] file, CloudinaryListener listener, String path) {

        try {
            log.info("Enviando para cloudinary");
            if (path.equals("profiles")) {
                log.info("New profile picture");
                BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(file));
                int height = bufferedImage.getHeight();
                int width = bufferedImage.getWidth();
                int targetWidth = 60;
                int targetHeight = 60;
                log.info("profile picture resize...");
                if (width > 720 || height > 600) {
                    double resizingFactor = 0.1;
                    targetWidth = (int) Math.round(width * resizingFactor);
                    targetHeight = (int) Math.round(height * resizingFactor);
                }
                Image image = bufferedImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_FAST);
                BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.SCALE_FAST);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                outputImage.getGraphics().drawImage(image, 0, 0, null);

                ImageIO.write(outputImage, "png", baos);
                file = baos.toByteArray();
                log.info("New profile picture resized. Ready for upload");
                Map upload = cloudinary.uploader().upload(file,
                        ObjectUtils.asMap(PUBLIC_ID, UUID.randomUUID().toString() + ".png"
                                , FOLDER_KEY, FOLDER + path
                        ));
                log.info("Enviando com sucesso");
                listener.onUploadFinish(upload);
            } else {
                Map upload = cloudinary.uploader().upload(file,
                        ObjectUtils.asMap(PUBLIC_ID, UUID.randomUUID().toString()
                                , FOLDER_KEY, FOLDER + path
                        ));
                log.info("Enviando com sucesso");
                listener.onUploadFinish(upload);
            }
        } catch (UnknownHostException unknownHostException) {
            log.warning("Falha ao fazer upload. Msg: " + unknownHostException.getMessage());
            log.info("Tentando novamente");
            Map upload = null;
            try {
                upload = cloudinary.uploader().upload(file,
                        ObjectUtils.asMap(PUBLIC_ID, UUID.randomUUID().toString()));
                listener.onUploadFinish(upload);
            } catch (Exception e) {
                listener.onUploadFinish(upload);
            }
        } catch (IOException e) {

            e.printStackTrace();

        }
    }

}
