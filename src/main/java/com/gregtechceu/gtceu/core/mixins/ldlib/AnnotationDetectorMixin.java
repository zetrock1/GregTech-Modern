package com.gregtechceu.gtceu.core.mixins.ldlib;

import com.lowdragmc.lowdraglib.gui.editor.runtime.AnnotationDetector;

import net.neoforged.fml.loading.FMLEnvironment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.function.*;

@Mixin(value = AnnotationDetector.class, remap = false)
public class AnnotationDetectorMixin {

    @Inject(method = "scanClasses", at = @At("HEAD"), cancellable = true, remap = false)
    private static <A, T, C> void gtceu$skipScan(Class<A> annotationClass,
                                                 Class<T> baseClazz,
                                                 BiPredicate<A, Class<? extends T>> predicate,
                                                 Function<Class<? extends T>, C> mapping,
                                                 Comparator<C> sorter,
                                                 Consumer<List<C>> onFinished,
                                                 CallbackInfoReturnable<List<C>> cir) {
        if (FMLEnvironment.dist.isDedicatedServer()) {
            cir.setReturnValue(Collections.emptyList());
        }
    }
}
