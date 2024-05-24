package test;

import static model.Gender.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dao.PersonDAO;
import model.Person;

public class DAOTest {
	
	PersonDAO pdao = new PersonDAO();
	
	Person foo = new Person("foo",2, Man);
	@Test
	public void recordPersonTestOK() {
		PersonDAO pdao = new PersonDAO();
		Person person = new Person("ほげ",2, Man);
		assertEquals(true, pdao.recordPerson(person));
		}
	
	@Test
	public void recordPersonTestNameNG() {
		
		Person person = new Person(null,2, Man);
		assertEquals(false, pdao.recordPerson(person));
	}
	
	@Test
	public void recordPersonTestAgeNG() {
		
		Person person = new Person("ほげ",200, Man);
		assertEquals(false, pdao.recordPerson(person));
	}
	
	@Test
	public void recordPersonTestGenderNG() {
		
		Person person = new Person("ほげ",10, null);
		assertEquals(false, pdao.recordPerson(person));
	}
    
	@Test
	public void serectAllTestOK() {
		
		List <Person> plist = pdao.selectAll();
		for(Person p : plist) {System.out.println(p.toString());}

	}
   
    @Test
    public void selectPersonTestOK () {
    	
    	Person person = pdao.selectPerson(1);
    	System.out.println(person.toString() + "Test\n");
    	
    }
    
    @Test
    public void selectPersonTestNG() {
       	
    	Person person = pdao.selectPerson(0);
    	assertNull(person);
    }
    
    @Test
    public void updatePersonTestOk() {
    	
		boolean record = pdao.recordPerson(foo);
		if(record) {
			Person bar = new Person(pdao.selectMAXID(), "bar", 2, Man);
			System.out.println(pdao.updatePerson(bar));
			assertEquals(true, pdao.updatePerson(bar));
			//FAIL idの指定がおかしいので
			//「現在登録されている内の最大id」を返すメソッドに変更
		}
    }
    
    @Test
    public void updatePersonTestNGIllegal() {
		boolean record = pdao.recordPerson(foo);
		if(record) {
			Person hoge = new Person(pdao.selectMAXID(), "hoge", 200, Man);
			System.out.println(pdao.updatePerson(hoge));
			assertEquals(false, pdao.updatePerson(hoge));
		}
    }
    
    @Test
    public void updatePersonTestNGNull() {
    
		boolean record = pdao.recordPerson(foo);
		if(record) {
			Person hoge = new Person(pdao.selectMAXID(), "hoge", 2, null);
			assertEquals(false, pdao.updatePerson(hoge));
		}
    }
    
    @Test
    public void deletePersonTestOK() {
   	
		boolean record = pdao.recordPerson(foo);
		if(record) {
			assertEquals(true, pdao.deletePerson(pdao.selectMAXID()));
			//FAIL おそらくIDの指定がおかしい
			//アプリケーションの上では問題なく更新も削除も出来るのでテストケースがおかしい
		}
    }
    
    @Test
    public void deletePersonTestNG() {
			assertEquals(false, pdao.deletePerson(0));
    }
    
    @Test
    public void selectPersonListTestOK() {
    	List<Integer> numList = Arrays.asList(1, 2, 3);
    	
    	List <Person> plist = pdao.selectPersonList(numList);
    	
		for(Person p : plist) {System.out.println(p.toString());}
    }
    
    @Test
    public void selectPersonListTestNG() {
    	List<Integer> numList = Arrays.asList(0, 1, 2);
    	
    	List <Person> plist = pdao.selectPersonList(numList);
    	assertNull(plist);
    }
    
    @Test
    public void selectMAXIDOK() {    	
    	System.out.println(pdao.selectMAXID());
    }
    
    @Test
    public void selectPersonByNameOK() {
    	List<Integer> ids = pdao.selectPersonByName("ユタカ").stream().map(i -> i.getId()).collect(Collectors.toList());
    	assertEquals(1, ids.get(0));
    }
    
    @Test
    public void selectPersonByNameNG() {
    	List<Person> hoges = pdao.selectPersonByName("ほげほげ").stream().collect(Collectors.toList());
    	assertEquals(0, hoges.size());
    }
    
    @Test
    public void deletePersonListOK() {
    	List<Person> foos = pdao.selectPersonByName("foo");
    	List<Integer> fooids = foos.stream().limit(2).map(i -> i.getId()).collect(Collectors.toList());
    	assertEquals(true, pdao.deletePersonList(fooids));
    }
    
    @Test
    public void deletePersonListNG() {
    	List<Person> foos = pdao.selectPersonByName("foo");
    	List<Integer> fooids = foos.stream().limit(2).map(i -> i.getId()).collect(Collectors.toList());
    	fooids.add(0);
 
    	assertEquals(false, pdao.deletePersonList(fooids));
    }
    
    @Test
    public void updatePersonListOK() {
    	List<Person> foos = pdao.selectPersonByName("foo");
    	List<Person> bars = foos.stream().limit(2).collect(Collectors.toList());
    	for(Person p :bars) {p.setName("bar");}
    	assertEquals(true, pdao.updatePersonList(bars));
    }
    
    @Test
    public void updatePersonListNG() {
//    	テストケースを書き直す
    	List<Person> foos = pdao.selectPersonByName("foo");
    	List<Person> hoges = foos.stream().limit(2).collect(Collectors.toList());

    	for(Person p :hoges) {p.setName(null);}
    	assertEquals(false, pdao.updatePersonList(hoges));
    }
}
