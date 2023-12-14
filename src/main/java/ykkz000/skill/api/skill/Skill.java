/*
 * Skill
 * Copyright (C) 2023  ykkz000
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ykkz000.skill.api.skill;


import lombok.Getter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import ykkz000.skill.SkillModMain;
import ykkz000.skill.api.SkillMainAPI;

@Getter
public abstract class Skill {
    private static final Skill EMPTY = SkillMainAPI.registerSkill(new Identifier(SkillModMain.MOD_ID, "empty"), new EmptySkill());
    protected final int cooldownTime;
    protected String translationKey;
    public Skill(int cooldownTime){
        this.cooldownTime = cooldownTime;
    }
    protected String getOrCreateTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.createTranslationKey("skill", SkillModMain.SKILL_REGISTRY.getId(this));
        }
        return this.translationKey;
    }
    public String getTranslationKey() {
        return this.getOrCreateTranslationKey();
    }
    public abstract boolean canUse(PlayerEntity player, World world);
    public abstract void use(PlayerEntity player, World world);

    public static final class EmptySkill extends Skill {

        public EmptySkill() {
            super(0);
        }

        @Override
        public boolean canUse(PlayerEntity player, World world) {
            return false;
        }

        @Override
        public void use(PlayerEntity player, World world) {
        }
    }
}
