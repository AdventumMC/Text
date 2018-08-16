package fr.shyrogan.utilities.text;

import fr.shyrogan.utilities.text.font.Font;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

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
                    return this;
                } else if (c == ChatColor.ITALIC) {
                    text.setItalic(true);
                    return this;
                } else if (c == ChatColor.UNDERLINE) {
                    text.setUnderlined(true);
                    return this;
                } else if (c == ChatColor.MAGIC) {
                    text.setObfuscated(true);
                    return this;
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
