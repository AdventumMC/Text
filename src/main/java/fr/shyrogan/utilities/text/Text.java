package fr.shyrogan.utilities.text;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Text is an utility tool allowing you to make complete messages.
 * They make manipulation of BaseComponent from Spigot easier.
 *
 * It is a modernized version Spigot's Chat Components.
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
     * Append a text to our current Text.
     *
     * @param text Text
     */
    public void append(Text text) {
        if(isSingle()) {
            builder = new ComponentBuilder(this.text).append(text.create());
        } else {
            builder.append(text.create());
        }
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
