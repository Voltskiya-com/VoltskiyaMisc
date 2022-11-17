package apple.voltskiya.miscellaneous.drops.items;

import apple.mc.utilities.inventory.item.InventoryUtils;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SpecialItemStack {
    private String[] flags;
    private ItemStack item;
    private String nameUid;

    public SpecialItemStack(ItemStack item) {
        this.item = item;
        this.flags = InventoryUtils.get().getItemFlags(item);
        this.nameUid = PlainTextComponentSerializer.plainText().serialize(this.item.displayName());
    }

    public SpecialItemStack(SpecialItemStack other) {
        this.item = other.item;
        this.flags = Arrays.copyOf(other.flags, other.flags.length);
        this.nameUid = other.nameUid;
    }

    public SpecialItemStack() {
    }

    public SpecialItemStack copy() {
        return new SpecialItemStack(this);
    }

    @NotNull
    public ItemStack toItem() {
        ItemStack item = this.item.clone();
        InventoryUtils.get().setItemFlags(item, flags);
        return item;
    }

    public String getNameUid() {
        return nameUid;
    }

    @Override
    public int hashCode() {
        return nameUid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SpecialItemStack itemDrop && itemDrop.nameUid.equals(this.nameUid);
    }
}
