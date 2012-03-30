/*
 * {{{ header & license
 * Copyright (c) 2004, 2005 Joshua Marinacci
 * Copyright (c) 2006 Wisconsin Court System
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * }}}
 */
package org.xhtmlrenderer.extend;

import java.awt.*;

import org.xhtmlrenderer.css.constants.*;
import org.xhtmlrenderer.render.*;

public interface TextRenderer {
    public void setup(FontContext context);

    public void drawString(OutputDevice outputDevice, String string, float x, float y, float[] kernings);
    public void drawString(
            OutputDevice outputDevice, String string, float x, float y, JustificationInfo info, float[] kernings);
    
    public void drawGlyphVector(OutputDevice outputDevice, FSGlyphVector vector, float x, float y);
    
    public FSGlyphVector getGlyphVector(OutputDevice outputDevice, FSFont font, String string);
    
    public float[] getGlyphPositions(OutputDevice outputDevice, FSFont font, FSGlyphVector fsGlyphVector);
    public Rectangle getGlyphBounds(OutputDevice outputDevice, FSFont font, FSGlyphVector fsGlyphVector, int index, float x, float y);

    public FSFontMetrics getFSFontMetrics(
            FontContext context, FSFont font, String string );

    public int getWidth(FontContext context, FSFont font, String string);

    public void setFontScale(float scale);

    public float getFontScale();

    /**
     * Set the smoothing threashold. This is a font size above which
     * all text will be anti-aliased. Text below this size will not be antialiased. 
     * Set to -1 for no antialiasing. 
     * Set to 0 for all antialising.
     * Else, set to the threshold font size. does not take font scaling
     * into account.
     */
    public void setSmoothingThreshold(float fontsize);

    public int getSmoothingLevel();

    /**
     * @deprecated no-op, will be removed in a future release. Anti-aliasing is now controlled via the smoothing
     * threshhold.
     * @param level no-op
     */
    public void setSmoothingLevel(int level);

    /**
     * @param fontContext
     * @param fsFont
     * @param substring
     * @param fsKerning TODO
     * @return
     */
    public float[] getKernings(FontContext fontContext, FSFont fsFont, String substring, IdentValue fsKerning);
    
    /**
     * @param fontContext
     * @param fsFont
     * @param substring
     * @param fsKerning TODO
     * @return
     */
    public float getFirstCharOffset(FontContext fontContext, FSFont fsFont, String substring, IdentValue fsKerning);
}

