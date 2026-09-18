package micromechanica.api;

public enum EnumLangSection {
    CREATIVE_TAB("CREATIVE TAB"),
    BLOCKS("BLOCKS"),
    ITEMS("ITEMS");

    private String category;

    EnumLangSection(String category) {
        this.category = category;
    }

    public String getName() {
        return this.category;
    }

    public int index() {
        return this.ordinal();
    }

}
