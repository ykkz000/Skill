/*
 * SkillModMain
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

package ykkz000.skill;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import ykkz000.skill.api.player.ExtendedPlayerEntity;
import ykkz000.skill.api.skill.Skill;

public class SkillModMain implements ModInitializer {
    public static final String MOD_ID = "skill";
    public static final RegistryKey<Registry<Skill>> SKILL_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier(MOD_ID, "skill"));
    public static final Registry<Skill> SKILL_REGISTRY = FabricRegistryBuilder.createSimple(SKILL_REGISTRY_KEY).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Identifier SKILL_PACKET_ID = new Identifier(MOD_ID, "skill");

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        ServerPlayNetworking.registerGlobalReceiver(SKILL_PACKET_ID, (server, player, handler, buffer, sender) -> {
            ((ExtendedPlayerEntity) player).useSkill(buffer.readIdentifier());
        });
    }
}
