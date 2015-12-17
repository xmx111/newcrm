package com.ufo.core.context;

public class RuntimeContextThreadLocal {
    private static InheritableThreadLocal<RuntimeContext> runtimeContextThreadLocal = new InheritableThreadLocal<RuntimeContext>() {
        protected RuntimeContext initialValue() {
            return null;
        }
    };

    public static RuntimeContext create() {
        RuntimeContext context = runtimeContextThreadLocal.get();
        if (context != null) {
            return context;
        }
        context = new RuntimeContext();
        runtimeContextThreadLocal.set(context);
        return context;
    }

    public static RuntimeContext get() {
        return runtimeContextThreadLocal.get();
    }

    public static void clear() {
        runtimeContextThreadLocal.remove();
    }
}
