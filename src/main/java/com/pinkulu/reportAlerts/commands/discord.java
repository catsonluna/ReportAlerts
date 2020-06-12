package com.pinkulu.reportAlerts.commands;

import com.pinkulu.reportAlerts.reportalert;
import com.pinkulu.reportAlerts.util;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.*;

public class discord extends CommandBase {
    @Override
    public String getCommandName() {
        return "alertsdiscord";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player = (EntityPlayer) sender;
        ChatStyle style = new ChatStyle();
        //hoverable text
        style.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/Fykpshg"));
        //what shows in chat and than the hoverable text is registered
        IChatComponent text = new ChatComponentText(util.replace("&9Join the discord to report people, click here")).setChatStyle(style);
        //ads to chat
        player.addChatMessage(text);
    }
    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}
