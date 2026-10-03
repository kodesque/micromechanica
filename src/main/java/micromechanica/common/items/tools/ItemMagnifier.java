package micromechanica.common.items.tools;

import micromechanica.common.templates.ModMachineBase;

public class ItemMagnifier extends ModMachineBase {

    public static int hoverTimer = 0;
    public static boolean drawing = false;
    public static final String[] dots = {".", "..", "..."};

    public ItemMagnifier(String name) {
        super(name, false);
        this.setSpecialCase();
    }

}
