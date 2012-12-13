package basic;

import java.util.ArrayList;
import java.util.Stack;


public class Calc {

	public static void main(String[] args){		
		
		long teraz = System.currentTimeMillis();
		
		String eq = "(2*(4*(2+2)-2)+2)";
		char [] sign = new char[eq.length()+1];
		for(int i=0; i<sign.length-1; i++){
			sign[i]=eq.charAt(i);
		}	

		String str="";
		ArrayList<String> arraylist = new ArrayList<String>();
		
		int j=0;	
		while(sign[j]!= '\0') {			
			if( (sign[j] == '+') || (sign[j] == '-') || (sign[j] == '*') || (sign[j] == '/')  || (sign[j] == '(') || ( sign[j] == ')' )  ){
				arraylist.add(str);
				str="";
				arraylist.add(str += sign[j]);
				str="";
			}
			else str += sign[j];
			
			if((sign[j+1] == '\0')){
				arraylist.add(str);
			}
			j++;
		}
		Stack<String> stack= new Stack<String>();		
		
		stack = reverseStack(arraylist);
	
		equationCalc(stack);
		
	/*	Stack <String>newstack = bracketCalc(stack);
	
		System.out.println("------");
		
		newstack = multDiv(newstack); 
			
		System.out.println("\n-------\n");
		
		System.out.println ( plusMinus(newstack) );*/
		
		long pozniej = System.currentTimeMillis();
		double czas = (pozniej-teraz);
		czas/=1000;
		System.out.println();
		System.out.println("Czas wykonywania = " + czas+" sek");	
	
	}
	
	public static Stack<String> reverseStack(Stack<String> stack){
		Stack<String> newstack = new Stack<String>();
		while(!stack.isEmpty()){
			newstack.push(stack.pop());
		}
		return newstack;
	}
	
	/*Give reversed arraylist
	 * Return a stack in right order
	*/
	
	public static Stack<String> reverseStack(ArrayList<String> al){
		Stack<String> stack = new Stack<String>();
		for(int i=0;i<al.size();i++){
			if(al.get(al.size()-1-i) == "") continue;
			stack.add( (String) al.get(al.size()-1-i) );
		}
		return stack;
		
		
	}
	/* Calculate all equation with brackets, multDiv,plusMinus
	 * algorithm :
	 * 1 . Find  first bracket in stack and j++
	 * 2. Count open brackets and end brackets in main bracket 
	 * 3. If open brackets == end brackets .. do recursion .. equationCalc once again .. but without 1 pair open,end bracket -- inside bracket
	 * 4. If there is no more brackets do multiDiv and plusMinus method's
	 * 
	 * returns calculated equation 
	* @param stack - equation  stack
	*/
	
	public static Stack<String> equationCalc(Stack<String> stack){
		String sign1="";
		int i=0,j=0;
		Stack<String> newstack = new Stack<String>();
		
		while( !stack.isEmpty() ){
			i++;
			sign1=stack.pop();
			System.out.println(i+" "+sign1 + " " +stack.size());
			if( (sign1.charAt(0) == '(')  ){
				j++;
				Stack<String> substack = new Stack<String>();
				sign1=stack.pop();			
				do{
					i++;
					if(sign1.charAt(0) == '(') j++;
					System.out.println(i+" "+sign1 + " " +stack.size());
					substack.push(sign1);
					sign1 = stack.pop();
					if(sign1.charAt(0) == ')' )j--;
				}
				while( j!=0 );	
				
				System.out.println("-----");
				substack=reverseStack(substack);
				
				System.out.println("-----");
				substack = equationCalc(substack); // HERE IS RECURSION .. 
				
				substack = multDiv(substack);
				System.out.println("-----");
				
				sign1= plusMinus(substack);
				System.out.println("-----");
				stack.push(sign1);
			}		
			else{
				newstack.push(sign1);
				}	

			}
		return reverseStack(newstack);
	}
	
	public static Stack<String> multDiv(Stack<String> stack){
		
		String sign1="";
		String number1="",number2="";
		char ch=' ';
		String sum="";
		int i=0;
		Stack<String> newstack = new Stack<String>();
		
		while(!stack.isEmpty()){
			i++;
			sign1=stack.pop();
			System.out.println(i+" "+sign1+" "+stack.size());
			if( (sign1.charAt(0) == '*') || (sign1.charAt(0) == '/')  ){
					
					number1=newstack.pop();
					number2=stack.pop();
					i++;
					System.out.println(i+" "+number2+" " +stack.size());	
					ch=sign1.charAt(0);			
					switch(ch){		
					
					case '*':
					//System.out.println(sign1);
					sum = Double.toString(( Double.parseDouble(number1)  * Double.parseDouble(number2) ));
					stack.push( sum );
					continue;
					
					case '/':
					//System.out.println(sign1);
					sum = Double.toString(( Double.parseDouble(number1)  / Double.parseDouble(number2) ));
					stack.push( sum );
					continue;				
					}
				}
			
			else{
				newstack.push(sign1);
			}
		}
		return reverseStack(newstack);
	}
	/*Calculate all minuses and pluses .. 
	*/
	public static String plusMinus(Stack<String> stack){
		String sign2="";
		String sign1="";
		String number1="",number2="";
		boolean sn=false;
		char ch=' ';
		String sum="";
		int i=0;
		
		while( !stack.isEmpty() ){
			i++;
			sign1=stack.pop();
			System.out.println(i+" "+sign1+" "+stack.size());
			if( (sign1.charAt(0) == '+') || (sign1.charAt(0) == '-')  ){
				sign2=sign1;
			}
			else{
				if(sn){		
					number2=sign1;
					ch=sign2.charAt(0);
					//System.out.println(ch);				
					switch(ch){		
					
					case '+':
					//System.out.println(sign1);
					sum = Double.toString(( Double.parseDouble(number1)  + Double.parseDouble(number2) ));
					stack.push( sum );
					sn=false;
					continue;
					
					case '-':
					//System.out.println(sign1);
					sum = Double.toString(( Double.parseDouble(number1)  - Double.parseDouble(number2) ));
					stack.push( sum );
					sn=false;
					continue;				
					}
				}
				else{
					number1=sign1;
					sn=true;	
				}
			}
		}
		return sign1;
	}
}