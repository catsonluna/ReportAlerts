package com.pinkulu.reportAlerts;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.pinkulu.reportAlerts.commands.*;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Mod(modid = reportalert.MODID, version = reportalert.VERSION, name = reportalert.NAME)

public class reportalert {
    static final String MODID = "reportalers";
    public static final String VERSION = "1.0";
    public static final String NAME = "ReportAlerts";
    public static double version = 1.0;
    public static boolean beta = false;

    private final lookup lookup = new lookup();

    @Mod.EventHandler
    public void onInitialization(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(lookup);
        ClientCommandHandler.instance.registerCommand(new clearList());
        ClientCommandHandler.instance.registerCommand(new help());
        ClientCommandHandler.instance.registerCommand(new playerinfo());
        ClientCommandHandler.instance.registerCommand(new discord());
        ClientCommandHandler.instance.registerCommand(new autoClear());
        loadConfig();
    }


    public static void saveConfig(){
        try {
            File file = new File("ReportAlert", "config.json");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            JsonWriter writer = new JsonWriter(new FileWriter(file, false));
            writeJson(writer);
            writer.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        try {
            File file = new File("ReportAlert", "config.json");
            if(file.exists())
                readJson(file);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void readJson(File file) throws Throwable{
        JsonParser parser = new JsonParser();
        JsonObject json  = parser.parse(new FileReader(file)).getAsJsonObject();
        json = json.getAsJsonObject("ReportAlert");

        com.pinkulu.reportAlerts.lookup.firstJoin = json.get("firstJoin").getAsBoolean();
        com.pinkulu.reportAlerts.lookup.autoClear = json.get("autoClear").getAsBoolean();

    }
    public static void writeJson(JsonWriter writer) throws IOException {
        writer.setIndent(" "); // this enabled pretty print
        writer.beginObject();
        writer.name("ReportAlert");
        writer.beginObject();

        writer.name("firstJoin").value(com.pinkulu.reportAlerts.lookup.firstJoin);
        writer.name("autoClear").value(com.pinkulu.reportAlerts.lookup.autoClear);


        writer.endObject();
        writer.endObject();
    }
}
