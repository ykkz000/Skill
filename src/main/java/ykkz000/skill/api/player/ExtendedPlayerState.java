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

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ExtendedPlayerState {
    private final Map<Identifier, SkillState> skills;

    public ExtendedPlayerState() {
        skills = new HashMap<>();
    }

    public SkillState getSkillState(Identifier id) {
        return skills.computeIfAbsent(id, identifier -> new SkillState(identifier, false));
    }

    public void tick() {
        skills.forEach((id, skillState) -> skillState.tick());
    }

    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList skillNbtList = new NbtList();
        skillNbtList.addAll(skills.values().stream().map(skillState -> skillState.writeNbt(new NbtCompound())).toList());
        nbt.put("skills", skillNbtList);
        return nbt;
    }

    public static ExtendedPlayerState createFromNbt(NbtCompound nbt) {
        ExtendedPlayerState playerState = new ExtendedPlayerState();
        NbtList list = nbt.getList("skills", NbtCompound.COMPOUND_TYPE);
        for (NbtElement element : list) {
            SkillState skillState = SkillState.createFromNbt((NbtCompound) element);
            playerState.skills.put(skillState.getId(), skillState);
        }
        return playerState;
    }
}
