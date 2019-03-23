/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.console.util;

import com.console.storage.StorageException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.util.FileSystemUtils;

/**
 *
 * @author Dell
 */
public class QrCodeUtil {

    private String file_path = "C:\\Users\\Dell\\Documents\\NetBeansProjects\\console\\src\\main\\resources\\static\\images\\qrcode\\";

    String QR_img_name = "123" + "_" + "Participant" + ".png";
    Path path = FileSystems.getDefault().getPath(file_path + QR_img_name);

    //the method for generating qr code image 
    public String generateQRCImg(String qrCharacterSet, String qrErrorCorrectionLevel, int width, int height,
            String qrContent, String qrImgExtension) throws WriterException, IOException {

        deleteAll();

        Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        hintMap.put(EncodeHintType.CHARACTER_SET, qrCharacterSet);

        // Now with zxing version 3.2.1 you could change border size (white border size
        // to just 1)
        hintMap.put(EncodeHintType.MARGIN, 3);
        /* default = 4 */
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.valueOf(qrErrorCorrectionLevel));

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, width, height, hintMap);

        int qr_img_width = byteMatrix.getWidth();
        int qr_img_height = byteMatrix.getHeight();
        BufferedImage qr_image = new BufferedImage(qr_img_width, qr_img_height, BufferedImage.TYPE_INT_RGB);
        qr_image.createGraphics();

        Graphics2D graphics = (Graphics2D) qr_image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, qr_img_width, qr_img_height);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < qr_img_width; i++) {
            for (int j = 0; j < qr_img_height; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        //ImageIO.write(qr_image, qrImgExtension, path);

        MatrixToImageWriter.writeToPath(byteMatrix, qrImgExtension, path);
        //return "resources/images/qrcs/" + QR_img_name;
        return QR_img_name;

    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(path.toFile());
    }

    public void init() {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

}
