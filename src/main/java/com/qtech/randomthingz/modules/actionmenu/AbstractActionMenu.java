package com.qtech.randomthingz.modules.actionmenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractActionMenu {
    protected List<IActionMenuItem> items = new ArrayList<>();

    public List<? extends IActionMenuItem> getItems() {
        return Collections.unmodifiableList(this.items);
    }

    public <T extends IActionMenuItem> T addItem(T item) {
        this.items.add(item);
        return item;
    }
}
