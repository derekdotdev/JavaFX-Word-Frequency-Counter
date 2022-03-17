//package application.data;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.logging.FileHandler;
//import java.util.logging.Logger;
//import java.util.logging.SimpleFormatter;
//
//public class Log {
//
//    public Logger logger;
//    FileHandler fileHandler;
//
//    public Log(String file_name) throws SecurityException, IOException {
//
//        File file = new File(file_name);
//
//        if(!file.exists()) {
//            file.createNewFile();
//        }
//
//        // Instantiate fileHandler with append set to true
//        fileHandler = new FileHandler(file_name, true);
//
//        logger = Logger.getLogger("test");
//        logger.addHandler(fileHandler);
//        SimpleFormatter formatter = new SimpleFormatter();
//        fileHandler.setFormatter(formatter);
//
//    }
//
//}
