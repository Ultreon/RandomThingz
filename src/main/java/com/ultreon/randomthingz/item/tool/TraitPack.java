package com.ultreon.randomthingz.item.tool;

import com.ultreon.randomthingz.item.tool.trait.AbstractTrait;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;

@Getter
public class TraitPack {
    public static final TraitPack EMPTY = new TraitPack();

    AbstractTrait[] sword = new AbstractTrait[0];
    AbstractTrait[] axe = new AbstractTrait[0];
    AbstractTrait[] pickaxe = new AbstractTrait[0];
    AbstractTrait[] shovel = new AbstractTrait[0];
    AbstractTrait[] hoe = new AbstractTrait[0];
    AbstractTrait[] longsword = new AbstractTrait[0];
    AbstractTrait[] broadsword = new AbstractTrait[0];
    AbstractTrait[] katana = new AbstractTrait[0];
    AbstractTrait[] cutlass = new AbstractTrait[0];
    AbstractTrait[] lumberAxe = new AbstractTrait[0];
    AbstractTrait[] battleaxe = new AbstractTrait[0];
    AbstractTrait[] hammer = new AbstractTrait[0];
    AbstractTrait[] excavator = new AbstractTrait[0];
    AbstractTrait[] helmet = new AbstractTrait[0];
    AbstractTrait[] chestplate = new AbstractTrait[0];
    AbstractTrait[] leggings = new AbstractTrait[0];
    AbstractTrait[] boots = new AbstractTrait[0];

    private TraitPack() {

    }

    public static Builder create() {
        return new Builder();
    }

    @SuppressWarnings("UnusedReturnValue")
    public static class Builder {
        private final TraitPack pack;

        private Builder() {
            this.pack = new TraitPack();
        }

        public Builder sword(AbstractTrait... traits) {
            pack.sword = ArrayUtils.addAll(pack.sword, traits);
            return this;
        }

        public Builder axe(AbstractTrait... traits) {
            pack.axe = ArrayUtils.addAll(pack.axe, traits);
            return this;
        }

        public Builder pickaxe(AbstractTrait... traits) {
            pack.pickaxe = ArrayUtils.addAll(pack.pickaxe, traits);
            return this;
        }

        public Builder shovel(AbstractTrait... traits) {
            pack.shovel = ArrayUtils.addAll(pack.shovel, traits);
            return this;
        }

        public Builder hoe(AbstractTrait... traits) {
            pack.hoe = ArrayUtils.addAll(pack.hoe, traits);
            return this;
        }

        public Builder longsword(AbstractTrait... traits) {
            pack.longsword = ArrayUtils.addAll(pack.longsword, traits);
            return this;
        }

        public Builder broadsword(AbstractTrait... traits) {
            pack.broadsword = ArrayUtils.addAll(pack.broadsword, traits);
            return this;
        }

        public Builder katana(AbstractTrait... traits) {
            pack.katana = ArrayUtils.addAll(pack.katana, traits);
            return this;
        }

        public Builder cutlass(AbstractTrait... traits) {
            pack.cutlass = ArrayUtils.addAll(pack.cutlass, traits);
            return this;
        }

        public Builder lumberAxe(AbstractTrait... traits) {
            pack.lumberAxe = ArrayUtils.addAll(pack.lumberAxe, traits);
            return this;
        }

        public Builder battleaxe(AbstractTrait... traits) {
            pack.battleaxe = ArrayUtils.addAll(pack.battleaxe, traits);
            return this;
        }

        public Builder hammer(AbstractTrait... traits) {
            pack.hammer = ArrayUtils.addAll(pack.hammer, traits);
            return this;
        }

        public Builder excavator(AbstractTrait... traits) {
            pack.excavator = ArrayUtils.addAll(pack.excavator, traits);
            return this;
        }

        public Builder helmet(AbstractTrait... traits) {
            pack.helmet = ArrayUtils.addAll(pack.helmet, traits);
            return this;
        }

        public Builder chestplate(AbstractTrait... traits) {
            pack.chestplate = ArrayUtils.addAll(pack.chestplate, traits);
            return this;
        }

        public Builder leggings(AbstractTrait... traits) {
            pack.leggings = ArrayUtils.addAll(pack.leggings, traits);
            return this;
        }

        public Builder boots(AbstractTrait... traits) {
            pack.boots = ArrayUtils.addAll(pack.boots, traits);
            return this;
        }

        public TraitPack build() {
            return pack;
        }

        public Builder all(AbstractTrait... traits) {
            helmet(traits);
            chestplate(traits);
            leggings(traits);
            boots(traits);
            sword(traits);
            axe(traits);
            pickaxe(traits);
            shovel(traits);
            hoe(traits);
            longsword(traits);
            broadsword(traits);
            katana(traits);
            cutlass(traits);
            lumberAxe(traits);
            battleaxe(traits);
            hammer(traits);
            excavator(traits);


            return this;
        }

        public Builder armor(AbstractTrait... traits) {
            helmet(traits);
            chestplate(traits);
            leggings(traits);
            boots(traits);

            return this;
        }

        public Builder tools(AbstractTrait... traits) {
            sword(traits);
            axe(traits);
            pickaxe(traits);
            shovel(traits);
            hoe(traits);
            longsword(traits);
            broadsword(traits);
            katana(traits);
            cutlass(traits);
            lumberAxe(traits);
            battleaxe(traits);
            hammer(traits);
            excavator(traits);

            return this;
        }
    }
}
