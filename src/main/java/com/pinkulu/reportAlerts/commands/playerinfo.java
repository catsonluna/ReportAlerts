package com.pinkulu.reportAlerts.commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pinkulu.reportAlerts.util;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class playerinfo extends CommandBase {
    @Override
    public String getCommandName() {
        return "alertplayerinfo";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args[0].length() == 0){
            Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText
                            (util.replace
                                    ("&cPlease specific the player you want to look up")));
        }
        Thread apiRequest = new Thread(() -> {
            {
                URL urlForGetRequest = null;
                try {
                    urlForGetRequest = new URL(util.replaceDash("http://api.pinkulu.com/hacker/name/" + args[0].toLowerCase()));
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
                        ChatStyle style = new ChatStyle();
                    if (!response.toString().contains("\"No such name\"")) {
                        String Name = convertedObject.get("Name").getAsString();
                        String uuid = convertedObject.get("UUID").getAsString();
                        String Reports = convertedObject.get("Reports").getAsString();
                        String FirstReport = convertedObject.get("First report").getAsString();
                        String LastReport = convertedObject.get("Last report").getAsString();
                        int Verified = convertedObject.get("Verified").getAsInt();
                            if (Verified==1){
                                String VerifiedDate = convertedObject.get("verified date").getAsString();
                                String VerifiedBy = convertedObject.get("verified by").getAsString();
                                style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(util.replace(
                                        " &3Player info: " +
                                                "\n &cName: &4"+ Name +
                                                "\n &cUUID: &4"+ uuid +
                                                "\n &cReports: &4"+ Reports +
                                                "\n &cFirst Report: &4"+ FirstReport +
                                                "\n &cLast Report: &4" + LastReport +
                                                "\n &bVerified date"+ VerifiedDate +
                                                "\n &4Verified by"+ VerifiedBy +
                                                "\n &4for discord /alertsdiscord" +
                                                "\n &5Mod made by:" +
                                                "\n &dPinkulu" ))));
                                IChatComponent text = new ChatComponentText(util.replace( Name + "&4 is a verified hacker")).setChatStyle(style);
                                Minecraft.getMinecraft().thePlayer.addChatMessage(text);
                        }
                        //hoverable text
                        style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(util.replace(
                                " &3Player info: " +
                                        "\n &cName: &4" + Name +
                                        "\n &cUUID: &4" + uuid +
                                        "\n &cReports: &4" + Reports +
                                        "\n &cFirst Report: &4" + FirstReport +
                                        "\n &cLast Report: &4" + LastReport +
                                        "\n &bIf you want to report this player join the discord" +
                                        "\n &4https://discord.gg/Fykpshg" +
                                        "\n &4or /DiscordAlerts" +
                                        "\n &5Mod made by:" +
                                        "\n &dPinkulu"))));
                        //what shows in chat and than the hoverable text is registered
                        IChatComponent text = new ChatComponentText(util.replace("&4This player has been reported &c" + Name + "&4 with &c" + Reports + "&4 report")).setChatStyle(style);
                        Minecraft.getMinecraft().thePlayer.addChatMessage(text);
                    } else {
                        //what shows in chat and than the hoverable text is registered
                        IChatComponent text = new ChatComponentText(util.replace("&aThis player hasnt been reported")).setChatStyle(style);
                        Minecraft.getMinecraft().thePlayer.addChatMessage(text);
                    }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        apiRequest.start();
    }

@Override
public int getRequiredPermissionLevel() {
    return -1;
}
}



