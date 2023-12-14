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

package ykkz000.skill.api;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import ykkz000.skill.SkillModMain;
import ykkz000.skill.api.skill.Skill;

public class SkillMainAPI {
    public static Skill registerSkill(Identifier id, Skill skill) {
        return Registry.register(SkillModMain.SKILL_REGISTRY, id, skill);
    }
}
