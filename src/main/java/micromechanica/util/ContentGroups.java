package micromechanica.util;

public enum ContentGroups {

    /* For localization and creative tab sorting means. The order is important, do not change.*/

    CREATIVE_TAB("CREATIVE TAB", true),
    BLOCKS("BLOCKS"),
    ITEMS("ITEMS"),
    MACHINES("MACHINES", ContentGroups.ITEMS),
    TOOLTIPS("TOOLTIPS", true),
    MESSAGES("MESSAGES", true);

    private final String category;
    private final boolean locOnly;
    private final ContentGroups parentGroup;

    ContentGroups(String category) {
        this.category = category;
        this.locOnly = false;
        this.parentGroup = null;
    }

    ContentGroups(String category, boolean locOnly) {
        this.category = category;
        this.locOnly = locOnly;
        this.parentGroup = null;
    }

    ContentGroups(String category, ContentGroups parentGroup) {
        this.category = category;
        this.locOnly = false;
        this.parentGroup = parentGroup;
    }

    public String getName() {
        return this.category;
    }

    public boolean isReal() {
        return this.locOnly;
    }

}

//public enum EnumLangSection {
//    CREATIVE_TAB("CREATIVE TAB"),
//    BLOCKS("BLOCKS"),
//    ITEMS("ITEMS");
//
//    private String category;
//
//    EnumLangSection(String category) {
//        this.category = category;
//    }
//
//    public String getName() {
//        return this.category;
//    }
//
//}
