package me.earth.earthhack.impl.managers;

import me.earth.earthhack.api.event.bus.instance.Bus;
import me.earth.earthhack.api.module.Module;
import me.earth.earthhack.api.module.util.PluginsCategory;
import me.earth.earthhack.api.plugin.Plugin;
import me.earth.earthhack.impl.Earthhack;
import me.earth.earthhack.impl.managers.chat.ChatManager;
import me.earth.earthhack.impl.managers.chat.CommandManager;
import me.earth.earthhack.impl.managers.chat.WrapManager;
import me.earth.earthhack.impl.managers.client.*;
import me.earth.earthhack.impl.managers.client.macro.MacroManager;
import me.earth.earthhack.impl.managers.config.ConfigManager;
import me.earth.earthhack.impl.managers.minecraft.*;
import me.earth.earthhack.impl.managers.minecraft.combat.*;
import me.earth.earthhack.impl.managers.minecraft.movement.*;
import me.earth.earthhack.impl.managers.minecraft.timer.TimerManager;
import me.earth.earthhack.impl.managers.render.ColorManager;
import me.earth.earthhack.impl.managers.render.TextRenderer;
import me.earth.earthhack.impl.managers.thread.EntityProvider;
import me.earth.earthhack.impl.managers.thread.ThreadManager;
import me.earth.earthhack.impl.managers.thread.connection.ConnectionManager;
import me.earth.earthhack.impl.managers.thread.holes.HoleManager;
import me.earth.earthhack.impl.managers.thread.lookup.LookUpManager;
import me.earth.earthhack.impl.managers.thread.safety.SafetyManager;

import java.io.IOException;

/**
 * The internals of the Client.
 * Loads managers upon startup and acts as a Manager for Managers.
 */
public class Managers {
    public static final ThreadManager THREAD       = new ThreadManager();
    public static final MacroManager MACRO         = new MacroManager();
    public static final ChatManager CHAT           = new ChatManager();
    public static final PlayerManager FRIENDS      = new PlayerManager();
    public static final PlayerManager ENEMIES      = new PlayerManager();
    public static final ModuleManager MODULES      = new ModuleManager();
    public static final HudElementManager ELEMENTS = new HudElementManager();
    public static final CombatManager COMBAT       = new CombatManager();
    public static final PositionManager POSITION   = new PositionManager();
    public static final RotationManager ROTATION   = new RotationManager();
    public static final ServerManager SERVER       = new ServerManager();
    public static final ActionManager ACTION       = new ActionManager();
    public static final SpeedManager SPEED         = new SpeedManager();
    public static final SwitchManager SWITCH       = new SwitchManager();
    public static final TimerManager TIMER         = new TimerManager();
    public static final TPSManager TPS             = new TPSManager();
    public static final TextRenderer TEXT          = new TextRenderer();
    public static final HoleManager HOLES          = new HoleManager();
    public static final SafetyManager SAFETY       = new SafetyManager();
    public static final ConnectionManager CONNECT  = new ConnectionManager();
    public static final KeyBoardManager KEYBOARD   = new KeyBoardManager();
    public static final ColorManager COLOR         = new ColorManager();
    public static final WrapManager WRAP           = new WrapManager();
    public static final NCPManager NCP             = new NCPManager();
    public static final SetDeadManager SET_DEAD    = new SetDeadManager();
    public static final LookUpManager LOOK_UP      = new LookUpManager();
    public static final ConfigManager CONFIG       = new ConfigManager();
    public static final BlockStateManager BLOCKS   = new BlockStateManager();
    public static final TargetManager TARGET       = new TargetManager();
    public static final EntityProvider ENTITIES    = new EntityProvider();
    public static final HealthManager HEALTH       = new HealthManager();
    public static final ServerTickManager TICK     = new ServerTickManager();
    public static final FileManager FILES          = new FileManager();
    public static final CommandManager COMMANDS    = new CommandManager();

    /**
     * Loads all Managers, starts the Event System and loads Plugins.
     */
    public static void load() {
        Earthhack.getLogger().info("Subscribing Managers.");
        Earthhack.getLogger().info("Starting Event System.");
        subscribe(TIMER, CONNECT, CHAT, COMBAT, POSITION, ROTATION, SERVER,
                ACTION, SPEED, SWITCH, TPS, HOLES, SAFETY, KEYBOARD, COLOR,
                WRAP, MACRO, NCP, SET_DEAD, BLOCKS, ENTITIES, HEALTH, TICK,
                FILES /*, new NoMotionUpdateService(), new PlayerMotionService(),
                new PotionService()*/);

        Earthhack.getLogger().info("Loading Plugins");
        PluginManager.getInstance().instantiatePlugins();
        for (Plugin plugin : PluginManager.getInstance().getPlugins().values())
            plugin.load();
        for (Module m : Managers.MODULES.getRegistered())
            PluginsCategory.getInstance().addPluginModule(m);

        Earthhack.getLogger().info("Loading Commands");
        COMMANDS.init();
        subscribe(COMMANDS);

        Earthhack.getLogger().info("Loading Modules");
        MODULES.init();
        /*
         * Initialize PingBypass here!!!
         */
        ELEMENTS.init();

        Earthhack.getLogger().info("Loading Configs");
        try
        {
            CONFIG.refreshAll();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Earthhack.getLogger().info("Initializing Modules");
        MODULES.load();
        ELEMENTS.load();
    }

    public static void subscribe(Object...subscribers)
    {
        for (Object subscriber : subscribers)
        {
            Bus.EVENT_BUS.subscribe(subscriber);
        }
    }
}
