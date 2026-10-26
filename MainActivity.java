
package com.example.calculator;
import android.app.Activity; import android.os.Bundle; import android.widget.*;
import android.widget.GridLayout; import android.view.ViewGroup;
public class MainActivity extends Activity {
    TextView display; StringBuilder input=new StringBuilder();
    protected void onCreate(Bundle b){ super.onCreate(b);
        LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(24,24,24,24);
        display=new TextView(this); display.setTextSize(36); display.setText("0");
        display.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.addView(display);
        GridLayout grid=new GridLayout(this); grid.setColumnCount(4);
        String[] btns={"C","⌫","%","/","7","8","9","*","4","5","6","-","1","2","3","+","0",".","=",""};
        for(String s:btns){ Button btn=new Button(this); btn.setText(s); btn.setTextSize(24);
            btn.setOnClickListener(v->onBtn(s)); grid.addView(btn); }
        root.addView(grid); setContentView(root);
    }
    void onBtn(String s){
        if(s.equals("C")){input.setLength(0); display.setText("0"); return;}
        if(s.equals("⌫")){ if(input.length()>0) input.deleteCharAt(input.length()-1); display.setText(input.length()==0?"0":input.toString()); return;}
        if(s.equals("=")){ try{ double r=eval(input.toString()); display.setText(String.valueOf(r)); input.setLength(0); input.append(r);}catch(Exception e){display.setText("Error"); input.setLength(0);} return;}
        if(s.isEmpty()) return;
        input.append(s); display.setText(input.toString());
    }
    double eval(String e){ return new Object(){ int p=-1,ch;
        void n(){ch=(++p<e.length())?e.charAt(p):-1;} boolean eat(int c){while(ch==' ')n(); if(ch==c){n();return true;}return false;}
        double parse(){n(); double x=expr(); return x;}
        double expr(){double x=term();for(;;){if(eat('+'))x+=term();else if(eat('-'))x-=term();else return x;}}
        double term(){double x=fac();for(;;){if(eat('*'))x*=fac();else if(eat('/'))x/=fac();else if(eat('%'))x%=fac();else return x;}}
        double fac(){if(eat('+'))return fac();if(eat('-'))return -fac();double x;int s=p;if(eat('(')){x=expr();eat(')');}else if((ch>='0'&&ch<='9')||ch=='.'){while((ch>='0'&&ch<='9')||ch=='.')n();x=Double.parseDouble(e.substring(s,p));}else throw new RuntimeException();return x;}
    }.parse(); }
}
