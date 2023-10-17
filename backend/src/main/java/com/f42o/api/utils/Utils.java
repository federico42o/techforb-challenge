package com.f42o.api.utils;

import java.util.Random;

public class Utils {

    public static  final String[] words = {"PESCADO","FIESTA","CAMION","SALTAMONTES","MANZANA", "PERA", "UVA", "PLATANO", "NARANJA", "FRESA", "KIWI", "LIMON", "CEREZA", "PIÑA", "SANDÍA", "MELON", "POMELO", "MANGO", "PAPAYA", "FRAMBUESA", "ARÁNDANO", "GRANADA", "CIRUELA", "COCO", "GUAYABA", "FRUTA", "VERDE", "AMARILLO", "ROJO", "MORADO", "AZUL", "NUBE", "SOL"};
    public static final Integer[] numbers = {1,2,3,4,5,6,7,8,9,0};
    private static final Random random = new Random();
    public static String randomWord(){

        return words[random.nextInt(words.length)];
    }
    public static Integer randomNumber(){

        return numbers[random.nextInt(numbers.length)];
    }

}
