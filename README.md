# Text

### Informations

Text is a public library to create, manipulate and modernize Spigot Chat Component.
It also adds some improvements as **length/width calculations** and **centering**.

> Keep in mind, this is a **work-in-progress** project and there are improvements to do.

### Usage

##### Your first Text.
Creating your first text is pretty simple. Just go invoke ``Text.of(String)`` method.  
In this example I will create a text sending "My first text" in red and bold.

````
player.spigot().send(
    Text.of("My first text")
    .color(ChatColor.RED, ChatColor.BOLD)
    .create()
);
````

##### Your first centered Text.

Now it goes a little tricky but still very quick. We're going to center "Thanks, Text!" in blue with space paddings.  

Here's an example of how it works. Here ``P`` is the padding, ``_`` our seperator and ``TEXT`` our text.  
``Text.of("TEXT").center('P', Text.of("_"))`` will result 
``PPPPPPPPPPP_TEXT_PPPPPPPPPPP``  

Interesting uh? Now let's create our "Thanks, Text!"

````
player.spigot().send(
    Text.of("Thanks, Text!")
    .color(ChatColor.BLUE)
    // I just want space to center, nothing else.
    .center(' ', Text.of(" "))
    .create()
);
````
Cooool!

##### Using multiple Texts using builder.

We know how to create a Text and manipulate it but what if I want to create a single Text with multiple little Texts using their own ClickEvent/HoverEvent ?  
Simple, simple, simple! We need to use ``append(Text)`` method our create a builder using ``Text.builder()`` *(Just creates an empty Text)*.

*I'll complete this README later with screenshot but this still usable. Please if you use this library, credit our work @Shyrogan or @AdventumMC.*