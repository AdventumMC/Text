package fr.shyrogan.utilities.text.font;

/**
 * A simple class able to calculate informations about a String.
 *
 * @author Sébastien (Shyrogan)
 */
public final class Font {

    // Default's chat length.
    public static final int HALF_CHAT_LENGTH = 152;

    /**
     * Returns Minecraft's Text length.
     *
     * @param text Text
     * @param bold Bold
     * @param strikethrough Strikethrough
     * @return Text length
     */
    public static int getLength(String text, boolean bold, boolean strikethrough) {
        if(strikethrough) {
            return text.length() * FontInfos.STRIKETHOUGH_LENGTH;
        }

        int length = 0;
        for(char c : text.toCharArray()) {
            final FontInfos fi = FontInfos.getFontInfo(c);
            length += bold ? fi.getBoldLength() : fi.getLength();
        }
        return length;
    }

}
