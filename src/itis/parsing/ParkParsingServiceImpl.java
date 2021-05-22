package itis.parsing;

import com.sun.javafx.collections.MapListenerHelper;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class ParkParsingServiceImpl implements ParkParsingService {

    //Парсит файл в обьект класса "Park", либо бросает исключение с информацией об ошибках обработки
    @Override
    public Park parseParkData(String parkDatafilePath) throws ParkParsingException {
        Map <String, String> value = new HashMap<>();

        Constructor<Park> parkConstructor = null;
        try {
            parkConstructor = Park.class.getDeclaredConstructor();
            parkConstructor.setAccessible(true);
            Park park = parkConstructor.newInstance();
            FileReader fileReader = new FileReader(parkDatafilePath);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.equals("***")) continue;
                String[] split = line.split(":");
                String name = split[0].substring(1, split[0].length() - 2);
                String strValue = split[1];
                strValue = split[1].substring(1, split[1].length() - 2);
                value.put(name, strValue);
            }
            reader.close();

            Field[] fields = Park.class.getDeclaredFields();


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File file = new File(parkDatafilePath);
            String legalName = null;
            String ownerOrganizationInn = null;
            Date foundationYear = null;
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String parkLine = scanner.nextLine();
                String[] parts = parkLine.split(":");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (parkLine.contains("foundationYear")) {
                    try {
                        foundationYear = dateFormat.parse(parts[1]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else if (parkLine.contains("ownerOrganizationInn")) {
                    ownerOrganizationInn = parts[1];
                }
                else if (parkLine.contains("legalName")) {
                    legalName = parts[1];
                }
                Class<Park> parkClass = Park.class;
                try {
                    Constructor<Park> privateConstructor = parkClass.getDeclaredConstructor(String.class, String.class, DateFormat.class);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

//
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
