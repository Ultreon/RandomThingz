package com.qsoftware.forgemod.modules.items.tools;

import com.qsoftware.forgemod.modules.items.tools.trait.AbstractTrait;
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
            ArrayUtils.addAll(pack.sword, traits);
            return this;
        }

        public Builder axe(AbstractTrait... traits) {
            ArrayUtils.addAll(pack.axe, traits);
            return this;
        }

        public Builder pickaxe(AbstractTrait... traits) {
            ArrayUtils.addAll(pack.pickaxe, traits);
            return this;
        }

        public Builder shovel(AbstractTrait... traits) {
            ArrayUtils.addAll(pack.shovel, traits);
            return this;
        }

        public Builder hoe(AbstractTrait... traits) {
            ArrayUtils.addAll(pack.hoe, traits);
            return this;
        }

        public Builder longsword(AbstractTrait... traits) {
            ArrayUtils.addAll(pack.longsword, traits);
            return this;
        }

        public Builder broadsword(AbstractTrait... traits) {
            ArrayUtils.addAll(pack.broadsword, traits);
            return this;
        }

        public Builder katana(AbstractTrait... traits) {
            ArrayUtils.addAll(pack.katana, traits);
            return this;
        }

        public Builder lumberAxe(AbstractTrait... traits) {
            ArrayUtils.addAll(pack.lumberAxe, traits);
            return this;
        }

        public Builder battleaxe(AbstractTrait... traits) {
            ArrayUtils.addAll(pack.battleaxe, traits);
            return this;
        }

        public Builder hammer(AbstractTrait... traits) {
            ArrayUtils.addAll(pack.hammer, traits);
            return this;
        }

        public Builder excavator(AbstractTrait... traits) {
            ArrayUtils.addAll(pack.excavator, traits);
            return this;
        }

        public Builder helmet(AbstractTrait... traits) {
            ArrayUtils.addAll(pack.helmet, traits);
            return this;
        }

        public Builder chestplate(AbstractTrait... traits) {
            ArrayUtils.addAll(pack.chestplate, traits);
            return this;
        }

        public Builder leggings(AbstractTrait... traits) {
            ArrayUtils.addAll(pack.leggings, traits);
            return this;
        }

        public Builder boots(AbstractTrait... traits) {
            ArrayUtils.addAll(pack.boots, traits);
            return this;
        }

        public TraitPack build() {
            return pack;
        }

        public Builder all(AbstractTrait... traits) {
            sword(traits);
            axe(traits);
            pickaxe(traits);
            shovel(traits);
            hoe(traits);
            longsword(traits);
            broadsword(traits);
            katana(traits);
            lumberAxe(traits);
            battleaxe(traits);
            hammer(traits);
            excavator(traits);


            return this;
        }
    }
}
