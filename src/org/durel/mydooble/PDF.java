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

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

public class PDF {
	PDDocument doc = null;
	PDPage page = null;
	PDRectangle cardBox = null;
	int nbItems = 0;

	private int col = 0;
	private int row = 0;
	final float WIDTH = (float) (6 * 72 / 2.54);
	final float HEIGHT = (float) (8.5 * 72 / 2.54);
	final float MARGIN = (float) (0.5 * 72 / 2.54);

	public PDF(int nbItems) throws IOException {
		doc = new PDDocument();
		this.nbItems = nbItems;
	}
	
	public boolean save(String file) throws IOException {
		try {
			doc.save(file);
		} catch (COSVisitorException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void newPage() {
		page = new PDPage();
		doc.addPage(page);
		col = 0;
		row = 0;
	}

	public void newCard() {
		if (page == null)
			newPage();
		else {
			col++;
			if (col > 2) {
				col = 0;
				row++;
				if (row > 2)
					newPage();
			}
		}
		cardBox = new PDRectangle();
		float x1 = col * (WIDTH + MARGIN) + MARGIN;
		float x2 = (col + 1) * (WIDTH + MARGIN);
		float y1 = (row + 1) * (HEIGHT + MARGIN);
		float y2 = row * (HEIGHT + MARGIN) + MARGIN;
		cardBox.setLowerLeftX(x1);
		cardBox.setLowerLeftY(y1);
		cardBox.setUpperRightX(x2);
		cardBox.setUpperRightY(y2);
		page.setArtBox(cardBox);
		try {
			PDPageContentStream contentStream = new PDPageContentStream(
					doc, page, true, false);
			contentStream.setStrokingColor( Color.BLACK );
			float x[] = new float[] {x1, x1, x2, x2};
			float y[] = new float[] {y1, y2, y2, y1};
			contentStream.drawPolygon(x, y);
			contentStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
