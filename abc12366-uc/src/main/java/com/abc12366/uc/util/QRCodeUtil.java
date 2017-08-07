package com.abc12366.uc.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import sun.misc.BASE64Encoder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
/**
 * 二维码生产工具
 * @author zhushuai 2017-8-5
 *
 */
public class QRCodeUtil {
	private static BufferedImage creatQRcode(String content,int qrCodeSize) {
		try {
			Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); // 矫错级别
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			// 创建比特矩阵(位矩阵)的QR码编码的字符串
			BitMatrix byteMatrix = qrCodeWriter.encode(content,
					BarcodeFormat.QR_CODE, qrCodeSize,qrCodeSize, hintMap);
			// 使BufferedImage勾画QRCode (matrixWidth 是行二维码像素点)
			int matrixWidth = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(matrixWidth,
					matrixWidth, BufferedImage.TYPE_INT_RGB);
			image.createGraphics();
			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, matrixWidth, matrixWidth);
			// 使用比特矩阵画并保存图像
			graphics.setColor(Color.BLACK);
			for (int i = 0; i < matrixWidth; i++) {
				for (int j = 0; j < matrixWidth; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i - 100, j - 100, 1, 1);
					}
				}
			}
			return image;
		}  catch (Exception e) {	
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static String getCreatQRcodeString(String content,int qrCodeSize,String formatName){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		try {
			 ImageIO.write(creatQRcode(content,qrCodeSize), formatName, baos);
			 BASE64Encoder encoder = new BASE64Encoder();  
		     return encoder.encode(baos.toByteArray());
		} catch (IOException e) {
			return "";
		}
		
	}
}
