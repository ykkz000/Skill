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

package ykkz000.skill.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ykkz000.skill.SkillModMain;
import ykkz000.skill.api.player.ExtendedPlayerEntity;
import ykkz000.skill.api.player.ExtendedPlayerState;
import ykkz000.skill.api.player.SkillState;
import ykkz000.skill.api.skill.Skill;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements ExtendedPlayerEntity {
    @Unique
    private ExtendedPlayerState extendedState;

    @Override
    public ExtendedPlayerState getExtendedPlayerState() {
        return extendedState;
    }

    @Override
    public void setExtendedPlayerState(ExtendedPlayerState state) {
        this.extendedState = state;
    }

    @Override
    public void useSkill(Identifier id) {
        SkillState skillState = extendedState.getSkillState(id);
        PlayerEntity player = (PlayerEntity) (Object) this;
        World world = player.getWorld();
        Skill skill = SkillModMain.SKILL_REGISTRY.get(id);
        if (!skillState.isEnabled() || skill == null) {
            player.sendMessage(Text.translatable("text.skill.skill_not_available").formatted(Formatting.RED));
        } else if (!skillState.canUse()){
            player.sendMessage(Text.translatable("text.skill.skill_cooling_down").formatted(Formatting.RED));
        } else if (!skill.canUse(player, world)) {
            player.sendMessage(Text.translatable("text.skill.skill_cannot_use").formatted(Formatting.RED));
        } else {
            skill.use(player, world);
            skillState.setLeftCooldownTime(skill.getCooldownTime());
        }
    }

    @Inject(method = "tick()V", at = @At("RETURN"))
    public void tick(CallbackInfo ci) {
        extendedState.tick();
    }
}
