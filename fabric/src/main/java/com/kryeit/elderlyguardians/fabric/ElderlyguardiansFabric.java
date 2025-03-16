package com.kryeit.elderlyguardians.fabric;

import com.kryeit.elderlyguardians.Elderlyguardians;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ElderlyguardiansFabric implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        // Run our common setup.
        Elderlyguardians.init();

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, damageSource, amount) -> {
            if (entity instanceof Guardian && !(entity instanceof ElderGuardian)) {

                if (damageSource.getMsgId().equals("lightningBolt")) {

                    ServerLevel world = (ServerLevel) entity.level();
                    Vec3 pos = entity.position();

                    ElderGuardian elderGuardian = EntityType.ELDER_GUARDIAN.create(world);
                    if (elderGuardian != null) {
                        elderGuardian.setPos(pos.x, pos.y, pos.z);
                        elderGuardian.setHealth(entity.getHealth());

                        world.addFreshEntity(elderGuardian);
                        entity.discard();

                        return false;
                    }
                }
            }

            return true;
        });
    }
}