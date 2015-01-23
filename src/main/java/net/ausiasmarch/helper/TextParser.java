/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import javax.servlet.ServletException;

/**
 *
 * @author Alvaro
 */
public class TextParser {

    /**
     *
     * @param text
     * @param url
     * @return
     * @throws Exception
     */
    public static String toHtml(String text, String url) throws Exception {
        ParserConverter oParserConverter = new ParserConverter();
        try {
            if (text != null) {
                if (!text.equals("")) {
                    text = oParserConverter.tag(text, "======", "h6");
                    text = oParserConverter.tag(text, "=====", "h5");
                    text = oParserConverter.tag(text, "====", "h4");
                    text = oParserConverter.tag(text, "===", "h3");
                    text = oParserConverter.tag(text, "==", "h2");
                    text = oParserConverter.tag(text, "=", "h1");
                    text = oParserConverter.tag(text, "\\*\\*", "b");
                    text = oParserConverter.tag(text, "\\$\\$", "i");
                    text = oParserConverter.a(text, url);
                    text = oParserConverter.p(text);
                }
            }
        } catch (Exception e) {
            throw new ServletException("TextParser Error: " + e.getMessage());
        }
        return text;
    }

    /**
     *
     * @param textEncode
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String textEncode(String textEncode) throws UnsupportedEncodingException, ServletException {
        String encode = "";
        try {
            encode = URLEncoder.encode(textEncode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new ServletException("Encode: " + e.getMessage());
        }
        return encode;
    }

    /**
     *
     * @param textDecode
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String textDecode(String textDecode) throws UnsupportedEncodingException, ServletException {
        String decode = "";
        try {
            decode = URLDecoder.decode(textDecode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new ServletException("Decode: " + e.getMessage());
        }
        return decode;
    }

    public static String forHTML(String aText) {
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(aText);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
            if (character == '<') {
                result.append("&lt;");
            } else if (character == '>') {
                result.append("&gt;");
            } else if (character == '&') {
                result.append("&amp;");
            } else if (character == '\"') {
                result.append("&quot;");
            } else if (character == '\t') {
                addCharEntity(9, result);
            } else if (character == '!') {
                addCharEntity(33, result);
            } else if (character == '#') {
                addCharEntity(35, result);
            } else if (character == '$') {
                addCharEntity(36, result);
            } else if (character == '%') {
                addCharEntity(37, result);
            } else if (character == '\'') {
                addCharEntity(39, result);
            } else if (character == '(') {
                addCharEntity(40, result);
            } else if (character == ')') {
                addCharEntity(41, result);
            } else if (character == '*') {
                addCharEntity(42, result);
            } else if (character == '+') {
                addCharEntity(43, result);
            } else if (character == ',') {
                addCharEntity(44, result);
            } else if (character == '-') {
                addCharEntity(45, result);
            } else if (character == '.') {
                addCharEntity(46, result);
            } else if (character == '/') {
                addCharEntity(47, result);
            } else if (character == ':') {
                addCharEntity(58, result);
            } else if (character == ';') {
                addCharEntity(59, result);
            } else if (character == '=') {
                addCharEntity(61, result);
            } else if (character == '?') {
                addCharEntity(63, result);
            } else if (character == '@') {
                addCharEntity(64, result);
            } else if (character == '[') {
                addCharEntity(91, result);
            } else if (character == '\\') {
                addCharEntity(92, result);
            } else if (character == ']') {
                addCharEntity(93, result);
            } else if (character == '^') {
                addCharEntity(94, result);
            } else if (character == '_') {
                addCharEntity(95, result);
            } else if (character == '`') {
                addCharEntity(96, result);
            } else if (character == '{') {
                addCharEntity(123, result);
            } else if (character == '|') {
                addCharEntity(124, result);
            } else if (character == '}') {
                addCharEntity(125, result);
            } else if (character == '~') {
                addCharEntity(126, result);
            } else {
         //the char is not a special one
                //add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }

    private static void addCharEntity(Integer aIdx, StringBuilder aBuilder) {
        String padding = "";
        if (aIdx <= 9) {
            padding = "00";
        } else if (aIdx <= 99) {
            padding = "0";
        } else {
            //no prefix
        }
        String number = padding + aIdx.toString();
        aBuilder.append("&#" + number + ";");
    }
}
