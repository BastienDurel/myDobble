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

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;

public class ImageItem extends Item {
	protected String image;

	public ImageItem(String img) throws IOException {
		super();
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
	public void toPDF(PDF out, int i) {
		super.toPDF(out, i);

		try {
			Image img = Image.getInstance(image);
			float ih = (float) (img.getHeight());
			float iw = (float) (img.getWidth());
			if (ih > h || iw > w) {
				float xratio = iw / w;
				float yratio = ih / h;
				float ratio = Math.max(xratio, yratio);
				log.info("ih: " + ih + " - iw: " + iw + " - xratio: " + xratio
						+ " - yratio: " + yratio + " - ratio: " + ratio
						+ " --> " + (int) (iw / ratio) + "x"
						+ (int) (ih / ratio));
				img.scalePercent(100 / ratio);
			}

			PdfContentByte cb = out.writer.getDirectContent();
			float x = bx + (c * (w + m)) + m;
			float y = by + (r * (h + m)) + m;
			img.setAbsolutePosition(x, y);
			cb.addImage(img, true);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
