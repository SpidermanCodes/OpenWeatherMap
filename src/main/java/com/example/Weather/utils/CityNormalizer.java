package com.example.Weather.utils;

import com.ibm.icu.text.Transliterator;

public class CityNormalizer {

    // Transliterator koji pretvara sve u Latinicu i uklanja dijakritike
    private static final Transliterator TO_LATIN = Transliterator.getInstance(
            "Any-Latin; NFD; [:Nonspacing Mark:] Remove; NFC"
    );

    public static String normalizeCity(String city) {
        if (city == null) return null;
        // pretvara Čačak -> Cacak, Đakovo -> Djakovo itd.
        String normalized = TO_LATIN.transform(city);

        // dodatno zameni specijalne slučajeve koje API očekuje
        normalized = normalized.replace("Đ", "D")
                .replace("đ", "d");

        return normalized;
    }
}