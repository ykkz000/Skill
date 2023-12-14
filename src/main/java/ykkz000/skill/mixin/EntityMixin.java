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

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ykkz000.skill.api.player.ExtendedPlayerEntity;
import ykkz000.skill.api.player.ExtendedPlayerState;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "readNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("RETURN"))
    public void readNbt(NbtCompound nbt, CallbackInfo ci) {
        if (((Entity) (Object) this) instanceof PlayerEntity) {
            ((ExtendedPlayerEntity) this).setExtendedPlayerState(nbt.contains("skill-extend") ? ExtendedPlayerState.createFromNbt(nbt.getCompound("skill-extend")) : new ExtendedPlayerState());
        }
    }

    @Inject(method = "writeNbt(Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/nbt/NbtCompound;", at = @At("RETURN"))
    public void writeNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (((Entity) (Object) this) instanceof PlayerEntity) {
            nbt.put("skill-extend", ((ExtendedPlayerEntity) this).getExtendedPlayerState().writeNbt(new NbtCompound()));
        }
    }
}
