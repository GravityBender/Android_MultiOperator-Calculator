/*
    File created By:    Garvit Verma    BT20CSE078
*/

package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.EmptyStackException;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    TextView inputText;
    TextView resultsText;

    String text="";
    private static Stack<String> equationString=new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTextViews();

    }

    private void initTextViews(){
        inputText = findViewById(R.id.inputText);
        resultsText= findViewById(R.id.resultsText);
    }

    private void setInputText(String value){
        if (value.equals("clear")){
            text="";
            inputText.setText("");
            equationString.clear();
            resultsText.setText("");
        }else if(value.equals("")){
            equationString.pop();
            for(String element:equationString){
                text=text+element;
            }
            inputText.setText(text.toString());
            text="";
        }else if(value.equals("=")){
//            String infix = "";
//            for(String element:equationString){
//                infix=infix+element;
//            }
//            System.out.println(infix);
//            String postfix=infixTopostfix(infix);
//            System.out.println(postfix);
//            double result = evaluatePostfix(postfix);
//            System.out.println(result);
//            String stringResult = String.valueOf(result);
//            resultsText.setText(stringResult.toString());

            String infix = "";
            for(String element:equationString){
                infix=infix+element;
            }
//            System.out.println(infix);  //Debugging
            String postfix=infixTopostfix(infix);
//            System.out.println(postfix);    //Debugging
            double result = evaluatePostfix(postfix);
            DecimalFormat dFormat;
            if(result<0){
                dFormat = new DecimalFormat("#0.0000");
            }else{
                dFormat = new DecimalFormat("#0.00");
            }
//            System.out.println(result); //Debugging
            String stringResult = String.valueOf(dFormat.format(result));
            inputText.setText(stringResult.toString());
            resultsText.setText("".toString());
            equationString.clear();
            equationString.push(stringResult);
        }
        else {
            if(equationString.size()>14){
                Toast.makeText(this, "Reached maximum number of digits(15)", Toast.LENGTH_SHORT).show();
            }else{
                equationString.push(value);
                System.out.println(equationString+" "+equationString.peek());
                for(String element:equationString){
                    text=text+element;
                }
                inputText.setText(text.toString());
                text="";

                String infix = "";
                for(String element:equationString){
                    infix=infix+element;
                }
//            System.out.println(infix);  //Debugging
                String postfix=infixTopostfix(infix);
//            System.out.println(postfix);    //Debugging
                double result = evaluatePostfix(postfix);
                DecimalFormat dFormat;
                if(result<0){
                    dFormat = new DecimalFormat("#0.0000");
                }else{
                    dFormat = new DecimalFormat("#0.00");
                }

//            System.out.println(result); //Debugging
                String stringResult = String.valueOf(dFormat.format(result));
                resultsText.setText(stringResult.toString());
            }

        }
    }

    private static int symVal(char ch){
        switch(ch){
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
        }
        return -1;
    }

    private String infixTopostfix(String infixPattern){
        String result = new String("");
        Stack<Character> characterStack = new Stack<>();

        for(int i =0;i<infixPattern.length();i++){
            char c = infixPattern.charAt(i);

            if(i==0&&c=='-'){
                result+=c;
            } else if(Character.isDigit(c)||c=='.'||(c=='-'&&(!Character.isLetterOrDigit(infixPattern.charAt(i-1))))){
                result+=c;
            }else{
                while(!characterStack.isEmpty()&&symVal(c)<=symVal(characterStack.peek())){
//                    result+=characterStack.pop(); //  Debugging
//                    result+=characterStack.pop(); //  Debugging
                    result+=' ';    //Change
                    result+=characterStack.pop();

                }

                characterStack.push(c);
//                characterStack.push(' '); //  Debugging
                result+=' ';
            }
        }
        while(!characterStack.isEmpty()){
            result+=' ';    //Change
            result+=characterStack.pop();
        }
        return result;
    }

//    private double evaluatePostfix(String postfixPattern){
//        Stack<Double> stack = new Stack<>();
//
//        for(int i = 0; i < postfixPattern.length();i++){
//            char c = postfixPattern.charAt(i);
//
//            if(c==' '){
//                continue;
//            }
//            else if(Character.isDigit(c)||c=='.'){
//                double n = 0;
//
//                while(Character.isDigit(c)){
//                    n=n*10+(int) (c-'0');
//                    i++;
//                    c=postfixPattern.charAt(i);
//                }
//                i--;
//                stack.push(n);
//            }else{
//                double val1 = stack.pop();
//                double val2 = stack.pop();
//
//                switch(c){
//                    case '+':
//                        stack.push(val2+val1);
//                        break;
//                    case '-':
//                        stack.push(val2-val1);
//                        break;
//                    case '*':
//                        stack.push(val2*val1);
//                        break;
//                    case '/':
//                        stack.push(val2/val1);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
//        return stack.pop();
//    }

    private double evaluatePostfix(String postfixExp){
        Stack<Double> stack = new Stack<Double>();
        String[] exp = postfixExp.split(" ");

        for(int i = 0; i < exp.length;i++){
            try{
                stack.push(Double.parseDouble(exp[i]));
            }catch(NumberFormatException e){
//                double val1=0;
//                double val2=0;
//                if(i==0&&exp[i].equals("-")){
//                    val2*=-1;
//                }
                double val1 = stack.pop();
                double val2 = stack.pop();

                switch(exp[i]){
                    case "+":
                        stack.push(val2+val1);
                        break;
                    case "-":
                        stack.push(val2-val1);
                        break;
                    case "*":
                        stack.push(val2*val1);
                        break;
                    case "/":
                        stack.push(val2/val1);
                        break;
                    case "%":
                        stack.push(val2%val1);
                    default:
                        break;
                }
            }
        }

        return stack.pop();
    }

    public void clearOnClick(View view) {
        setInputText("clear");
    }

    public void deleteOnClick(View view) {
        if(!equationString.isEmpty()){
            setInputText("");
        }
    }

    public void divideOnClick(View view) {
        try {
            if(!equationString.peek().equals("^")&&!equationString.peek().equals("*")&&!equationString.peek().equals("/")&&!equationString.peek().equals("+")&&!equationString.peek().equals("-")){
                setInputText("/");
            }else{
                resultsText.setText("ERROR".toString());
            }
        }catch (EmptyStackException e){
//            inputText.setText("ERROR".toString());
            resultsText.setText("ERROR".toString());
        }
    }

    public void sevenOnClick(View view) {
        setInputText("7");
    }

    public void eightOnClick(View view) {
        setInputText("8");
    }

    public void nineOnClick(View view) {
        setInputText("9");
    }

    public void multiplyOnClick(View view) {
        try {
            if(!equationString.peek().equals("^")&&!equationString.peek().equals("*")&&!equationString.peek().equals("/")&&!equationString.peek().equals("+")&&!equationString.peek().equals("-")){
                setInputText("*");
            }else{
                resultsText.setText("ERROR".toString());
            }
        }catch (EmptyStackException e){
//            inputText.setText("ERROR".toString());
            resultsText.setText("ERROR".toString());
        }
    }

    public void fourOnClick(View view) {
        setInputText("4");
    }

    public void fiveOnClick(View view) {
        setInputText("5");
    }

    public void sixOnClick(View view) {
        setInputText("6");
    }

    public void minusOnClick(View view) {
        try{
            setInputText("-");
        }catch (EmptyStackException e){
            resultsText.setText("ERROR".toString());
        }
    }

    public void oneOnClick(View view) {
        setInputText("1");
    }

    public void twoOnClick(View view) {
        setInputText("2");
    }

    public void threeOnClick(View view) {
        setInputText("3");
    }

    public void plusOnClick(View view) {
        try {
            if(!equationString.peek().equals("^")&&!equationString.peek().equals("*")&&!equationString.peek().equals("/")&&!equationString.peek().equals("+")&&!equationString.peek().equals("-")){
                setInputText("+");
            }else{
                resultsText.setText("ERROR".toString());
            }
        }catch (EmptyStackException e){
//            inputText.setText("ERROR".toString());
            resultsText.setText("ERROR".toString());
        }
    }

    public void moduloOnClick(View view) {
        try {
            if(!equationString.peek().equals("^")&&!equationString.peek().equals("*")&&!equationString.peek().equals("/")&&!equationString.peek().equals("+")&&!equationString.peek().equals("-")){
                setInputText("%");
            }else{
                resultsText.setText("ERROR".toString());
            }
        }catch (EmptyStackException e){
//            inputText.setText("ERROR".toString());
            resultsText.setText("ERROR".toString());
        }
    }

    public void zeroOnClick(View view) {
        setInputText("0");
    }

    public void dotOnClick(View view) {
        setInputText(".");
    }

    public void equalsOnClick(View view) {
        setInputText("=");
    }
}