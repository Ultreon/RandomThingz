package com.qtech.forgemod.common;

import lombok.Getter;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

public class BetterItemGroup extends ItemGroup {
    private final ItemStack icon;
    @Getter private final ResourceLocation location;
    @Getter private final String labelName;

    public BetterItemGroup(ResourceLocation location, ItemStack icon) {
        super(location.getNamespace() + "_" + location.getPath().replaceAll("[/.]", "_"));
        this.icon = icon;
        this.location = location;
        this.labelName = location.getPath().replaceAll("[/.]", "_");
    }

    public BetterItemGroup(ResourceLocation location, IItemProvider itemProvider) {
        super(location.getNamespace() + "_" + location.getPath().replaceAll("[/.]", "_"));
        this.icon = new ItemStack(itemProvider);
        this.location = location;
        this.labelName = location.getPath().replaceAll("[/.]", "_");
    }

    public String getModID() {
        return this.location.getNamespace();
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return this.icon;
    }

    @SuppressWarnings("unchecked")
    protected  <T extends Enum<T>> void addEnumValuesStack(NonNullList<ItemStack> items, Class<T> enum_, Function<T, ItemStack> function) {
        try {
            Method method = enum_.getDeclaredMethod("values");
            Collection<T> values = (Collection<T>) method.invoke(null);

            values.stream().map(function).filter(Objects::nonNull).forEach(items::add);
        } catch (Throwable t) {

            CrashReport crashreport = CrashReport.createCrashReport(t, "Enum has no static values() method.");
            CrashReportCategory crashreportcategory = crashreport.createCategory("Enum being looped in QForgeMod Item Group.");
            crashreportcategory.addDetail("Enum Class Name", enum_::toString);
            throw new ReportedException(crashreport);
        }
    }
    @SuppressWarnings("unchecked")
    protected  <T extends Enum<T>> void addEnumValuesStackCollection(NonNullList<ItemStack> items, Class<T> enum_, Function<T, Collection<ItemStack>> function) {
        try {
            Method method = enum_.getDeclaredMethod("values");
            Collection<T> values = (Collection<T>) method.invoke(null);

            values.stream().map(function).filter(Objects::nonNull).forEach(items::addAll);
        } catch (Throwable t) {

            CrashReport crashreport = CrashReport.createCrashReport(t, "Enum has no static values() method.");
            CrashReportCategory crashreportcategory = crashreport.createCategory("Enum being looped in QForgeMod Item Group.");
            crashreportcategory.addDetail("Enum Class Name", enum_::toString);
            throw new ReportedException(crashreport);
        }
    }
    @SuppressWarnings("unchecked")
    protected  <T extends Enum<T>> void addEnumValuesCollection(NonNullList<ItemStack> items, Class<T> enum_, Function<T, Collection<IItemProvider>> function) {
        try {
            Method method = enum_.getDeclaredMethod("values");
            Collection<T> values = (Collection<T>) method.invoke(null);

            values.forEach(t -> function.apply(t).stream().filter(Objects::nonNull).forEach((item) -> items.add(new ItemStack(item))));
        } catch (Throwable t) {

            CrashReport crashreport = CrashReport.createCrashReport(t, "Enum has no static values() method.");
            CrashReportCategory crashreportcategory = crashreport.createCategory("Enum being looped in QForgeMod Item Group.");
            crashreportcategory.addDetail("Enum Class Name", enum_::toString);
            throw new ReportedException(crashreport);
        }
    }
    @SuppressWarnings("unchecked")
    protected  <T extends Enum<T>> void addEnumValues(NonNullList<ItemStack> items, Class<T> enum_, Function<T, IItemProvider> function) {
        try {
            Method method = enum_.getDeclaredMethod("values");
            Collection<T> values = (Collection<T>) method.invoke(null);

            values.stream().map(t -> function.apply(t) != null ? new ItemStack(function.apply(t)) : null).filter(Objects::nonNull).forEach(items::add);
        } catch (Throwable t) {

            CrashReport crashreport = CrashReport.createCrashReport(t, "Enum has no static values() method.");
            CrashReportCategory crashreportcategory = crashreport.createCategory("Enum being looped in QForgeMod Item Group.");
            crashreportcategory.addDetail("Enum Class Name", enum_::toString);
            throw new ReportedException(crashreport);
        }
    }
}
