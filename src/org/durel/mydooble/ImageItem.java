package org.durel.mydooble;

/*
 Copyright © 2011 Bastien Durel

 This file is part of myDobble.

 myDobble is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 myDobble is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with myDobble.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageItem extends Item {
	protected String image;

	public ImageItem(String img) throws IOException {
		super();
		image = img;
		if (!(image.toLowerCase().endsWith(".jpg")) || image.toLowerCase().endsWith(".tif")
				|| image.toLowerCase().endsWith(".tiff")) {
			throw new IOException("Image type not supported:" + image);
		} else {
			// Throws FileNotFoundException
			new FileInputStream(image);
		}
	}

	private static BufferedImage resizeImage(BufferedImage originalImage, int w, int h) {
		BufferedImage resizedImage = new BufferedImage(w, h,
				originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, w, h, null);
		g.dispose();

		return resizedImage;
	}

	@Override
	public void toPDF(PDF out, int i) {
		super.toPDF(out, i);/*
		try {
			PDXObjectImage ximage = null;
			if (image.toLowerCase().endsWith(".jpg")) {
				ximage = new PDJpeg(out.doc, new FileInputStream(image));
			} else if (image.toLowerCase().endsWith(".tif") || image.toLowerCase().endsWith(".tiff")) {
				ximage = new PDCcitt(out.doc, new RandomAccessFile(new File(image), "r"));
			}

			float ih = (float) (ximage.getHeight());
			float iw = (float) (ximage.getWidth());
			if (ih > h || iw > w) {
				float xratio = iw / w;
				float yratio = ih / h;
				float ratio = Math.max(xratio, yratio);
				log.info("ih: " + ih + " - iw: " + iw + " - xratio: " + xratio + " - yratio: " + yratio
						+ " - ratio: " + ratio + " --> " + (int) (iw / ratio) + "x" + (int) (ih / ratio));
				BufferedImage tmp = resizeImage(ximage.getRGBImage(), (int) (iw / ratio),
						(int) (ih / ratio));
				File temp = File.createTempFile("tmpfile", ".jpg");
				ImageIO.write(tmp, "jpg", temp);
				ximage = new PDJpeg(out.doc, new FileInputStream(temp));
				temp.delete();
			}

			PDPageContentStream contentStream = new PDPageContentStream(out.doc, out.page, true, false);
			float x = (w + m) * c + bx + m / 2, y = (h + m) * r + by + m / 2;
			// contentStream.setNonStrokingColor(Color.RED);
			// contentStream.fillRect(x, y, w, h);
			x += (w - ximage.getWidth()) / 2;
			y += (h - ximage.getHeight()) / 2;
			contentStream.drawImage(ximage, x, y);
			log.info("x: " + x + " - y: " + y + " w: " + w + " - h: " + h);
			contentStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
