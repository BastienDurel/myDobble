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
import java.io.OutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class PDF {
	Document doc = null;
	PdfWriter writer = null;
	ByteArrayOutputStream buffer = null;
	//PDPage page = null;
	Rectangle cardBox = null;
	int nbItems = 0;

	private int col = 0;
	private int row = 0;
	final float WIDTH = (float) (6 * 72 / 2.54);
	final float HEIGHT = (float) (8.5 * 72 / 2.54);
	final float MARGIN = (float) (0.5 * 72 / 2.54);

	public PDF(int nbItems) throws IOException, DocumentException {
		buffer = new ByteArrayOutputStream();
		doc = new Document(PageSize.A4);
		writer = PdfWriter.getInstance(doc, buffer);
		this.nbItems = nbItems;
		doc.open();
	}
	
	public boolean save(String file) throws IOException {
		try {
			doc.close();
			writer.flush();
			FileOutputStream fo = new FileOutputStream(file);
			fo.write(buffer.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void newPage() {
		doc.newPage();
		//page = new PDPage();
		//doc.addPage(page);
		col = 0;
		row = 0;
	}

	public void newCard() {
			col++;
			if (col > 2) {
				col = 0;
				row++;
				if (row > 2)
					newPage();
			}
		float x1 = col * (WIDTH + MARGIN) + MARGIN;
		float x2 = (col + 1) * (WIDTH + MARGIN);
		float y2 = (row + 1) * (HEIGHT + MARGIN);
		float y1 = row * (HEIGHT + MARGIN) + MARGIN;
		cardBox = new Rectangle(x1, y1, x2, y2);
		//page.setArtBox(cardBox);
		try {
			PdfContentByte cb = writer.getDirectContent();
			//cb.saveState();
			cb.setColorStroke(BaseColor.BLACK);
			cb.setColorFill(BaseColor.RED);
			cb.rectangle(cardBox);
			//cb.restoreState();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
