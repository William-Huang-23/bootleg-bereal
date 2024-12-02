package com.william.bootleg_bereal.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ErrorUtils {
    private static final Map<Integer, String> errorMessages = new HashMap<>();

    static {
        errorMessages.put(0, "SUCCESS");
        errorMessages.put(1, "PHOTO NOT FOUND");
//        errorMessages.put(2, "INVALID REQUEST ID");
        errorMessages.put(3, "INVALID USERNAME");
        errorMessages.put(4, "INVALID DATE");
        errorMessages.put(5, "PHOTO FOR TODAY UPLOADED");
        errorMessages.put(6, "USER NOT FOUND");
        errorMessages.put(7, "USER ALREADY EXISTS");
        errorMessages.put(8, "COMMENT NOT FOUND");
        errorMessages.put(9, "INVALID COMMENT ID");
        errorMessages.put(10, "USERNAME AND TARGET USERNAME IS THE SAME");
        errorMessages.put(11, "TARGET USER IS ALREADY IN FRIEND LIST");
        errorMessages.put(12, "TARGET USER NOT FOUND IN FRIEND LIST");
        errorMessages.put(13, "COMMENT NOT FOUND IN PHOTO");
        errorMessages.put(14, "INVALID PASSWORD");
        errorMessages.put(15, "INVALID NAME");
        errorMessages.put(16, "INVALID AGE");
        errorMessages.put(17, "INVALID BIRTHDAY");
        errorMessages.put(18, "INVALID TARGET USERNAME");
        errorMessages.put(19, "TARGET USER NOT FOUND");
        errorMessages.put(99, "SYSTEM ERROR");
    }

//    returns TRUE if it's not empty, returns FALSE if it's empty or null
    public static boolean stringIsEmpty(Object input) {
        return input == null || input.toString().isEmpty();
    }

    public static ResponseEntity<?> errorFormat(int errorCode) {
        Map<String, Object> response = new HashMap<String, Object>();

        response.put("errorCode", errorCode);
        response.put("errorMessage", errorMessages.get(errorCode));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static Map<String, Object> success() {
        Map<String, Object> response = new HashMap<String, Object>();

        response.put("errorCode", 0);
        response.put("errorMessage", errorMessages.get(0));

        return response;
    }
}
