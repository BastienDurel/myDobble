package org.durel.mydooble;

import java.awt.Color;
import java.io.IOException;

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
		super.toPDF(out, i);
		/*
		float k = (float) (out.cardBox.getHeight() / ((float)out.nbItems + 1.5));
		float w = out.cardBox.getWidth();
		float x = out.cardBox.getLowerLeftX();
		float y = out.cardBox.getLowerLeftY();
		final int fontSize = 10;

		try {
			PDPageContentStream contentStream = new PDPageContentStream(
					out.doc, out.page, true, false);
			contentStream.setStrokingColor(Color.RED);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSize);
			float sw = (float) (PDType1Font.TIMES_ROMAN.getStringWidth(name) / 1000 * fontSize);
			log.info("w: " + w + " - sw: " + sw);
			contentStream.beginText();
			contentStream.moveTextPositionByAmount(x + w / 2 - sw / 2, y + (k * (i + 1)));
			contentStream.drawString(name);
			contentStream.endText();
			contentStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

}
