package com.mikeyyds.library.log;

public class MikeStackTraceUtil {
    public static StackTraceElement[] getCroppedRealStackTrack(StackTraceElement[] stackTrace,String ignorePackage,int maxDepth){
        return cropStackTrace(getRealStackTrack(stackTrace,ignorePackage),maxDepth);
    }

    private static StackTraceElement[] cropStackTrace(StackTraceElement[] callStack,int maxDepth){
        int realDepth = callStack.length;
        if (maxDepth>0){
            realDepth = Math.min(maxDepth,realDepth);
        }
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(callStack,0,realStack,0,realDepth);
        return realStack;
    }

    private static StackTraceElement[] getRealStackTrack(StackTraceElement[] stackTrace,String ignorePackage){
        int ingnoreDepth = 0;
        int allDepth = stackTrace.length;
        String className;
        for (int i = allDepth-1; i >= 0; i--) {
            className = stackTrace[i].getClassName();
            if (ignorePackage != null && className.startsWith(ignorePackage)) {
                ingnoreDepth = i+ 1;
                break;
            }
        }
        int realDepth = allDepth - ingnoreDepth;
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(stackTrace,ingnoreDepth,realStack,0,realDepth);
        return realStack;
    }
}
