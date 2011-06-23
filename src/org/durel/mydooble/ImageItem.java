package org.durel.mydooble;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDCcitt;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

public class ImageItem extends Item {
	protected String image;

	public ImageItem(String img) throws IOException {
		image = img;
		if (!(image.toLowerCase().endsWith(".jpg"))
				|| image.toLowerCase().endsWith(".tif")
				|| image.toLowerCase().endsWith(".tiff")) {
			throw new IOException("Image type not supported:" + image);
		} else {
			// Throws FileNotFoundException
			new FileInputStream(image);
		}
	}

	@Override
	public void toPDF(PDF out) {
		try {
			PDXObjectImage ximage = null;
			if (image.toLowerCase().endsWith(".jpg")) {
				ximage = new PDJpeg(out.doc, new FileInputStream(image));
			} else if (image.toLowerCase().endsWith(".tif")
					|| image.toLowerCase().endsWith(".tiff")) {
				ximage = new PDCcitt(out.doc, new RandomAccessFile(new File(
						image), "r"));
			}

			PDPageContentStream contentStream = new PDPageContentStream(
					out.doc, out.page);
			// TODO
			float x = 20, y = 20;
			contentStream.drawImage(ximage, x, y);
			contentStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
