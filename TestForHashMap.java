package json;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author laidong
 * @time  2018年6月1日 下午3:22:27
 * @decription HashMap测试
 * 
 * HashMap不是线程安全的，需要加Synchronized 关键字
 * HashTable是线程安全的 
 * 
 * 
 */
public class TestForHashMap {
	
	public static void main(String[] args) {
		MapOper oper = new MapOper();
		TableOper tableOper=new TableOper();
		Thread a=new Thread(()->{oper.add();tableOper.add();});
		Thread b=new Thread(()->{oper.add();tableOper.add();});
		a.start();
		b.start();
		/*
		 *  HashMap未加关键字输出结果：
		 *  map:{ld=23}
		 *	map:{ld=23}
		 *	table:{ld=20}
		 *	table:{ld=40}
		 *  
		 *  HashMap添加Synchronized输出结果：
		 *  map:{ld=20}
		 *	map:{ld=40}
		 *	table:{ld=20}
		 *	table:{ld=40}
		 *  
		 *  结果分析：
		 *  HashMap线程不安全，HashTable线程安全
		 *  
		 *  原因：
		 *  HashTable中的方法上都加了Synchronized关键字-_-
		 *  
		 *  补充：
		 *  HashTable使用的hash方法和容器大小策略和HashMap不一样，HashTable使用素数作为容器大小，可以使hash算法计算更均匀。
		 *  HashMap使用2的冥次方作为容器大小，加快hash计算速度，但是增加了hash冲突，在1.8版本中，hashMap引入了红黑树，更加提高了效率，
		 *  所以目前建议使用HashMap作为兼kv储存容器。
		 *  
		 * */
	}
}

class MapOper{
	private Map<String,Integer> map=new HashMap<>();
	public MapOper() {
		map.put("ld", 0);
	}
	public synchronized void add() {
		for(int i=0;i<20;i++) {
			Integer total = map.get("ld");
			total=total+1;
			map.put("ld", total);
		}
		System.out.println("map:"+map);
	}
}

class TableOper{
	private Hashtable<String,Integer> table=new Hashtable<>();
	
	public TableOper() {
		table.put("ld", 0);
	}
	public  void add() {
		for(int i=0;i<20;i++) {
			Integer total = table.get("ld");
			total=total+1;
			table.put("ld", total);
		}
		System.out.println("table:"+table);
	}
}
