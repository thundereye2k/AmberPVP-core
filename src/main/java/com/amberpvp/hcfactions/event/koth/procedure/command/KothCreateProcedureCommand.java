package com.amberpvp.hcfactions.event.koth.procedure.command;

import com.amberpvp.hcfactions.profile.Profile;
import com.amberpvp.hcfactions.event.EventManager;
import com.amberpvp.hcfactions.event.koth.procedure.KothCreateProcedure;
import com.amberpvp.hcfactions.event.koth.procedure.KothCreateProcedureStage;
import com.amberpvp.hcfactions.profile.Profile;
import com.amberpvp.hcfactions.util.PluginCommand;
import com.amberpvp.hcfactions.util.command.Command;
import com.amberpvp.hcfactions.util.command.CommandArgs;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

public class KothCreateProcedureCommand extends PluginCommand {

    @Command(name = "koth.create", aliases = {"koth.new", "createkoth", "newkoth"})
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        Profile profile = Profile.getByPlayer(player);
        String[] args = command.getArgs();

        if (!player.hasPermission("staff.sradmin")) {
            player.sendMessage(PluginCommand.NO_PERMISSION);
            return;
        }

        if (profile.getKothCreateProcedure() != null) {
            player.sendMessage(" ");
            player.sendMessage(ChatColor.RED + "You're already in the process of creating a KoTH.");
            player.sendMessage(ChatColor.RED + "Type 'cancel' in chat to stop the procedure.");
            player.sendMessage(" ");
            return;
        }

        if (args.length == 0) {
            profile.setKothCreateProcedure(new KothCreateProcedure().stage(KothCreateProcedureStage.NAME_SELECTION));
            player.sendMessage(" ");
            player.sendMessage(ChatColor.YELLOW + "Please type a name for this KoTH in chat.");
            player.sendMessage(" ");
        } else {

            if (EventManager.getInstance().getByName(StringUtils.join(args)) != null) {
                player.sendMessage(" ");
                player.sendMessage(ChatColor.RED + "An event with that name already exists.");
                player.sendMessage(" ");
                return;
            }

            profile.setKothCreateProcedure(new KothCreateProcedure().stage(KothCreateProcedureStage.ZONE_SELECTION).name(StringUtils.join(args)));
            player.sendMessage(" ");
            player.sendMessage(ChatColor.YELLOW + "You have received the zone wand.");
            player.sendMessage(" ");
            player.getInventory().removeItem(KothCreateProcedure.getWand());
            player.getInventory().addItem(KothCreateProcedure.getWand());
        }

    }
}
