package me.earth.earthhack.impl.event.events.misc;

import me.earth.earthhack.api.event.events.Event;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class DamageBlockEvent extends Event
{
    private final BlockPos pos;
    private final Direction facing;
    private float damage;
    private int delay;

    public DamageBlockEvent(BlockPos pos, Direction facing, float damage, int delay)
    {
        this.pos = pos;
        this.facing = facing;
        this.damage = damage;
        this.delay = delay;
    }

    public BlockPos getPos()
    {
        return pos;
    }

    public Direction getFacing()
    {
        return facing;
    }

    public float getDamage()
    {
        return damage;
    }

    public void setDamage(float damage)
    {
        this.damage = damage;
    }

    public int getDelay()
    {
        return delay;
    }

    public void setDelay(int delay)
    {
        this.delay = delay;
    }

}
