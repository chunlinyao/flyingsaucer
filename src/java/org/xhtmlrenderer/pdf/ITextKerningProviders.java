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
package org.xhtmlrenderer.pdf;

import java.util.*;

import org.xhtmlrenderer.css.constants.*;
import org.xhtmlrenderer.extend.*;
import org.xhtmlrenderer.render.*;

import com.lowagie.text.pdf.*;

/**
 *
 */
public class ITextKerningProviders {

    static public KerningProvider getProvider(FontContext context, FSFont fsFont, IdentValue ident) {
        if (ident == IdentValue.DEFAULT) {
            return new DefaultKerningProvider(
                    ((ITextFSFont) fsFont).getFontDescription().getFont(), fsFont.getSize2D());
        } else if (ident == IdentValue.CJK) {
            return new CJKKerningProvider(((ITextFSFont) fsFont).getFontDescription().getFont(),
                    fsFont.getSize2D());
        } else if (ident == IdentValue.CJK_DANGLE) {
            return new CJKDangleKerningProvider(((ITextFSFont) fsFont).getFontDescription()
                    .getFont(), fsFont.getSize2D());
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static class DefaultKerningProvider implements KerningProvider {
        private BaseFont font;

        private float fontSize;

        private DefaultKerningProvider(BaseFont font, float fontSize) {
            super();
            this.font = font;
            this.fontSize = fontSize;
        }

        /* (non-Javadoc)
         * @see org.xhtmlrenderer.extend.KerningProvider#getKernings(java.lang.String)
         */
        public float[] getKernings(String substring) {
            float[] kernings = new float[substring.length() + 1];
            Arrays.fill(kernings, 0f);
            for (int i = 0; i < substring.length() - 1; i++) {
                kernings[i] = font.getKerning(substring.codePointAt(i), substring
                        .codePointAt(i + 1))
                        / 1000 * fontSize;
            }
            kernings[substring.length() - 1] = 0;
            return kernings;
        }

        /* (non-Javadoc)
         * @see org.xhtmlrenderer.extend.KerningProvider#getFirstCharOffset(java.lang.String)
         */
        public float getFirstCharOffset(String substring) {
            return 0f;
        }

    }

    public static class CJKKerningProvider implements KerningProvider {
        private float fontSize;

        private CJKKerningProvider(BaseFont font, float fontSize) {
            super();
            this.fontSize = fontSize;
        }

        /* (non-Javadoc)
         * @see org.xhtmlrenderer.extend.KerningProvider#getKernings(java.lang.String)
         */
        public float[] getKernings(String substring) {
            float[] kernings = new float[substring.length() + 1];
            Arrays.fill(kernings, 0f);
            for (int i = 0; i < substring.length() - 1; i++) {
                if (substring.charAt(i) == '：') {
                    if (substring.charAt(i + 1) == '“') {
                        kernings[i] = -500f / 1000 * fontSize;
                    }
                }
                if (substring.charAt(i) == '。') {
                    if (substring.charAt(i + 1) == '”') {
                        kernings[i] = -500f / 1000 * fontSize;
                    }
                }
            }
            char lastchar = substring.charAt(substring.length() - 1);
            if ("。，”）』】》’".indexOf(lastchar) >= 0) {
                kernings[substring.length() - 1] = -500f / 1000 * fontSize;
            }
            return kernings;
        }

        /* (non-Javadoc)
         * @see org.xhtmlrenderer.extend.KerningProvider#getFirstCharOffset(java.lang.String)
         */
        public float getFirstCharOffset(String substring) {
            int firstChar = substring.codePointAt(0);
            if ("“（『【‘《".indexOf(firstChar) < 0) {
                return 0;
            } else {
                return -500f / 1000 * fontSize;
            }
        }

    }

    public static class CJKDangleKerningProvider implements KerningProvider {
        private float fontSize;

        private CJKDangleKerningProvider(BaseFont font, float fontSize) {
            super();
            this.fontSize = fontSize;
        }

        /* (non-Javadoc)
         * @see org.xhtmlrenderer.extend.KerningProvider#getKernings(java.lang.String)
         */
        public float[] getKernings(String substring) {
            float[] kernings = new float[substring.length() + 1];
            Arrays.fill(kernings, 0f);
            for (int i = 0; i < substring.length() - 1; i++) {
                if (substring.charAt(i) == '：') {
                    if (substring.charAt(i + 1) == '“') {
                        kernings[i] = -500f / 1000 * fontSize;
                    }
                }
                if (substring.charAt(i) == '。') {
                    if (substring.charAt(i + 1) == '”') {
                        kernings[i] = -500f / 1000 * fontSize;
                    }
                }
            }
            char lastchar = substring.charAt(substring.length() - 1);
            if ("”）』】》’".indexOf(lastchar) >= 0) {
                kernings[substring.length() - 1] = -500f / 1000 * fontSize;
            }
            if ("。，".indexOf(lastchar) >= 0) {
                kernings[substring.length() - 1] = -fontSize;
            }
            return kernings;
        }

        /* (non-Javadoc)
         * @see org.xhtmlrenderer.extend.KerningProvider#getFirstCharOffset(java.lang.String)
         */
        public float getFirstCharOffset(String substring) {
            int firstChar = substring.codePointAt(0);
            if ("“（『【‘《".indexOf(firstChar) < 0) {
                return 0;
            } else {
                return -500f / 1000 * fontSize;
            }
        }
    }
}
