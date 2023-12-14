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

package ykkz000.skill.api.player;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

@Getter
@Setter
public class SkillState {
    private final Identifier id;
    private boolean enabled;
    private int leftCooldownTime;

    public SkillState(Identifier id, boolean enabled) {
        this.id = id;
        this.enabled = enabled;
    }

    public boolean canUse() {
        return enabled && leftCooldownTime <= 0;
    }

    public void tick() {
        leftCooldownTime = Math.max(leftCooldownTime - 1, 0);
    }

    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putString("id", id.toString());
        nbt.putBoolean("enabled", enabled);
        nbt.putInt("leftCooldownTime", leftCooldownTime);
        return nbt;
    }

    public static SkillState createFromNbt(NbtCompound nbt) {
        SkillState state = new SkillState(new Identifier(nbt.getString("id")), nbt.getBoolean("enabled"));
        state.leftCooldownTime = nbt.getInt("leftCooldownTime");
        return state;
    }

}
