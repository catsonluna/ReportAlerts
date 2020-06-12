package com.pinkulu.reportAlerts.commands;

import com.pinkulu.reportAlerts.lookup;
import com.pinkulu.reportAlerts.reportalert;
import com.pinkulu.reportAlerts.util;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class autoClear extends CommandBase {
    @Override
    public String getCommandName() {
        return "alertautoclear";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(!lookup.autoClear){
            lookup.autoClear = true;
            Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText
                            (util.replace
                                    ("&2You have enabled auto clear")));
            reportalert.saveConfig();
        }
        else {
            lookup.autoClear = false;
            Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText
                            (util.replace
                                    ("&4You have disabled auto clear")));
            reportalert.saveConfig();
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}
