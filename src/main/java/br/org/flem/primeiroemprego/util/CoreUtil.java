/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.util;

import br.org.flem.primeiroemprego.entity.UID;
import com.google.gson.Gson;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;

/**
 *
 * @author tscortes
 */
public class CoreUtil {

    /**
     * retorna o segundo objeto, caso o primeiro parametro seja nulo
     *
     * @param value
     * @param otherValue
     * @return Object
     */
    public static Object nvl(Object value, Object otherValue) {
        if (value == null) {
            return otherValue;
        }
        return value;
    }

    /**
     * retorna o segunda coleção, caso o primeira seja nula ou vazia
     *
     * @param values Collection
     * @param otherValues Collection
     * @return Object Collection
     */
    public static Collection nvl(Collection values, Collection otherValues) {
        if (values == null || values.isEmpty()) {
            return otherValues;
        }
        return values;
    }

    /**
     * Verifica se os dois objetos são iguais
     *
     * @param value Object
     * @param otherValue Object
     * @return boolean
     */
    public static boolean isIgual(Object value, Object otherValue) {
        return value != null && otherValue != null && value.equals(otherValue);
    }

    /**
     * Verifica se existe algum valor nulo
     *
     * @param params
     * @return boolean
     */
    public static boolean someIsNull(UID... params) {
        if (params != null && params.length > 0) {
            for (UID uid : params) {
                if (uid == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param json
     * @param classes
     * @return
     */
    public static Object jsonToObject(String json, Class classes) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        Gson gson = new Gson();
        Object dto = gson.fromJson(json, classes);
        return dto;
    }

    public static boolean isValidEmailAddress(String email) {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }
    
}
