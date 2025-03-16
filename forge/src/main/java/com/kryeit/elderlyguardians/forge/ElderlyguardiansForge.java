package com.kryeit.elderlyguardians.forge;

import com.kryeit.elderlyguardians.Elderlyguardians;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Elderlyguardians.MOD_ID)
public final class ElderlyguardiansForge {

    public ElderlyguardiansForge() {
        MinecraftForge.EVENT_BUS.register(this);

        Elderlyguardians.init();
    }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        if (event.getEntity().level().isClientSide()) return;

        if (event.getEntity() instanceof Guardian guardian && !(guardian instanceof ElderGuardian)) {

            if (event.getSource().getMsgId().equals("lightningBolt")) {
                Vec3 pos = guardian.position();

                ElderGuardian elderGuardian = EntityType.ELDER_GUARDIAN.create(guardian.level());
                if (elderGuardian != null) {
                    elderGuardian.setPos(pos.x, pos.y, pos.z);
                    elderGuardian.setHealth(guardian.getHealth());

                    guardian.level().addFreshEntity(elderGuardian);
                    guardian.discard();

                    event.setCanceled(true);
                }
            }
        }
    }
}