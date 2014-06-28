package com.personal.oyl.newffms.pojo;

import java.lang.reflect.Method;

public abstract class BasePojo {
    public void setAllEmptyStringToNull() throws Exception {
        Method[] methods = this.getClass().getMethods();
        
        if (methods == null || methods.length == 0) {
            return;
        }
        
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            
            if (method.getName().equals("getCustomIdentification") ||
                method.getName().equals("getLogicalKey") ||
                !(method.getName().startsWith("get"))) {
                continue;
            }
            
            Object resultObj = method.invoke(this, new Object[] {});
            
            if (!(resultObj instanceof String)) {
                continue;
            }
            
            String result = (String) resultObj;
            if ("".equals(result.trim())) {
                String setMethodName = "set"
                        + method.getName().substring(3,
                                method.getName().length());
                try {
                    Method setMethod = this.getClass().getMethod(
                            setMethodName, new Class[] { String.class });
                    setMethod.invoke(this, new Object[] { null });
                }
                catch (NoSuchMethodException e) {
                    //ErrorHelper.getInstance().logError(log, e);
                }
            }
        }
    }

    
    public void trimAllString()  throws Exception {
        Method[] methods = this.getClass().getMethods();
        if (methods != null && methods.length > 0) {
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                
                if (method.getName().equals("getCustomIdentification") ||
                    method.getName().equals("getLogicalKey") ||
                    !(method.getName().startsWith("get"))) {
                    continue;
                }
                
                Object resultObj = method.invoke(this, new Object[] {});
                
                if (!(resultObj instanceof String)) {
                    continue;
                }
                
                String resultStr = (String) resultObj;
                String setMethodName = "set"
                        + method.getName().substring(3,
                                method.getName().length());

                try {
                    Method setMethod = this.getClass()
                            .getMethod(setMethodName,
                                    new Class[] { String.class });
                    setMethod.invoke(this,
                            new Object[] { resultStr.trim() });
                }
                catch (NoSuchMethodException e) {
                    //ErrorHelper.getInstance().logError(log, e);
                }
            }
        }
    }
}
