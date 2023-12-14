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

package ykkz000.skill.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.network.PacketByteBuf;
import ykkz000.skill.SkillModMain;
import ykkz000.skill.api.client.SkillClientAPI;

@Environment(EnvType.CLIENT)
public class SkillModClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            for (KeyBinding keyBinding : SkillClientAPI.SKILL_BINDINGS.keySet()) {
                boolean pressed = false;
                while (keyBinding.wasPressed()) {
                    pressed = true;
                }
                if (pressed) {
                    PacketByteBuf buffer = PacketByteBufs.create();
                    buffer.writeIdentifier(SkillClientAPI.SKILL_BINDINGS.get(keyBinding));
                    ClientPlayNetworking.send(SkillModMain.SKILL_PACKET_ID, buffer);
                }
            }
        });
    }
}
