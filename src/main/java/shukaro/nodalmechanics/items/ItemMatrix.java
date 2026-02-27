package shukaro.nodalmechanics.items;

import java.util.List;
import java.util.Locale;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import org.apache.commons.lang3.text.WordUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import shukaro.nodalmechanics.NodalMechanics;
import shukaro.nodalmechanics.util.FormatCodes;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class ItemMatrix extends Item {

    @SideOnly(Side.CLIENT)
    private IIcon icon;

    public ItemMatrix() {
        super();
        this.setHasSubtypes(true);
        this.setCreativeTab(NodalMechanics.mainTab);
        this.setUnlocalizedName("nodalmechanics.matrix");
        this.setMaxStackSize(1);
    }

    public static boolean isAttuned(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey("Aspects");
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + (isAttuned(stack) ? ".attuned" : ".unattuned");
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
        list.add(new ItemStack(item, 1, 0));
        for (Aspect aspect : Aspect.getPrimalAspects()) {
            ItemStack itemStack = new ItemStack(item, 1, 0);
            AspectList aspectList = new AspectList().add(aspect, 8);
            NBTTagCompound tagCompound = new NBTTagCompound();
            aspectList.writeToNBT(tagCompound);
            itemStack.setTagCompound(tagCompound);
            list.add(itemStack);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return this.icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.icon = iconRegister.registerIcon(NodalMechanics.modID.toLowerCase(Locale.ENGLISH) + ":" + "itemMatrix");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack itemStack) {
        return isAttuned(itemStack) ? EnumRarity.uncommon : EnumRarity.common;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack itemStack, int pass) {
        return isAttuned(itemStack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advancedTooltips) {
        if (isAttuned(itemStack)) {
            NBTTagCompound tagCompound = itemStack.getTagCompound();
            AspectList aspectList = new AspectList();
            aspectList.readFromNBT(tagCompound);
            for (Aspect aspect : aspectList.getAspects()) {
                list.add(
                        "\u00A7" + (aspect.getChatcolor() != null ? aspect.getChatcolor() : "7")
                                + WordUtils.capitalize(aspect.getName())
                                + FormatCodes.Reset.code
                                + FormatCodes.White.code
                                + " x "
                                + aspectList.getAmount(aspect));
            }
            list.add(
                    FormatCodes.DarkGrey.code + FormatCodes.Italic.code
                            + StatCollector.translateToLocal("tooltip.nodalmechanics.matrix.attuned"));
        } else {
            list.add(
                    FormatCodes.DarkGrey.code + FormatCodes.Italic.code
                            + StatCollector.translateToLocal("tooltip.nodalmechanics.matrix.unattuned"));
        }
        list.add(
                FormatCodes.Grey.code + FormatCodes.Italic.code
                        + StatCollector.translateToLocal("tooltip.nodalmechanics.matrix.recipe"));
    }
}
