package com.crazymoney.scanningwallet.widget;

public class HamburgerMenuItem {
    private String itemName;
    private Boolean isASeparator;

    public HamburgerMenuItem(String itemName, Boolean isASeparator) {
        this.itemName = itemName;
        this.isASeparator = isASeparator;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Boolean isASeparator() {
        return isASeparator;
    }

    public void setASeparator(Boolean ASeparator) {
        isASeparator = ASeparator;
    }

    @Override
    public String toString() {
        return "HamburgerMenuItem{" +
            "itemName='" + itemName + '\'' +
            ", isASeparator=" + isASeparator +
            '}';
    }
}
