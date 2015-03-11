/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanimoto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author jesus
 */
public class TaniMoto {

    Connection conn = ConnectDB.linkConnect();
    PreparedStatement pst;
    ResultSet rst;

    private int similarity(String a, String b) {
        int aLength = a.length();
        int bLength = b.length();
        float c = 0.0f;
        boolean bb = aLength > bLength;
        for (int i = 0; i < (bb ? aLength : bLength); i++) {
            if (bb) {
                char ch = a.charAt(i);
                if (b.contains(String.valueOf(ch))) {
                    c++;
                }
            } else {
                char ch = b.charAt(i);
                if (a.contains(String.valueOf(ch))) {
                    c++;
                }
            }
        }
        int f = (int) (c / (aLength + bLength - c) * 100);
        return f;
    }

    public void test2(LinkedList<String> list) {
        int sizeCollection = list.size();
        if (sizeCollection > 2) {
            for (int i = 0; i < sizeCollection - 1; i++) {
                for (int j = i + 1; j <= sizeCollection -1; j++) {
                    System.out.println("similarity " 
                            + i + " и " + j + " = " 
                    + similarity(list.get(i), list.get(j)));
                }
            }
        } else if(sizeCollection == 2){
            System.out.println("similarity " 
                            + 0 + " и " + 1 + " = " 
                    + similarity(list.get(0), list.get(1)));
        } else {
            System.out.println("There are no elements for comparison!!");
        }
    }

    private void test() {
        String sql = "SELECT HI_TEXT_ENG FROM History_intro "
                + "WHERE HI_END IS NULL ORDER BY RAND() LIMIT 5 ";
        LinkedList<String> list = new LinkedList<>();
        try {
            pst = conn.prepareStatement(sql);
            rst = pst.executeQuery();
            while (rst.next()) {
                list.add(rst.getString("HI_TEXT_ENG"));
            }
        } catch (SQLException e) {
            System.err.println("Exception " + e.getMessage());
        }
        test2(list);
    }

    public static void main(String[] args) {
        new TaniMoto().test();
    }

}
