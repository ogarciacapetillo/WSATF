package com.app.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by OS344312 on 8/30/2016.
 */
public class PropertyLoader {
    private final static Logger logger = LoggerFactory.getLogger(PropertyLoader.class);

    public PropertyLoader() {}


    public String loadProperty(String property)  {
        ResourceBundle _prop;
        String value=null;
        try{
            // go through the resources files
            Iterator<String> iterator = enumResources(System.getProperty("user.dir") + "\\src\\main\\resources").iterator();
            while (iterator.hasNext()){
                try{
                    _prop = ResourceBundle.getBundle(iterator.next());
                    //logger.info("Bundle: "+_prop.getBaseBundleName());
                    value = _prop.getString(property);
                    break;
                }catch (Exception e){
                    if(e.getMessage().contains("NullPointer")){
                        logger.info("Context", Arrays.toString(e.getStackTrace()));
                        throw new NullPointerException(e.getMessage());
                    }
                }

            }
        }catch(Exception ex){
            logger.info("Error loading the properties file: "+ Arrays.toString(ex.getStackTrace()));
            if(ex.getMessage().contains("NullPointer")){
                try {
                    logger.info("Context", Arrays.toString(ex.getStackTrace()));
                    throw new NullPointerException(ex.getMessage());
                } catch (Exception e) {
                    logger.info("Context", Arrays.toString(e.getStackTrace()));
                    throw new NullPointerException(e.getMessage());
                }
            }

        }
        return value;
    }

    private static List<String> enumResources(String resourceLocation) throws IOException {
        List<String> result = new ArrayList<String>();
        File resourceFolder = new File(resourceLocation);
        File[] listOfFiles = resourceFolder.listFiles();
        String propReg = "[A-Za-z]+?\\.properties";

        for(File file:listOfFiles){
            if (file.getName().matches(propReg)) result.add(file.getName().substring(0,file.getName().indexOf(".")));
        }
        return result;
    }
}




