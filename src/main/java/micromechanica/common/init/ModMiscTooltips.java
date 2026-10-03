package micromechanica.common.init;

import micromechanica.common.templates.ModItemBase;
import net.minecraft.item.Item;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModMiscTooltips {

    public static void initTooltips() {
        addInfoToTooltip(ModItems.DRY_MILK,
                new TextComponentTranslation("tooltip.micromechanica.dry_milk.text")
                        .setStyle(new Style()
                                .setColor(TextFormatting.GRAY)
                                .setItalic(true))
                        .getFormattedText());

    }

    private static void addInfoToTooltip(Item item, String... list) {
        List<String> tooltipInfo = new ArrayList<>(Arrays.asList(list));

        ((ModItemBase)item).addInfoToTooltip(tooltipInfo);
    }
}
