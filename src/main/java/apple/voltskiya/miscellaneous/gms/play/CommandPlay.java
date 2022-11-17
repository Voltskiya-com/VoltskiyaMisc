package apple.voltskiya.miscellaneous.gms.play;

import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandCompletion;
import com.voltskiya.lib.acf.annotation.Subcommand;
import apple.mc.utilities.player.chat.SendMessage;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import org.bukkit.entity.Player;

@CommandAlias("play")
public class CommandPlay extends BaseCommand implements SendMessage {

    public CommandPlay() {
        VoltskiyaPlugin.get().registerCommand(this);
    }

    @Subcommand("join")
    @CommandCompletion("@playthrough")
    public void join(Player player) {

    }

    @Subcommand("add")
    @CommandCompletion("[name]")
    public void add() {

    }

    @Subcommand("delete")
    @CommandCompletion("@playthrough")
    public void delete() {

    }

    @Subcommand("archive")
    public class Archive extends BaseCommand {

        @Subcommand("join")
        public void join() {

        }
    }
}
