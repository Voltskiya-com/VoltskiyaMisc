package apple.voltskiya.miscellaneous.arrows;

import apple.lib.pmc.AppleModule;

public class PluginArrowTags extends AppleModule {
    /**
     * enables the module with listeners and commands and anything else
     */
    @Override
    public void enable() {
        new ArrowHitListener();
    }

    /**
     * @return the name of the module which also specifies the datafolder
     */
    @Override
    public String getName() {
        return "arrow_tags";
    }
}
