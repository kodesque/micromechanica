package micromechanica.util.foundation;

import com.google.common.io.Files;
import micromechanica.root.Main;
import micromechanica.util.ContentGroups;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentTranslation;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class T9n {

    public static Path LANG = Paths.get("src/main/resources/assets/micromechanica/lang");

    public interface ILocGroupValues {

        String getKey();
    }

    public enum EnumGroups {
        EXAMPLE("example", EnumSuffixes.TEXT);

        private String key;
        private EnumSuffixes suffix;

        EnumGroups(String key, EnumSuffixes suffix) {
            this.key = key;
            this.suffix = suffix;
        };

        public String getKey() {
            return this.key;
        }

        public String getSuffix() {
            return this.suffix.getKey();
        }

        public enum Example implements ILocGroupValues {
            EXAMPLE_VALUE("example_value");

            private String key;

            Example(String key) {
                this.key = key;
            };

            @Override
            public String getKey() {
                return this.key;
            }
        }
    }

    public enum EnumSuffixes {
        NAME("name"),
        TEXT("text");

        private String key;

        EnumSuffixes(String key) {
            this.key = key;
        };

        public String getKey() {
            return this.key;
        }
    }

    public static String simpleKey(String name, ContentGroups section) {

        TextComponentTranslation comp = new TextComponentTranslation(Main.MODID + "." + name);

        String fullkey = null;

        switch (section) {
            case CREATIVE_TAB: {
                fullkey = "tab." + name;
                break;
            }
            case BLOCKS: {
                fullkey = "tile." + name;
                break;
            }
            case ITEMS: {
                fullkey = "item." + name;
                break;
            }
        }

        fullkey = fullkey + ".name";

        if (!I18n.hasKey(comp.getKey())) {
            refreshFile(fullkey, section.getName());
        }

        return comp.getFormattedText();
    }

    public static TextComponentTranslation getComp(EnumGroups group, ILocGroupValues type) {

        TextComponentTranslation comp = new TextComponentTranslation(group.getKey() + "." + Main.MODID + "." + type.getKey() + "." + group.getSuffix());

        if (!I18n.hasKey(comp.getKey())) {
            refreshFile(comp.getKey(), group.getKey().toUpperCase());
        }

        return comp;
    }

    public static String getLoc(EnumGroups group, ILocGroupValues type) {

        TextComponentTranslation comp = new TextComponentTranslation(group.getKey() + "." + Main.MODID + "." + type.getKey() + "." + group.getSuffix());

        if (!I18n.hasKey(comp.getKey())) {
            refreshFile(comp.getKey(), group.getKey().toUpperCase());
        }

        return comp.getKey();
    }

    public static void refreshFile(String fullkey, String section) {

        File[] files = LANG.toFile().listFiles();

        if (files == null)
            return;

        for (File file : files) {

            try {

                List<String> lines = Files.readLines(file, StandardCharsets.UTF_8);

                String header = "#" + section;

                Integer start = null;

                for (int i = 0; i < lines.size(); i++) {
                    if (lines.get(i).trim().equalsIgnoreCase(header)) {
                        start = i;
                        break;
                    }
                }

                if (start == null)
                    return;

                int end = lines.size();

                for (int i = start + 1; i < lines.size(); i++) {
                    if (lines.get(i).startsWith("#")) {
                        end = i;
                        break;
                    }
                }

                Integer insert = null;

                for (int i = start + 1; i < end; i++) {
                    if (lines.get(i).trim().isEmpty()) {
                        insert = i;
                        break;
                    }
                }

                if (insert == null) {
                    insert = end;
                }

                lines.add(insert, fullkey + "=");

                String content = String.join(System.lineSeparator(), lines);

                Files.write(content, file, StandardCharsets.UTF_8);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}