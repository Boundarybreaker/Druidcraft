package com.vulp.druidcraft.api;

import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

@FunctionalInterface
public interface IKnifeable {
    ActionResult onKnife(ItemUsageContext context);
}
