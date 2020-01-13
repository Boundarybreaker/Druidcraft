package com.vulp.druidcraft.advancements;

import com.vulp.druidcraft.advancements.criterion.TameMonsterTrigger;
import com.vulp.druidcraft.mixin.MixinCriterions;

public class Advancements {
    public static final TameMonsterTrigger TAME_MONSTER = MixinCriterions.invokeRegister(new TameMonsterTrigger());
}