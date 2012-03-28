package cn;
import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xhtmlrenderer.pdf.*;
import org.xhtmlrenderer.pdf.util.*;

import com.lowagie.text.pdf.*;

/*
 * {{{ header & license
 * Copyright (c) 2007 Wisconsin Court System
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * }}}
 */

/**
 *
 */
public class Test {

    /**
     * @param args
     */
    public static void main1(String[] args) {
        BreakIterator it = BreakIterator.getLineInstance(Locale.SIMPLIFIED_CHINESE);
        final String text = "【你好】，今天是我们的测试日子。Hello world是在计算机教学"
                + "中经常使用的例子。现在主流的计算机语言有15种，每年计算机的毕业生有5,6645人。\n从前有三只小猪，"
                + "他们都要起房子，老大说：“我就起个草房子吧。”；老二说：“我要起个木头房子。”老三说……。今年的猪肉产量10顿，也就是10,000Kg。好像是的，是这么多。";
        it.setText(text);
        int start = it.first();
        for (int end = it.next(); end != BreakIterator.DONE; start = end, end = it.next()) {
            System.out.println(text.substring(start, end));
        }

    }

    public static void main(String[] args) throws Exception {
        ITextRenderer render = new ITextRenderer();
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(Test.class.getResourceAsStream("text.html"));
        render.setPDFVersion(PdfWriter.VERSION_1_4);
        render.getFontResolver().addFont("/usr/share/fonts/truetype/wqy/wqy-zenhei.ttc", "wqy",
                BaseFont.IDENTITY_H, BaseFont.EMBEDDED, null);
        render.getFontResolver().addFont("/home/yaochunlin/.fonts/HiraginoSansGB-W3.ttf", "dq",
                BaseFont.IDENTITY_H, BaseFont.EMBEDDED, null);
        render.setListener(new XHtmlMetaToPdfInfoAdapter(doc));
        render.setDocument(doc, null);
        render.layout();
        final File out = File.createTempFile("text", ".pdf");
        render.createPDF(new FileOutputStream(out), true);
        Desktop.getDesktop().open(out);
    }
}
