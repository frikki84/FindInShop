package com.dziadkouskaya.findinshop.by.parsing.model.stringCorrect;

public class StringCorrect {
    public  static final int INDEX_IN_BUSLIK_DELETE_OT_FROM_PRICE = 3;

    public static String priceRubCorrection(String price) {
        String value = "р";
        if (price.contains(value)) {
            int index = price.indexOf(value);
            price = price.substring(0, index).trim();
        }
        return price;
    }

    public static String priceCorrectionFrom_OT(String price) {
        String value = "от "; // "от 25 руб"
        if (price.contains(value)) {
            price = price.substring(INDEX_IN_BUSLIK_DELETE_OT_FROM_PRICE).trim();
        }
        return price;
    }

    public static String priceCorrectionWildberries(String price) {
        String value = " р. "; // "от 25 руб"
        if (price.contains(value)) {
            price = price.replace(" р. ", ".");
            price = price.replace(" к.", "");
        }
        return price;
    }

    //the method to convert prices with cents
    public static String priceCentCorrection(String price) {
        String value = "к.";
        if (price.contains(value)) {
            int index = price.indexOf(value);
            price = price.substring(0, index).trim();
        }
        return price;
    }

    public static String stringCorrectForParsing(String msg) {
        msg = msg.toLowerCase();
        msg = msg.replace(" голд ", " gold ");
        msg = msg.replace("посудом.", "посудомоечных ");
        msg = msg.replace("ополаск. ", "ополаскиватель ");
        msg = msg.replace("окраш. ", "окрашенных ");
        msg = msg.replace("для мытья посуды в посудомоечных машинах", "для посудомоечных машин");
        msg = msg.replace("макаронные изделия ", "макароны ");
        msg = msg.replace("макфа ", "makfa ");
        msg = msg.replace("спираль ", "спирали ");
        msg = msg.replace("\"", " ");
        msg = msg.replace("0 гр", "0г");
        msg = msg.replace(" c ", " с ");
        msg = msg.replace(" фас ", " ");




        String[] mass = {"(м)", ".", "+", ",", "/","'", "\'", "\"", "«", "»", "*", "\\", "|", "(", ")",
                ":", "для мальчиков и девочек ", "для мужчин ", "для женщин ", "для новорожденных "
                , "для детей ", "для тонких волос ", "для нормальных волос ", "для нормальных и жирных волос "
                ,"для всех типов волос ","многослойные ", "для жирных волос ", "для окрашенных волос "
                , "для сухих волос ", "с ухажпротеином", " с рождения", " с 6 месяцев"
                , "для окрашенных и поврежденных волос", "для поврежденных волос",
                " поврежденных волос ", "для сухих повреждволос ", "для сухих/поврежденных волос "
                ,"для сухих ", "/повреждволос ", "новый ", "новая ", "новое ", "для сухих повреждволос "
                , "д/повреждволос ", "детские ", "для детского питания ",
                "восстановленный ", "осветленный ", "осветленныe ", "полнорацс", "duo ", "single "
                , "ультратонкие ", "арт88508", "арт88509", "арт81408", "для секущихся "
        , "для окраш.волос ", " фас.", "для мальч девоч", "для мальчиков девочек", " в ассортименте", " в асс"};
        for (int i = 0; i < mass.length; i++) {
            if (msg.contains(mass[i])) {
                msg = msg.replace(mass[i], "");
            }
        }
        msg = msg.replace("  ", " ");
        msg = msg.replace("   ", " ");
        msg = msg.replace("    ", " ");
        msg = msg.replace("     ", " ");
        msg = msg.replace("0 мл", "0мл");
        msg = msg.replace(" мл", "мл");
        msg = msg.replace(" кг", "кг");
        msg = msg.replace(" шт", "шт");
        msg = msg.replace("д/", "для ");
        msg = msg.replace("мужской бритвы", "бритья");
        msg = msg.replace("белита", "BELITA");
        msg = msg.replace("гигиен ", "гигиенические ");
        msg = msg.replace(" и ", " ");
        msg = msg.replace("sos-", "sos ");
        msg = msg.replace("экстрапитание", "питательная");
        msg = msg.replace("garnier fructis", "fructis");
        msg = msg.replace("кофе молотый ", "кофе натуральный молотый ");
        msg = msg.replace(" prodom ", " prodomo ");
        msg = msg.replace("трусики-подгузники", "трусики");
        msg = msg.replace("подгузники-трусики", "трусики");
        msg = msg.replace("семечки подсолнечника ", "семечки ");


        msg = msg.trim().toUpperCase();

        return msg;
    }

}
