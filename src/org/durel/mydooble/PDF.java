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

import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class PDF {
	PDDocument doc = null;
	PDPage page = null;
	PDRectangle cardBox = null;

	private int col = 0;
	private int row = 0;
	private float WIDTH = 6;
	private float HEIGHT = 9;
	private float MARGIN = (float) 0.5;

	public PDF() throws IOException {
		doc = new PDDocument();
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
		cardBox.setLowerLeftX(col * (WIDTH + MARGIN) + MARGIN);
		cardBox.setLowerLeftY((row + 1) * (HEIGHT + MARGIN) + MARGIN);
		cardBox.setUpperRightX((row + 1) * (WIDTH + MARGIN) + MARGIN);
		cardBox.setUpperRightY(col * (HEIGHT + MARGIN) + MARGIN);
		page.setArtBox(cardBox);
	}
}
