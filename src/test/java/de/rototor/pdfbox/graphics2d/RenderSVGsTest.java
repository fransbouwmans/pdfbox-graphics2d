package de.rototor.pdfbox.graphics2d;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.*;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.junit.Test;
import org.w3c.dom.Document;

import java.awt.*;
import java.io.IOException;

public class RenderSVGsTest extends PdfBoxGraphics2DTestBase {

	@Test
	public void testSVGs() throws IOException {
		String svg_URI_input = RenderSVGsTest.class.getResource("barChart.svg").toString();
		String uri = svg_URI_input;

		// create the document
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
		Document document = f.createDocument(uri, RenderSVGsTest.class.getResourceAsStream("barChart.svg"));

		// create the GVT
		UserAgent userAgent = new UserAgentAdapter();
		DocumentLoader loader = new DocumentLoader(userAgent);
		BridgeContext bctx = new BridgeContext(userAgent, loader);
		bctx.setDynamicState(BridgeContext.STATIC);
		GVTBuilder builder = new GVTBuilder();
		final GraphicsNode gvtRoot = builder.build(bctx, document);

		this.exportGraphic("svg", "barChart", new GraphicsExporter() {
			@Override
			public void draw(Graphics2D gfx) throws IOException {
				gfx.scale(0.4, 0.4);
				gvtRoot.paint(gfx);
			}
		});

	}

}
