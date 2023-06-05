package com.morpheus.previewtyapi.util;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
public class ConvertUtil {

    /**
    *
    * converObejctToMap.class 의 설명을 하단에 작성한다.
    * vo 객체를 Map<String,Object> 형태로 반환
    * parameter : Obejct (vo 객체)
    * returnType : Map<String,Object>
    * @author 최연식
    * @version 1.0.0
    * 작성일 2021/08/05
    **/
    public static Map convertObjectToMap(Object obj){
        Map map = new HashMap();
        Field[] fields = obj.getClass().getDeclaredFields();
        for(int i=0; i <fields.length; i++){
            fields[i].setAccessible(true);
            try{
                map.put(fields[i].getName(), fields[i].get(obj));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return map;
    }
}
