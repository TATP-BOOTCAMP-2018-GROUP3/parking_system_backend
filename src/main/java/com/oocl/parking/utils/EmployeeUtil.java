package com.oocl.parking.utils;

import com.oocl.parking.domain.Employee;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class EmployeeUtil {
    private static final String SOURCES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    public static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = SOURCES.charAt(random.nextInt(SOURCES.length()));
        }
        return new String(text);
    }

    private static String hashPassword(String password, String token) {
        String hashedPassword = null;
        try {
            MessageDigest md = null;
            md = MessageDigest.getInstance("SHA-256");
            md.update(token.getBytes());
            md.update(password.getBytes());
            byte byteBuffer[] = md.digest();
            StringBuffer strHexString = new StringBuffer();
            for (int i = 0; i < byteBuffer.length; i++) {
                String hex = Integer.toHexString(0xff & byteBuffer[i]);
                if (hex.length() == 1) {
                    strHexString.append('0');
                }
                strHexString.append(hex);
            }
            hashedPassword = strHexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }

    public static Employee fillEmployeeDefaultInfo(Employee originEmployee) {
        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(originEmployee.getId());
        updatedEmployee.setEmail(originEmployee.getEmail());
        updatedEmployee.setPhoneNum(originEmployee.getPhoneNum());
        updatedEmployee.setAccountName(originEmployee.getAccountName());

        updatedEmployee.setName(originEmployee.getAccountName());
        updatedEmployee.setRole("CLERK");
        updatedEmployee.setWorkingStatus("On Duty");

        return updatedEmployee;
    }

    public static Employee fillInEmployeePasswordInfo(Employee originEmployee, String password) {
        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(originEmployee.getId());
        updatedEmployee.setEmail(originEmployee.getEmail());
        updatedEmployee.setPhoneNum(originEmployee.getPhoneNum());
        updatedEmployee.setAccountName(originEmployee.getAccountName());
        updatedEmployee.setName(originEmployee.getName());
        updatedEmployee.setRole(originEmployee.getRole());
        updatedEmployee.setWorkingStatus(originEmployee.getWorkingStatus());

        String token = generateRandomString(8);
        updatedEmployee.setToken(token);
        updatedEmployee.setHashedPassword(hashPassword(password, token));

        return updatedEmployee;
    }
}
