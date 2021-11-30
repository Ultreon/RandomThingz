package com.ultreon.randomthingz.common.internal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class RtCredits {
    private final List<Credit> credits = new ArrayList<>();

    public RtCredits() {

    }

    @Contract("_,_,_,_,_->this")
    public RtCredits add(String ingameName, String originalName, String person, String description, @Nullable URL url) {
        credits.add(new Credit(ingameName, originalName, person, description, url));
        return this;
    }

    public List<Credit> getCredits() {
        return Collections.unmodifiableList(credits);
    }

    @Getter
    @EqualsAndHashCode
    public static class Credit {
        private final String ingameName;
        private final String originalName;
        private final String person;
        private final String description;
        @Nullable
        private final URL url;

        public Credit(String ingameName, String originalName, String person, String description, @Nullable URL url) {
            this.ingameName = ingameName;
            this.originalName = originalName;
            this.person = person;
            this.description = description;
            this.url = url;
        }

        @Override
        public String toString() {
            if (url != null) {
                return "‘" + originalName + "’ by " + person + "\n" +
                        "  Ingame name: " + ingameName + "\n" +
                        "  URL: " + url +
                        "  Description: " + description;
            } else {
                return "‘" + originalName + "’ by " + person + "\n" +
                        "  Ingame name: " + ingameName + "\n" +
                        "  Description: " + description;
            }
        }
    }
}
