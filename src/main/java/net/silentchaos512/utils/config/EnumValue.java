package net.silentchaos512.utils.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.ConfigSpec;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Consumer;

public class EnumValue<E extends Enum<E>> extends ConfigValue<E> {
    private final E defaultValue;

    EnumValue(E defaultValue, ConfigSpecWrapper wrapper, String path, Consumer<ConfigSpec> handleSpec, Consumer<CommentedConfig> handleConfig) {
        super(wrapper, path, handleSpec, handleConfig);
        this.defaultValue = defaultValue;
    }

    @Override
    public E get() {
        // Enums do not load correctly, so we save and load them as strings.
        String name = config.get(path);
        for (E e : defaultValue.getDeclaringClass().getEnumConstants()) {
            if (e.name().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return defaultValue;
    }

    /**
     * Create a "valid values" comment with all values of the enum. Also see {@link
     * #validValuesComment(Set)}.
     *
     * @param clazz The enum class
     * @param <E>   The enum class
     * @return A comment listing all enum constants as valid values.
     */
    public static <E extends Enum<E>> String allValuesComment(Class<E> clazz) {
        return validValuesComment(EnumSet.allOf(clazz));
    }

    /**
     * Create a "valid values" comment with the given enum values. Also see {@link
     * #allValuesComment(Class)}.
     *
     * @param validValues The valid values
     * @param <E>         The enum class
     * @return A comment listing the given enum constants as valid values.
     */
    public static <E extends Enum<E>> String validValuesComment(Set<E> validValues) {
        StringBuilder builder = new StringBuilder();
        validValues.forEach(e -> {
            builder.append(builder.length() == 0 ? "Valid values: " : ", ");
            builder.append(e.name());
        });
        return builder.toString();
    }
}
