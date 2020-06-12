package com.pinkulu.reportAlerts.commands;

import com.pinkulu.reportAlerts.reportalert;
import com.pinkulu.reportAlerts.util;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public class help extends CommandBase {
    @Override
    public String getCommandName() {
        return "alerthelp";
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
        style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(util.replace(
                   "&3Commands: " +
                        "\n&9/alertclear" +
                        "\n&bThis will clear the list of the palyers, stored, by default if you meet a player, " +
                        "\n&9/alertautoclear" +
                        "\n&bThis will do &9/alerclear &b every time you switch lobby" +
                        "\n&9/alertplayerinfo" +
                        "\n&blook up a player and see if they have been reported, or anything else" +
                        "\n&band you join a game with him again, it will not look for the player again" +
                        "\n&9/alertsdiscord" +
                        "\n&bThis will give you a link to the discord server, so you can report people" +
                        "\n&4In game reporting is not a thing yet, to keep false reports, as low as possible" +
                        "\n&5Mod made by:" +
                        "\n&dPinkulu" ))));
        //what shows in chat and than the hoverable text is registered
        IChatComponent text = new ChatComponentText(util.replace( "&e " + reportalert.NAME +" " + reportalert.VERSION + " &7(hoverable text)")).setChatStyle(style);
        //ads to chat
        player.addChatMessage(text);
    }
    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}

