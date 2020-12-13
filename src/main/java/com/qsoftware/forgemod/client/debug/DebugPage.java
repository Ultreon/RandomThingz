package com.qsoftware.forgemod.client.debug;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class DebugPage {
    private String id;
    private static final HashMap<String, DebugPage> pages = new HashMap<>();
    protected Minecraft mc = Minecraft.getInstance();

    public static Collection<DebugPage> getPages() {
        return pages.values();
    }

    public DebugPage() {

    }

    public void setId(String name) {

        // String to be scanned to find the pattern.
        String pattern = "[A-Z0-9_]*"; // 1.0-alpha4 // 5.4-release-7

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(name);
        if (m.find()) {
            if (pages.containsKey(name)) {
                throw new IllegalArgumentException("Page with id " + name + " already added");
            }
            this.id = name;
            pages.put(name, this);
        } else {
            throw new IllegalArgumentException("Invalid version,");
        }
    }

    @NotNull
    public abstract DebugText generateText();

    public String getId() {
        return id;
    }
}
