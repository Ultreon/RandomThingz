package com.ultreon.randomthingz.common.item;

import com.ultreon.randomthingz.item.group.*;
import lombok.experimental.UtilityClass;

/**
 * ModItemGroups collection.
 *
 * @author Qboi123
 */
@UtilityClass
public class ModItemGroups {
    public static final OresItemGroup ORES = OresItemGroup.instance;
    public static final MetalCraftablesItemGroup METAL_CRAFTABLES = MetalCraftablesItemGroup.instance;
    public static final ToolsItemGroup TOOLS = ToolsItemGroup.instance;
    public static final MachinesItemGroup MACHINES = MachinesItemGroup.instance;
    public static final SpecialsItemGroup SPECIALS = SpecialsItemGroup.instance;
    public static final DungeonsItemGroup DUNGEONS = DungeonsItemGroup.instance;
    public static final BookshelvesItemGroup BOOKSHELVES = BookshelvesItemGroup.instance;
    public static final OverpoweredItemGroup OVERPOWERED = OverpoweredItemGroup.instance;
}