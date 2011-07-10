package org.durel.mydooble;

import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfContentByte;

public class TextItem extends Item {

	private String name;

	public TextItem(String item) {
		super();
		name = item;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public void toPDF(PDF out, int i) {
		COLS = 1;
		super.toPDF(out, i);
		
		float x = bx + (c * (w + m)) + m + w / 2;
		float y = by + (r * (h + m)) + m + h / 2;
		
		PdfContentByte cb = out.writer.getDirectContent();
		cb.beginText();
		cb.moveText(x, y);
		cb.showTextAligned(Element.ALIGN_CENTER, name, x, y, 0);
		cb.endText();
	}

}
