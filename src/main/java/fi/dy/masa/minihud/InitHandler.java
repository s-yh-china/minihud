package fi.dy.masa.minihud;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.*;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import fi.dy.masa.malilib.network.handler.client.ClientPlayHandler;
import fi.dy.masa.malilib.network.payload.PayloadManager;
import fi.dy.masa.malilib.network.payload.PayloadType;
import fi.dy.masa.malilib.network.payload.channel.ServuxStructuresPayload;
import fi.dy.masa.minihud.config.Configs;
import fi.dy.masa.minihud.event.*;
import fi.dy.masa.minihud.hotkeys.KeyCallbacks;
import fi.dy.masa.minihud.network.ServuxStructuresPlayListener;

public class InitHandler implements IInitializationHandler
{
    @Override
    public void registerModHandlers()
    {
        ConfigManager.getInstance().registerConfigHandler(Reference.MOD_ID, new Configs());
        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());
        InputEventHandler.getInputManager().registerMouseInputHandler(InputHandler.getInstance());

        RenderHandler renderer = RenderHandler.getInstance();
        RenderEventHandler.getInstance().registerGameOverlayRenderer(renderer);
        RenderEventHandler.getInstance().registerTooltipLastRenderer(renderer);
        RenderEventHandler.getInstance().registerWorldLastRenderer(renderer);

        WorldLoadListener listener = new WorldLoadListener();
        WorldLoadHandler.getInstance().registerWorldLoadPreHandler(listener);
        WorldLoadHandler.getInstance().registerWorldLoadPostHandler(listener);

        ServerListener serverListener = new ServerListener();
        ServerHandler.getInstance().registerServerHandler(serverListener);

        TickHandler.getInstance().registerClientTickHandler(new ClientTickHandler());

        PayloadManager.getInstance().register(PayloadType.SERVUX_STRUCTURES, "structure_bounding_boxes", "servux", "structures");
        ServuxStructuresPlayListener<ServuxStructuresPayload> servuxStructuresListener = ServuxStructuresPlayListener.getInstance();
        ClientPlayHandler.getInstance().registerClientPlayHandler(servuxStructuresListener);

        KeyCallbacks.init();
    }
}
