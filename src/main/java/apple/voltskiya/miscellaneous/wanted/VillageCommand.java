package apple.voltskiya.miscellaneous.wanted;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Subcommand;

@CommandAlias("village")
public class VillageCommand extends BaseCommand {
    public VillageCommand() {
        VoltskiyaPlugin.get().getCommandManager().registerCommand(this);
        VoltskiyaPlugin.get().getCommandManager().getCommandCompletions().registerCompletion("village-type",(o)-> Village.VillageType.names());
    }

    @Subcommand("create")
    @CommandCompletion("factionName @village-type")
    public void create(String factionName, String type) {
        VillageDatabase.addVillage(Village.create(factionName, type));
    }

}
