package me.muriplz.elderlyguardians.neoforge;

import me.muriplz.elderlyguardians.Elderlyguardians;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@Mod(Elderlyguardians.MOD_ID)
public final class ElderlyguardiansNeoForge {
    public ElderlyguardiansNeoForge() {
        NeoForge.EVENT_BUS.register(this);

        Elderlyguardians.init();
    }

    @SubscribeEvent
    public void onLivingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity().level().isClientSide()) return;

        if (event.getEntity() instanceof Guardian guardian && !(guardian instanceof ElderGuardian)) {

            if (event.getSource().getMsgId().equals("lightningBolt")) {
                Vec3 pos = guardian.position();

                ElderGuardian elderGuardian = EntityType.ELDER_GUARDIAN.create(guardian.level(), EntitySpawnReason.CONVERSION);
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
