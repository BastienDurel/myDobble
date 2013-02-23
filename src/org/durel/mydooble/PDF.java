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

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class PDF {
	Document doc = null;

	PdfWriter writer = null;

	ByteArrayOutputStream buffer = null;

	// PDPage page = null;
	Rectangle cardBox = null;

	int nbItems = 0;

	protected int COL_NUM = 3;

	protected int ROW_NUM = 3;

	private int col = -1;

	private int row = 0;

	final float WIDTH = (float) (6.3 * 72 / 2.54);

	final float HEIGHT = (float) (9.25 * 72 / 2.54);

	final float MARGIN = (float) (0.5 * 72 / 2.54);

	final BaseFont font = BaseFont.createFont();

	final int FONT_SIZE = 12;

	public PDF(int nbItems) throws IOException, DocumentException {
		buffer = new ByteArrayOutputStream();
		doc = new Document(PageSize.A4);
		writer = PdfWriter.getInstance(doc, buffer);
		writer.setCompressionLevel(0);
		this.nbItems = nbItems;
		doc.open();

		PdfContentByte cb = writer.getDirectContent();
		cb.setFontAndSize(font, FONT_SIZE);
	}

	public boolean save(String file) throws IOException {
		FileOutputStream fo = null;
		try {
			doc.close();
			writer.flush();
			fo = new FileOutputStream(file);
			fo.write(buffer.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (fo != null)
				fo.close();
		}
		return true;
	}

	public void newPage() {
		doc.newPage();
		// page = new PDPage();
		// doc.addPage(page);
		col = -1;
		row = 0;
	}

	public void newCard() {
		col++;
		if (col > (COL_NUM - 1)) {
			col = 0;
			row++;
			if (row > (ROW_NUM - 1))
				newPage();
		}
		float x1 = col * (WIDTH + MARGIN) + MARGIN;
		float x2 = (col + 1) * (WIDTH + MARGIN);
		float y2 = (row + 1) * (HEIGHT + MARGIN);
		float y1 = row * (HEIGHT + MARGIN) + MARGIN;
		cardBox = new Rectangle(x1, y1, x2, y2);
		try {
			PdfContentByte cb = writer.getDirectContent();
			cb.saveState();
			cardBox.setBorderWidth(1.2f);
			cardBox.setBorderColor(BaseColor.BLACK);
			cardBox.setBorder(Rectangle.BOX);
			cb.rectangle(cardBox);
			cb.stroke();
			cb.restoreState();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
