package com.pinkulu.reportAlerts;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Collection;
import java.util.ArrayList;

public class lookup {
    public static ArrayList<String> people = new ArrayList<String>();
    ArrayList<String> info = new ArrayList<String>();
    public static boolean firstJoin = false;
    public static boolean autoClear = false;
    public static boolean joinServer = false;
    public static int Ticks = 0;


    @SubscribeEvent
    public void worldSwitch(WorldEvent.Unload event){
        if (autoClear){
            people.clear();
        }
    }
    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (!joinServer) {
            if (Ticks < 40) {
                Ticks++;
            } else if (Ticks == 40) {
                if (!firstJoin){
                    firstJoin = true;
                    reportalert.saveConfig();
                    Minecraft.getMinecraft().thePlayer.addChatMessage(
                            new ChatComponentText
                                    (util.replace
                                            ("&aHello and welcome to ReportAlerts" +
                                                    "\n&aMake sure you do &3/alertdiscord" +
                                                    "\n&aThats the only way to report people, get help, or report bugs" +
                                                    "\n&a(This message will only show up the first time)")));
                }
                Thread apiRequest = new Thread(() -> {
                    {
                        URL urlForGetRequest = null;
                        try {
                            urlForGetRequest = new URL(util.replaceDash("http://api.pinkulu.com/version"));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        String readLine = null;
                        HttpURLConnection conection = null;
                        try {
                            assert urlForGetRequest != null;
                            conection = (HttpURLConnection) urlForGetRequest.openConnection();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            assert conection != null;
                            conection.setRequestMethod("GET");
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        }
                        int responseCode = 0;
                        try {
                            responseCode = conection.getResponseCode();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            BufferedReader in = null;
                            try {
                                in = new BufferedReader(
                                        new InputStreamReader(conection.getInputStream()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            StringBuilder response = new StringBuilder();
                            while (true) {
                                try {
                                    assert in != null;
                                    if ((readLine = in.readLine()) == null) break;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                response.append(readLine);
                            }
                            try {
                                in.close();
                                JsonObject convertedObject = new Gson().fromJson(response.toString(), JsonObject.class);
                                double Version = convertedObject.get("Version").getAsDouble();
                                double BetaVersion = convertedObject.get("Beta-Version").getAsDouble();
                                ChatStyle style = new ChatStyle();
                                style.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/pinkulu/reportalerts/releases"));
                                if (reportalert.beta) {
                                    if (BetaVersion > reportalert.version) {
                                        Minecraft.getMinecraft().thePlayer.addChatMessage(
                                                new ChatComponentText
                                                        (util.replace
                                                                ("&dReportAlert &9Beta update")));
                                        Minecraft.getMinecraft().thePlayer.addChatMessage(
                                                new ChatComponentText
                                                        (util.replace
                                                                ("&bv" + BetaVersion + "-beta is now available")));
                                        Minecraft.getMinecraft().thePlayer.addChatMessage(
                                                new ChatComponentText
                                                        (util.replace
                                                                ("&bClick here to download the new version")).setChatStyle(style));

                                    }
                                } else if (Version > reportalert.version) {
                                    Minecraft.getMinecraft().thePlayer.addChatMessage(
                                            new ChatComponentText
                                                    (util.replace
                                                            ("&dReportAlert &9update")));
                                    Minecraft.getMinecraft().thePlayer.addChatMessage(
                                            new ChatComponentText
                                                    (util.replace
                                                            ("&bv" + Version + " is now available")));
                                    Minecraft.getMinecraft().thePlayer.addChatMessage(
                                            new ChatComponentText
                                                    (util.replace
                                                            ("&bClick here to download the new version")).setChatStyle(style));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                joinServer = true;
                apiRequest.start();
            }
        }
    }
    // uwu
    @SubscribeEvent
    public void player(EntityJoinWorldEvent event) throws Exception {
        if (!Minecraft.getMinecraft().isSingleplayer()) {
            if (event.entity instanceof EntityPlayer) {
                Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
                for (NetworkPlayerInfo player : players) {
                    if (!people.contains(player.getGameProfile().getName())) {
                        people.add(player.getGameProfile().getName());
                        Thread apiRequest = new Thread(() -> {
                            {
                                URL urlForGetRequest = null;
                                try {
                                    urlForGetRequest = new URL(util.replaceDash("http://api.pinkulu.com/hacker/uuid/" + player.getGameProfile().getId()));
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                String readLine = null;
                                HttpURLConnection conection = null;
                                try {
                                    assert urlForGetRequest != null;
                                    conection = (HttpURLConnection) urlForGetRequest.openConnection();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    assert conection != null;
                                    conection.setRequestMethod("GET");
                                } catch (ProtocolException e) {
                                    e.printStackTrace();
                                }
                                int responseCode = 0;
                                try {
                                    responseCode = conection.getResponseCode();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (responseCode == HttpURLConnection.HTTP_OK) {
                                    BufferedReader in = null;
                                    try {
                                        in = new BufferedReader(
                                                new InputStreamReader(conection.getInputStream()));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    StringBuilder response = new StringBuilder();
                                    while (true) {
                                        try {
                                            assert in != null;
                                            if ((readLine = in.readLine()) == null) break;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        response.append(readLine);
                                    }
                                    try {
                                        in.close();
                                        if (!response.toString().contains("\"No such uuid\"")) {
                                            JsonObject convertedObject = new Gson().fromJson(response.toString(), JsonObject.class);
                                            String Name = convertedObject.get("Name").getAsString();
                                            String uuid = convertedObject.get("UUID").getAsString();
                                            String Reports = convertedObject.get("Reports").getAsString();
                                            String FirstReport = convertedObject.get("First report").getAsString();
                                            String LastReport = convertedObject.get("Last report").getAsString();
                                            int Verified = convertedObject.get("Verified").getAsInt();

                                            ChatStyle style = new ChatStyle();
                                            if (Verified==1){
                                                String VerifiedDate = convertedObject.get("verified date").getAsString();
                                                String VerifiedBy = convertedObject.get("verified by").getAsString();
                                                style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(util.replace(
                                                        " &3Player info: " +
                                                                "\n &cName: &4"+ Name +
                                                                "\n &cUUID: &4"+ uuid +
                                                                "\n &cReports: &4"+ Reports +
                                                                "\n &cFirst Report: &4"+ FirstReport +
                                                                "\n &cLast Report: &4"+ LastReport +
                                                                "\n &cVerified date: &4"+ VerifiedDate +
                                                                "\n &cVerified by: &4"+ VerifiedBy +
                                                                "\n &4for discord /alertsdiscord" +
                                                                "\n &5Mod made by:" +
                                                                "\n &dPinkulu" ))));
                                                IChatComponent text = new ChatComponentText(util.replace( "&4There is a verified hacker in your game &c" + Name)).setChatStyle(style);
                                                Minecraft.getMinecraft().thePlayer.addChatMessage(text);
                                            }
                                            else if (Reports.equals("1")) {
                                                //hoverable text
                                                style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(util.replace(
                                                        " &3Player info: " +
                                                                "\n &cName: &4"+ Name +
                                                                "\n &cUUID: &4"+ uuid +
                                                                "\n &cReports: &4"+ Reports +
                                                                "\n &cFirst Report: &4"+ FirstReport +
                                                                "\n &bIf you want to report this player join the discord" +
                                                                "\n &4https://discord.gg/Fykpshg" +
                                                                "\n &4or /DiscordAlerts" +
                                                                "\n &5Mod made by:" +
                                                                "\n &dPinkulu" ))));
                                                //what shows in chat and than the hoverable text is registered
                                                IChatComponent text = new ChatComponentText(util.replace( "&4There is a hacker in your game &c" + Name + "&4 with &c" + Reports + "&4 report")).setChatStyle(style);
                                                Minecraft.getMinecraft().thePlayer.addChatMessage(text);
                                            }
                                            else{
                                                style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(util.replace(
                                                        " &3Player info: " +
                                                                "\n &cName: &4"+ Name +
                                                                "\n &cUUID: &4"+ uuid +
                                                                "\n &cReports: &4"+ Reports +
                                                                "\n &cFirst Report: &4"+ FirstReport +
                                                                "\n &cLast Report: &4"+ LastReport +
                                                                "\n &bIf you want to report this player join the discord" +
                                                                "\n &4https://discord.gg/Fykpshg" +
                                                                "\n &4or /DiscordAlerts" +
                                                                "\n &5Mod made by:" +
                                                                "\n &dPinkulu" ))));
                                                //what shows in chat and than the hoverable text is registered
                                                IChatComponent text = new ChatComponentText(util.replace( "&4There is a hacker in your game &c" + Name + "&4 with &c" + Reports + "&4 reports")).setChatStyle(style);
                                                Minecraft.getMinecraft().thePlayer.addChatMessage(text);
                                            }

                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            info.clear();
                        });
                        apiRequest.start();
                    }
                }
            }
        }
        else {
            people.clear();
        }
    }
}