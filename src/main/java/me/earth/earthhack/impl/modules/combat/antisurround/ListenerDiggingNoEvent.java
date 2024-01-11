package me.earth.earthhack.impl.modules.combat.antisurround;

import me.earth.earthhack.impl.core.ducks.network.IPlayerActionC2SPacket;
import me.earth.earthhack.impl.event.events.network.PacketEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.managers.Managers;
import me.earth.earthhack.impl.modules.combat.antisurround.util.AntiSurroundFunction;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;

final class ListenerDiggingNoEvent extends
        ModuleListener<AntiSurround, PacketEvent.NoEvent<PlayerActionC2SPacket>>
{
    private final AntiSurroundFunction function;

    public ListenerDiggingNoEvent(AntiSurround module)
    {
        super(module,
                PacketEvent.NoEvent.class,
                -1000,
                PlayerActionC2SPacket.class);
        this.function = new PreCrystalFunction(module);
    }

    @Override
    public void invoke(PacketEvent.NoEvent<PlayerActionC2SPacket> event)
    {
        if (event.isCancelled()
            || !event.hasPost()
            || event.getPacket().getAction()
                    != PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK
            || module.holdingCheck()
            || !module.preCrystal.getValue()
            || !((IPlayerActionC2SPacket) event.getPacket())
                                              .earthhack$isClientSideBreaking())
        {
            return;
        }

        module.onBlockBreak(
                event.getPacket().getPos(),
                Managers.ENTITIES.getPlayersAsync(),
                Managers.ENTITIES.getEntitiesAsync(),
                function);
    }

}
