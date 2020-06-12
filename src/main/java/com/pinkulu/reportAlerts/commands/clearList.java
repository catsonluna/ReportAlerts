package com.pinkulu.reportAlerts.commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pinkulu.reportAlerts.lookup;
import com.pinkulu.reportAlerts.util;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class clearList extends CommandBase {
    @Override
    public String getCommandName() {
        return "alertclear";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        lookup.people.clear();
        Minecraft.getMinecraft().thePlayer.addChatMessage(
                new ChatComponentText
                (util.replace
                        ("&aYou have cleared the list")));
        }
    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}
