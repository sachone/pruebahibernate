/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.helper;

import javax.servlet.ServletException;

/**
 *
 * @author Alvaro
 */
public class ParserConverter {

    /**
     *
     * @param text
     * @param token
     * @param type
     * @return
     * @throws java.lang.Exception
     */
    public String tag(String text, String token, String type) throws Exception {

        String[] textSplit = text.split(token);

        String textFomat = "";
        String close = "";
        boolean tag = false;

        try {
            for (int x = 0; x < textSplit.length; x++) {
                if (tag) {
                    tag = false;
                    close = "/";
                } else {
                    close = "";
                    tag = true;
                }

                textFomat += textSplit[x] + "<" + close + type + ">";

            }
            if (tag == true) {
                textFomat = textFomat.substring(0, textFomat.length() - 4);
            }
        } catch (Exception e) {
            throw new ServletException("Method " + type + " Error: " + e.getMessage());
        }
        tag = false;
        return textFomat;
    }

    /**
     *
     * @param text
     * @param url
     * @return
     * @throws Exception
     */
    public String a(String text, String url) throws Exception {

        String[] textSplit = text.split("\\[");

        String textFomat = "";
        String tag = "";
        boolean a = false;
        try {
            if (textSplit.length != 1) {
                for (int x = 0; x < textSplit.length; x++) {

                    if (x == 0) {
                        textFomat += textSplit[x];
                        x++;
                    }

                    String[] spl = textSplit[x].split("\\]");
                    String[] split = spl[0].split("\\|");
                    if (split[0].length() >= 7 && split[0].substring(0, 7).equals("http://")) {
                        if (spl.length == 1) {
                            tag = "<a href='" + split[0] + "'>" + split[1] + "&nbsp<i class='icon-globe'></i></a>";
                        } else {
                            tag = "<a href='" + split[0] + "'>" + split[1] + "&nbsp<i class='icon-globe'></i></a>" + spl[1];
                        }
                    } else {
                        String ulrSplit[] = url.split("&");
                        String strUrl = "";
                        for (int g = 0; g < ulrSplit.length; g++) {
                            String[] urlSplit2 = ulrSplit[g].split("=");
                            if (urlSplit2[0].equals("class")) {
                                strUrl += urlSplit2[0] + "=" + urlSplit2[1];
                            }
                            if (urlSplit2[0].equals("method")) {
                                strUrl += urlSplit2[0] + "=" + urlSplit2[1];
                            }
                            if (g != ulrSplit.length - 1) {
                                strUrl += "&";
                            }
                            if (strUrl.length() == 1) {
                                strUrl = "";
                            }
                        }
                        if (spl.length == 1) {
                            tag = "<a href='Controller?" + strUrl + "&id=" + split[0] + "'>" + split[1] + "&nbsp<i class='icon-globe'></i></a>";
                        } else {
                            tag = "<a href='Controller?" + strUrl + "&id=" + split[0] + "'>" + split[1] + "&nbsp<i class='icon-globe'></i></a>" + spl[1];
                        }
                    }

                    textFomat += tag;
                }
            }
        } catch (Exception e) {
            throw new ServletException("Method aExtern Error: " + e.getMessage());
        }
        if (textFomat.isEmpty()) {
            textFomat = text;
        }
        return textFomat;
    }

    /**
     *
     * @param text
     * @return
     * @throws java.lang.Exception
     */
    public String p(String text) throws Exception {
        String[] textSplit = text.split("\n");
        String textFomat = "";
        try {
            for (int p = 0; p < textSplit.length; p++) {
                textFomat += "<p>" + textSplit[p] + "</p>";
            }
        } catch (Exception e) {
            throw new ServletException("Method p Error: " + e.getMessage());
        }
        return textFomat;
    }

}
