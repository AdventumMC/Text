package fr.shyrogan.utilities.text;

import com.sun.corba.se.impl.oa.toa.TOAFactory;
import fr.shyrogan.utilities.text.font.Font;
import fr.shyrogan.utilities.text.font.FontInfos;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Text is an utility tool allowing you to make complete messages.
 * They make manipulation of BaseComponent from Spigot easier.
 *
 * It is a modernized version Spigot's Font Components.
 *
 * It also allows some more complex informations like Centering/Length calculating.
 *
 * @author Sébastien (Shyrogan)
 */
public final class Text {

    /**
     * Creates an empty Text.
     *
     * @return Text
     */
    public static Text builder() {
        return new Text(new ComponentBuilder(""));
    }

    /**
     * Creates a simple Text.
     *
     * @param text Text
     * @return Text
     */
    public static Text of(String text) {
        return new Text(new TextComponent(text));
    }

    // A text in case if it's single.
    private BaseComponent text;

    // Builder in case there are multiple texts.
    private ComponentBuilder builder;

    /**
     * Creates a Text an empty text with a Builder.
     * These aren't public because I recommend going through
     * @see this#builder()
     *
     * @param builder Builder.
     */
    private Text(ComponentBuilder builder) {
        // We load our builder.
        this.builder = builder;
    }

    /**
     * Creates a Text with a single message.
     *
     * @param text Text.
     */
    private Text(BaseComponent text) {
        // We load our text
        this.text = text;
        this.text.setColor(ChatColor.RESET);
        this.text.setBold(false);
        this.text.setItalic(false);
        this.text.setStrikethrough(false);
        this.text.setUnderlined(false);
        this.text.setObfuscated(false);
    }

    public Text color(Collection<ChatColor> colors) {
        return color(colors.toArray(new ChatColor[] {}));
    }

    /**
     * Color and stylish a Text.
     *
     * @param color ChatColor
     */
    public Text color(ChatColor... color) {
        if(isSingle()) {
            for(ChatColor c : color) {
                if(c == ChatColor.STRIKETHROUGH) {
                    text.setStrikethrough(true);
                } else if (c == ChatColor.BOLD) {
                    text.setBold(true);
                } else if (c == ChatColor.ITALIC) {
                    text.setItalic(true);
                } else if (c == ChatColor.UNDERLINE) {
                    text.setUnderlined(true);
                } else if (c == ChatColor.MAGIC) {
                    text.setObfuscated(true);
                }
                text.setColor(c);
            }
        }
        return this;
    }

    /**
     * Append a text to our current Text.
     *
     * @param text Text
     */
    public Text append(Text text) {
        if(isSingle()) {
            builder = new ComponentBuilder(this.text).append(text.create());
        } else {
            builder.append(text.create());
        }
        return this;
    }

    /**
     * Returns calculated Text length using Font & FontInfos.
     * @see fr.shyrogan.utilities.text.font.FontInfos
     * @see Font
     *
     * @return Text length.
     */
    public int getLength() {
        if(isSingle()) {
            return Font.getLength(ChatColor.stripColor(text.toLegacyText()), isBold(), isStrikethrough());
        }
        int length = 0;
        for(BaseComponent comp : builder.create()) {
            length += Font.getLength(ChatColor.stripColor(comp.toLegacyText()), comp.isBold(), comp.isStrikethrough());
        }
        return length;
    }

    /**
     * Check if our text is strikethrough. This method only works with a single
     * Text.
     * @see this#isSingle()
     *
     * @return True if strikethrough.
     */
    public boolean isStrikethrough() {
        return isSingle() && text.isStrikethrough();
    }

    /**
     * Check if our text is obfuscated. This method only works with a single
     * Text.
     * @see this#isSingle()
     *
     * @return True if obfuscated.
     */
    public boolean isObfuscated() {
        return isSingle() && text.isObfuscated();
    }

    /**
     * Check if our text is italic. This method only works with a single
     * Text.
     * @see this#isSingle()
     *
     * @return True if italic.
     */
    public boolean isItalic() {
        return isSingle() && text.isItalic();
    }

    /**
     * Sets an event when the message is clicked.
     *
     * @param event Event
     */
    public Text setClickEvent(ClickEvent event) {
        if(isSingle()) {
            text.setClickEvent(event);
        }
        return this;
    }

    /**
     * Sets an event when the message is hovered.
     *
     * @param event Event
     */
    public Text setHoverEvent(HoverEvent event) {
        if(isSingle()) {
            text.setHoverEvent(event);
        }
        return this;
    }

    /**
     * Converts the text into a builder if it's not already and centers it
     * using specified character. It can also use a seperator to seperate the text
     * & the character centering it.
     */
    public Text center(char padding, Text seperator, ChatColor... colors) {
        List<ChatColor> listColors = Arrays.asList(colors);
        // Let's calculate the place we need to fill, represents only one side.
        int TO_FILL = Font.HALF_CHAT_LENGTH - (getLength() + seperator.getLength() * 2) / 2;
        int FILLED = 0;
        final FontInfos paddingInfos = FontInfos.getFontInfo(padding);
        final StringBuilder builder = new StringBuilder();

        while (FILLED < TO_FILL) {
            int charLength = listColors.contains(ChatColor.STRIKETHROUGH) ?
                    FontInfos.STRIKETHOUGH_LENGTH + 1 : listColors.contains(ChatColor.BOLD) ?
                    paddingInfos.getBoldLength() + 1 : paddingInfos.getLength() + 1;

            if(FILLED + charLength > TO_FILL) {
                FILLED = TO_FILL;
                break;
            }
            FILLED += charLength;
            builder.append(padding);
        }
        final Text fill = Text.of(builder.toString()).color(colors);

        return Text.builder()
                // Beginning
                .append(fill)
                .append(seperator)
                // Actual text (centered)
                .append(this)
                // End
                .append(seperator)
                .append(fill);
    }

    /**
     * Check if our text is bold. This method only works with a single
     * Text.
     * @see this#isSingle()
     *
     * @return True if bold.
     */
    public boolean isBold() {
        return isSingle() && text.isBold();
    }

    /**
     * Checks if our Text is composed of a single text.
     *
     * @return True if composed of a single text.
     */
    public boolean isSingle() {
        return !isMultiple();
    }

    /**
     * Checks if our Text is composed of a builder.
     *
     * @return True if multiple
     */
    public boolean isMultiple() {
        return builder != null;
    }

    /**
     * Creates a Spigot chat component of our text.
     *
     * @return BaseComponent
     */
    public BaseComponent[] create() {
        return isSingle() ? new BaseComponent[] { text } : builder.create();
    }

}
