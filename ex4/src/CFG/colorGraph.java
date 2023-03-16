/***********/
/* PACKAGE */
/***********/
package CFG;

import java.util.*;
/*******************/
/* GENERAL IMPORTS */
/*******************/
import TEMP.*;
import MIPS.*; 
//import basicBlock;
/*******************/
/* PROJECT IMPORTS */
/*******************/
public class colorGraph{
	public class node{
		String name;//who we represent
		HashSet<String> neig;
		int activeNeig;
		String paint;
		public node(String name){
			this.name=name;
			this.neig=new HashSet<String>();
			this.activeNeig=0;
			this.paint=null;
		}

	} 
	HashSet<node> valid=new HashSet<node>();
	HashSet<node> overall=new HashSet<node>();

	public colorGraph(basicBlock h) {
		basicBlock curr=h;
		while (curr!=null){
			Iterator<String> it=curr.inSet.iterator();
			node p;
			while(it.hasNext()){
				String n=it.next();
				p=find(n);
				if(p==null){
					p=new node(n);
					this.overall.add(p);
					this.valid.add(p);
				}
				Iterator<String> o=curr.inSet.iterator();
				while(o.hasNext()){
					String s=o.next();
					if(!s.equals(p.name)){
						p.neig.add(s);
						
					}
				}
				p.activeNeig=p.neig.size();
				
			}
			curr=curr.direct;
		
			
		}
			

	}
	public node find(String check){
		for (node maybe : this.valid) {
			if (Objects.equals(maybe.name, check)) {
				return maybe;

			}
		}
		return null;


	}
	public Boolean isEmpty(){
		return this.valid.isEmpty();
	}

}