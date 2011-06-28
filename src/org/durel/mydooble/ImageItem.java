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
					out.doc, out.page, true, false);
			// TODO
			float x = 20, y = 20;
			contentStream.drawImage(ximage, x, y);
			contentStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
