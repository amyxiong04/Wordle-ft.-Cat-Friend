package model;

import java.util.ArrayList;

public class Log {
    private ArrayList<String> log;
    private String result;
//    private StringBuilder sb;

    public Log() {
        log = new ArrayList<>();
        result = "";
//        sb = new StringBuilder();
    }

    public void add(String str){
        log.add(str);
    }

    //    public String toString(){
//        return
//    }
    public void iterateLog(){
//        result = "";
        for (int i=0; i<log.size(); i++){
            this.result+=log.get(i);
            if (i<log.size()-1) {
                this.result += "_";
            }
        }
//        for (String e: log) {
//            System.out.println(e);
//        }
    }

    public String getResult() {
        return this.result;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
//        sb.append("[");
        for (int i = 0; i < log.size(); i++) {
            sb.append(log.get(i));
            if (i < log.size() - 1) {
                sb.append("|");
            }
        }
//        sb.append("]");
        return sb.toString();
//        String forHandleString = sb.toString();
//        int len = forHandleString.length();
//        for (int i = 0; i<len;i++) {
//            System.out.println(forHandleString.charAt(i));
//            if ((i+1)%9==0) {
//                System.out.println();
//            }
//        }
//        return sb.toString();

    }

    public void formatPrint(){
//        System.out.println(toString());

        String str = toString();
//        int len = str.length();
//
//        for (int i = 0; i < len; i++) {
//            System.out.print(str.charAt(i));
//            if ((i + 1) % 5 == 0) {
//                System.out.println();
//            }
//        }
    }
}
