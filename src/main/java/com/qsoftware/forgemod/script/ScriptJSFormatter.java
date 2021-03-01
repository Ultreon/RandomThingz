package com.qsoftware.forgemod.script;

import com.google.common.annotations.Beta;
import com.qsoftware.forgemod.util.StringTransformer;
import net.minecraft.util.text.TextFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Beta
public class ScriptJSFormatter {
    private static final Logger LOGGER = LogManager.getLogger("QFM:ScriptJS:Formatter");

    //////////////////////
    //     KEYWORDS     //
    //////////////////////
    private static final StringTransformer keywords = (src) -> {
        Matcher out = Pattern.compile("(abstract|async|await|boolean|break|case|catch|char|class|const|continue|debugger|default|delete|do|else|enum|export|extends|final|finally|for|function|goto|if|implements|in|instanceof|interface|let|native|new|null|of|private|protected|public|return|static|super|switch|synchronized|this|throw|throws|transient|try|typeof|var|void|volatile|while|with|true|false|prototype|yield)")
                .matcher(src);
//        try {
//            if (out.group(1) == null || out.group(1).equals("")) {
//                return src;
//            }
//        } catch (IllegalStateException e) {
//            return src;
//        }
        return src.replaceAll("(abstract|async|await|boolean|break|case|catch|char|class|const|continue|debugger|default|delete|do|else|enum|export|extends|final|finally|for|function|goto|if|implements|in|instanceof|interface|let|native|new|null|of|private|protected|public|return|static|super|switch|synchronized|this|throw|throws|transient|try|typeof|var|void|volatile|while|with|true|false|prototype|yield)", TextFormatting.LIGHT_PURPLE + "$1" + TextFormatting.RESET);
    };

    ////////////////////////////
    //     CORE VARIABLES     //
    ////////////////////////////
    private static final StringTransformer coreVars = (src) -> {
        Matcher out = Pattern.compile("(player|script|server)")
                .matcher(src);
//        try {
//            if (out.group(1) == null || out.group(1).equals("")) {
//                return src;
//            }
//        } catch (IllegalStateException e) {
//            return src;
//        }
        return src.replaceAll("(player|script|server)", TextFormatting.DARK_PURPLE + "$1" + TextFormatting.RESET);
    };

    ////////////////////////////
    //     CORE FUNCTIONS     //
    ////////////////////////////
    private static final StringTransformer coreFunctions = (src) -> {
        Matcher out = Pattern.compile("(import|run|put|get|command|console|world)")
                .matcher(src);
//        try {
//            if (out.group(1) == null || out.group(1).equals("")) {
//                return src;
//            }
//        } catch (IllegalStateException e) {
//            return src;
//        }
        return src.replaceAll("(import|run|put|get|command|console|world)", TextFormatting.DARK_AQUA + "$1" + TextFormatting.RESET);
    };

    //////////////////////////
    //     CORE CLASSES     //
    //////////////////////////
    private static final StringTransformer coreClasses = (src) -> {
        Matcher out = Pattern.compile("(byte|short|int|long|double|float|string|char|either|lazy|map|set|table|list|optional)")
                .matcher(src);
//        try {
//            if (out.group(1) == null || out.group(1).equals("")) {
//                return src;
//            }
//        } catch (IllegalStateException e) {
//            return src;
//        }
        return src.replaceAll("(byte|short|int|long|double|float|string|char|either|lazy|map|set|table|list|optional)", TextFormatting.BLUE + "$1" + TextFormatting.RESET);
    };

    //////////////////////
    //     FUNCTION     //
    //////////////////////
    private static final StringTransformer function = (src) -> {
        Matcher out = Pattern.compile("function ([a-zA-Z_]*[a-zA-Z0-9_]*)")
                .matcher(src);
//        try {
//            if (out.group(1) == null || out.group(1).equals("")) {
//                return src;
//            }
//        } catch (IllegalStateException e) {
//            return src;
//        }
        return src.replaceAll("function ([a-zA-Z_]*[a-zA-Z0-9_]*)", "function " + TextFormatting.AQUA + "$1" + TextFormatting.RESET);
    };

    ///////////////////
    //     CLASS     //
    ///////////////////
    private static final StringTransformer clazz = (src) -> {
        Matcher out = Pattern.compile("class ([a-zA-Z_]*[a-zA-Z0-9_]*)")
                .matcher(src);
//        try {
//            if (out.group(1) == null || out.group(1).equals("")) {
//                return src;
//            }
//        } catch (IllegalStateException e) {
//            return src;
//        }
        return src.replaceAll("class ([a-zA-Z_]*[a-zA-Z0-9_]*)", "class " + TextFormatting.YELLOW + "$1" + TextFormatting.RESET);
    };

    ///////////////////////
    //     NEW (...)     //
    ///////////////////////
    private static final StringTransformer _new = (src) -> {
        Matcher out = Pattern.compile("new ([a-zA-Z_]*[a-zA-Z0-9_]*)")
                .matcher(src);
//        try {
//            if (out.group(1) == null || out.group(1).equals("")) {
//                return src;
//            }
//        } catch (IllegalStateException e) {
//            return src;
//        }
        return src.replaceAll("new ([a-zA-Z_]*[a-zA-Z0-9_]*)", "new " + TextFormatting.YELLOW + "$1" + TextFormatting.RESET);
    };

    ///////////////////////
    //     OPERATORS     //
    ///////////////////////
    private static final StringTransformer operators = (src) -> {
        Matcher out = Pattern.compile("(\\+=|\\*=|/=|-=|%=|=|\\||\\+|-|\\*|/|%|\\*\\*|\\\\|&|->|;|<=|>=|==|!=|[\\-+*/%<>])")
                .matcher(src);
//        try {
//            if (out.group(1) == null || out.group(1).equals("")) {
//                return src;
//            }
//        } catch (IllegalStateException e) {
//            return src;
//        }
        return src.replaceAll("(\\+=|\\*=|/=|-=|%=|=|\\||\\+|-|\\*|/|%|\\*\\*|\\\\|&|->|;|<=|>=|==|!=|[\\-+*/%<>])", TextFormatting.GRAY + "$1" + TextFormatting.RESET);
    };

    /////////////////////
    //     NUMBERS     //
    /////////////////////
    private static final StringTransformer numbers = (src) -> {
        Matcher out = Pattern.compile("(\\s*|\\+=|\\*=|/=|-=|%=|=|\\||\\+|-|\\*|/|%|\\*\\*|\\\\|&|->|<=|>=|==|!=|[;{}()\\[\\],*/\\-+\\\\'\"<>]|^)([0-9]*)")
                .matcher(src);
//        try {
//            if (out.group(1) == null || out.group(1).equals("")) {
//                return src;
//            }
//        } catch (IllegalStateException e) {
//            return src;
//        }
        return src.replaceAll("(\\s*|\\+=|\\*=|/=|-=|%=|=|\\||\\+|-|\\*|/|%|\\*\\*|\\\\|&|->|<=|>=|==|!=|[;{}()\\[\\],*/\\-+\\\\'\"<>]|^)([0-9]*)", "$1" + TextFormatting.GOLD + "$2" + TextFormatting.RESET);
    };

    /////////////////////
    //     STRINGS     //
    /////////////////////
    private static final StringTransformer strings = (src) -> {
        Matcher src1 = Pattern.compile("(\"((?:[^\"]|(\\\\\"))*)\")")
                .matcher(src);
        src1.reset();
        StringBuffer result = new StringBuffer();
        while (src1.find()) {
            StringBuffer buffer = new StringBuffer();
            src1.appendReplacement(buffer, "$1");
            String s = buffer.toString();
            s = s.replaceAll("(?i)\u00a7[0-9A-FK-ORa-fk-o]", "");
            s = s.replaceAll("(\"((?:[^\"]|(\\\\\"))*)\")", TextFormatting.GREEN + "$1" + TextFormatting.RESET);
            result.append(s);
        }
        src1.appendTail(result);
        return result.toString();
    };

    private ScriptJSFormatter() {

    }

    public String format(String s) {
        LOGGER.debug("--------------------------");
        LOGGER.debug(s = numbers.replace(s));
        LOGGER.debug(s = function.replace(s));
        LOGGER.debug(s = clazz.replace(s));
        LOGGER.debug(s = keywords.replace(s));
        LOGGER.debug(s = coreVars.replace(s));
        LOGGER.debug(s = coreFunctions.replace(s));
        LOGGER.debug(s = coreClasses.replace(s));
        LOGGER.debug(s = operators.replace(s));
        LOGGER.debug(s = strings.replace(s));
        LOGGER.debug("--------------------------");
        return s;
    }

    public static ScriptJSFormatter instance() {
        return new ScriptJSFormatter();
    }
}
